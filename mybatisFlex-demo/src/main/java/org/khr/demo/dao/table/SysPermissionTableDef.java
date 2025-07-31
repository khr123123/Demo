package org.khr.demo.dao.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 *  表定义层。
 *
 * @author KK
 * @since 2025-07-31 14:13:27
 */
public class SysPermissionTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SysPermissionTableDef SYS_PERMISSION = new SysPermissionTableDef();

    /**
     * 权限ID
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 接口路径
     */
    public final QueryColumn URL = new QueryColumn(this, "url");

    /**
     * 状态
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 权限标识（如 system:user:list）
     */
    public final QueryColumn PERMISSION_KEY = new QueryColumn(this, "permission_key");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PERMISSION_KEY, URL, STATUS, CREATE_TIME};

    public SysPermissionTableDef() {
        super("", "sys_permission");
    }

    private SysPermissionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SysPermissionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SysPermissionTableDef("", "sys_permission", alias));
    }

}
