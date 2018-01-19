package com.yhyt.health.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * 
 * @author gsh
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableCaching //加上这个注解是的支持缓存注解
public class RedisConfiguration extends CachingConfigurerSupport {

//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//
//    @Value("${spring.redis.database}")
//    private int database;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.sentinel.nodes}")
//    private String redisNodes;
//
//    @Value("${spring.redis.sentinel.master}")
//    private String master;
//
//
//    /**
//     * redis哨兵配置
//     * @return
//     */
//    @Bean
//    public RedisSentinelConfiguration redisSentinelConfiguration(){
//        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
//        String[] host = redisNodes.split(",");
//        for(String redisHost : host){
//            String[] item = redisHost.split(":");
//            String ip = item[0];
//            String port = item[1];
//            configuration.addSentinel(new RedisNode(ip, Integer.parseInt(port)));
//        }
//        configuration.setMaster(master);
//        return configuration;
//    }
//
//    /**
//     * 连接redis的工厂类
//     *
//     * @return
//     */
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory(redisSentinelConfiguration());
////        factory.setHostName(host);
////        factory.setPort(port);
////        factory.setTimeout(timeout);
////        factory.setPassword(password);
////        factory.setDatabase(database);
//        return factory;
//    }
//
//    /**
//     * 配置RedisTemplate
//     * 设置添加序列化器
//     * key 使用string序列化器
//     * value 使用Json序列化器
//     * 还有一种简答的设置方式，改变defaultSerializer对象的实现。
//     *
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        //StringRedisTemplate的构造方法中默认设置了stringSerializer
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        //设置开启事务
//        template.setEnableTransactionSupport(true);
//        //set key serializer
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        template.setKeySerializer(stringRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    /**
//     * 设置RedisCacheManager
//     * 使用cache注解管理redis缓存
//     *
//     * @return
//     */
//    @Override
//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
//        return redisCacheManager;
//    }
//
//    /**
//     * 自定义生成redis-key
//     *
//     * @return
//     */
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object o, Method method, Object... objects) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(o.getClass().getName()).append(".");
//                sb.append(method.getName()).append(".");
//                for (Object obj : objects) {
//                    sb.append(obj.toString());
//                }
//                System.out.println("keyGenerator=" + sb.toString());
//                return sb.toString();
//            }
//        };
//    }


    /**
     * 注入 RedisConnectionFactory
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> functionDomainRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式
     *
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }

    /**
     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 实例化 ValueOperations 对象,可以使用 String 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 实例化 ListOperations 对象,可以使用 List 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 实例化 SetOperations 对象,可以使用 Set 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}
