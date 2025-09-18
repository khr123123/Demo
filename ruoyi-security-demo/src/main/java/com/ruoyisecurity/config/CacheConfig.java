// package com.ruoyisecurity.config;
//
// import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
// import org.springframework.cache.CacheManager;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.cache.RedisCacheConfiguration;
// import org.springframework.data.redis.cache.RedisCacheManager;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.RedisSerializationContext;
//
// /**
//  * SpringCache 缓存配置器
//  // */
// @Configuration
// // 在Spring Boot应用中，如果你在配置类中手动创建了 RedisConnectionFactory 和 CacheManager，
// // 确实会覆盖 application.yml 中的Redis配置。为了确保 application.yml 中的配置生效，你可以采取以下几种方法：
// // 使用 @ConditionalOnMissingBean 注解
// public class CacheConfig {
//
//     /**
//      * 缓存使用 Lettuce
//      */
//     @Bean
//     @ConditionalOnMissingBean
//     public RedisConnectionFactory redisConnectionFactory() {
//         return new LettuceConnectionFactory();
//     }
//
//     /**
//      * 缓存管理器
//      */
//     @Bean
//     @ConditionalOnMissingBean
//     public CacheManager cacheManager() {
//         RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                 .disableCachingNullValues()
//                 .serializeValuesWith(RedisSerializationContext.SerializationPair
//                         .fromSerializer(new GenericJackson2JsonRedisSerializer()));
//         return RedisCacheManager.builder(redisConnectionFactory())
//                 .cacheDefaults(redisCacheConfiguration)
//                 .build();
//     }
// }