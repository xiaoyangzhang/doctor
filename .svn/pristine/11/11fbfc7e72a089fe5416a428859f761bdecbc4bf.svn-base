//package com.yhyt.health.config;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import com.yhyt.health.constant.RedisConstants;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
///**
// * JedisClusterConfig
// *
// * @author gsh
// * @create 2017-10-23 19:08
// **/
//@Configuration
//public class JedisClusterConfig {
//
//    @Autowired
//    private RedisConstants redisConstants;
//
//    /**
//     * 客户端连接超时时间
//     */
//    private final static int TIME_OUT = 3000;
//    /**
//     * soket超时时间
//     */
//    private final static int SO_TIME_OUT = 3000;
//
//    /**
//     * 最大尝试次数
//     */
//    private final static int MAX_ATTEMP = 5;
//
//    /**
//     *这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
//     * @return
//     */
//    @Bean
//    public JedisCluster getJedisCluster() {
//        String[] serverArray = redisConstants.getClusterNodes().split(",");//获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
//        Set<HostAndPort> nodes = new HashSet<>();
//
//        for (String ipPort : serverArray) {
//            String[] ipPortPair = ipPort.split(":");
//            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
//
//        }
//        JedisCluster jedisCluster = new JedisCluster(nodes, TIME_OUT, SO_TIME_OUT, MAX_ATTEMP, "123456", getCommonPoolConfig());
//        return jedisCluster;
//    }
//
//    /**
//     * pool配置
//     * @return
//     */
//    public static GenericObjectPoolConfig getCommonPoolConfig() {
//        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 10);
//        poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 5);//空闲数
//        poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 2);
//        // JedisPool.borrowObject最大等待时间
//        poolConfig.setMaxWaitMillis(1000L);
//        // 开启jmx
//        poolConfig.setJmxEnabled(true);
//        return poolConfig;
//    }
//
//
//}