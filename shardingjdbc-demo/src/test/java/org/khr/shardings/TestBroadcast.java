package org.khr.shardings;

import org.junit.jupiter.api.Test;
import org.khr.shardings.domain.Dict;
import org.khr.shardings.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author KK
 * @create 2025-06-26-17:47
 */
@SpringBootTest
public class TestBroadcast {

    @Autowired
    private DictMapper dictMapper;

    @Test
    public void test() {
        Dict dict = new Dict();
        dict.setDictType("caonima");
        int insert = dictMapper.insert(dict);
        System.out.println("insert = " + insert);
    }

    @Test
    public void testSelect() {
       dictMapper.selectList(null).forEach(System.out::println);
    }

}
