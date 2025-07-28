package org.khr.canalspringbootstarter.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class CanalHandler<T> {

    public abstract String tableName();

    public abstract void handleInsert(String table, T after);

    public abstract void handleUpdate(String table, T before, T after);

    public abstract void handleDelete(String table, T before);

    @SuppressWarnings("unchecked")
    public final Class<T> getEntityClass() {
        Type genericSuperclass = getClass().getGenericSuperclass();

        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (type instanceof Class<?> clazz) {
                return (Class<T>) clazz;
            }
        }
        throw new RuntimeException("无法解析泛型类型，请确保继承结构正确");
    }
}
