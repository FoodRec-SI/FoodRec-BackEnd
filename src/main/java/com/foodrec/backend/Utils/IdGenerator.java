package com.foodrec.backend.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IdGenerator {

    private static RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    private static Map<String, String> cache;

    @Autowired
    public IdGenerator(RedisConnectionFactory redisConnectionFactory) {
        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        cache = new ConcurrentHashMap<>();
    }

    public static String generateNextId(Class<?> entityClass, String columnName) {
        String entityClassName = entityClass.getSimpleName();
        String prefix = entityClassName.substring(0, 3);
        String columnId = cache.get(prefix);
        if (columnId == null || columnId.isEmpty()) {
            columnId = RepositoryUtils.findLastById(entityClass, columnName);
            assert columnId != null;
            if(columnId.isEmpty()){
                columnId = prefix + "000001";
            }
            cache.put(prefix, columnId);
        }
        Long incrementedNumber = Long.parseLong(cache.get(prefix).substring(3));
        String incrementedColumnId = prefix + String.format("%06d", incrementedNumber + 1);
        columnId = incrementedColumnId.toUpperCase();
        cache.put(prefix, columnId);
        redisTemplate.delete(prefix);
        return columnId;
    }
}

