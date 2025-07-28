package org.khr.canalspringbootstarter.core;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = "canal", name = "enabled", havingValue = "true", matchIfMissing = false)
public class CanalListenerService {

    private final CanalConnector canalConnector;
    private final List<CanalHandler<?>> canalHandlers;
    private final CanalProperties canalProperties;

    @PostConstruct
    public void startListener() {
        new Thread(() -> {
            try {
                while (true) {
                    canalConnector.connect();
                    canalConnector.subscribe(canalProperties.dbName() + ".*");
                    Message message = canalConnector.get(100);
                    List<CanalEntry.Entry> entries = message.getEntries();
                    if (entries.isEmpty()) {
                        Thread.sleep(1000);
                    } else {
                        for (CanalEntry.Entry entry : entries) {
                            handleEntry(entry);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                canalConnector.disconnect();
            }
        }).start();
    }


    private final ObjectMapper objectMapper = new ObjectMapper();

    private void handleEntry(CanalEntry.Entry entry) throws InvalidProtocolBufferException {
        String tableName = entry.getHeader().getTableName();
        CanalEntry.EntryType entryType = entry.getEntryType();

        if (!CanalEntry.EntryType.ROWDATA.equals(entryType)) {
            return;
        }

        ByteString storeValue = entry.getStoreValue();
        CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(storeValue);
        CanalEntry.EventType eventType = rowChange.getEventType();
        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();

        for (CanalEntry.RowData rowData : rowDatasList) {
            Map<String, Object> beforeMap = columnsToMap(rowData.getBeforeColumnsList());
            Map<String, Object> afterMap = columnsToMap(rowData.getAfterColumnsList());

            log.info("表名:{} 操作类型:{}", tableName, eventType);

            for (CanalHandler<?> handler : canalHandlers) {
                if (!handler.tableName().equals(tableName)) {
                    continue;
                }

                Class<?> entityClass = handler.getEntityClass();

                switch (eventType) {
                    case INSERT -> {
                        Object afterEntity = objectMapper.convertValue(afterMap, entityClass);
                        // 这里用强转，编译器会警告，可以加 @SuppressWarnings
                        ((CanalHandler<Object>) handler).handleInsert(tableName, afterEntity);
                    }
                    case UPDATE -> {
                        Object beforeEntity = objectMapper.convertValue(beforeMap, entityClass);
                        Object afterEntity = objectMapper.convertValue(afterMap, entityClass);
                        ((CanalHandler<Object>) handler).handleUpdate(tableName, beforeEntity, afterEntity);
                    }
                    case DELETE -> {
                        Object beforeEntity = objectMapper.convertValue(beforeMap, entityClass);
                        ((CanalHandler<Object>) handler).handleDelete(tableName, beforeEntity);
                    }
                    default -> log.info("其他操作:{}", eventType);
                }
                // 如果确定一条数据只被一个handler处理，处理完跳出循环
                break;
            }
        }
    }


    private Map<String, Object> columnsToMap(List<CanalEntry.Column> columns) {
        Map<String, Object> map = new HashMap<>();
        for (CanalEntry.Column column : columns) {
            map.put(column.getName(), column.getValue());
        }
        return map;
    }
}
