package com.yhyt.health.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.yhyt.health.util.SerializeUtil;

/**
 * @author localadmin
 */
@Repository
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    /**
     * 保存对象
     *
     * @param key    key
     * @param t 对象
     * @param expire 过期时间(单位:秒),传入 -1 时表示不设置过期时间
     */
    public void put(String global, String key, Object t, long expire) {
        logger.info("put" + "global :" + global + " key : " + key + " object " + t + " expire : " + expire);
        redisTemplate.opsForHash().put(global, key, SerializeUtil.serialize(t));
        if (expire != -1) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }



    /**
     * 删除
     *
     * @param key 传入key的名称
     */
    public boolean remove(String global, String key) {
        logger.info("remove " + "global:" + global + " key :" + key);
        Long result = redisTemplate.opsForHash().delete(global, key);
        logger.info("remove result " + result.longValue());
        return result > 0 ? true : false;
    }

    /**
     * 查询
     *
     * @param key 查询的key
     * @return
     */
    public Object get(String global, String key) {
        logger.info("get" + "global :" + global + " key : " + key);
        byte[] value = (byte[]) redisTemplate.opsForHash().get(global, key);
        return SerializeUtil.unserialize(value);
    }

    /**
     * 获取当前redis库下所有对象
     *
     * @return
     */
    public List<Object> getAll(String global) {
        return redisTemplate.opsForHash().values(global);
    }

    /**
     * 查询查询当前redis库下所有key
     *
     * @return
     */
    public Set<Object> getKeys(String global) {
        return redisTemplate.opsForHash().keys(global);
    }

    /**
     * 判断key是否存在redis中
     *
     * @param key 传入key的名称
     * @return
     */
    public boolean isKeyExists(String global, String key) {
        return redisTemplate.opsForHash().hasKey(global, key);
    }

    /**
     * 查询当前key下缓存数量
     *
     * @return
     */
    public long count(String global) {
        logger.info("count" + "global :" + global);
        return redisTemplate.opsForHash().size(global);
    }

    /**
     * 清空redis
     */
    public void empty(String global) {
        Set<Object> set = redisTemplate.opsForHash().keys(global);
        set.stream().forEach(key -> redisTemplate.opsForHash().delete(global, key));
    }

    /**
     * 保存对象
     *
     * @param key    key
     * @param t 对象
     * @param expire 过期时间(单位:秒),传入 -1 时表示不设置过期时间
     */
    public void put(String key, Object t, long expire) {
        redisTemplate.opsForValue().set(key,t,expire,TimeUnit.SECONDS);
    }

    /**
     * 得到对象
     *
     * @param key    key
     */
    public Object get(String key) {
       return redisTemplate.opsForValue().get(key);
    }

}
