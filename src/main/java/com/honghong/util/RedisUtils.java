package com.honghong.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author: kent
 * @date: 2018/12/13
 */
@Component
public class RedisUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private StringRedisTemplate redis;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    ValueOperations operations = null;

    /**
     * 写入redis存储（不设置expire存活时间）
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            operations = redis.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return result;
    }

    /**
     * 写入redis缓存（设置expire存活时间）
     *
     * @param key
     * @param value
     * @param timeOut
     * @return
     */
    public boolean set(final String key, String value, long timeOut) {
        boolean result = false;
        try {
            operations = redis.opsForValue();
            operations.set(key, value);
            redis.expire(key, timeOut, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return result;
    }

    /**
     * 读取redis缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object obj = null;
        try {
            operations = redis.opsForValue();
            obj = operations.get(key);
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return obj;
    }

    /**
     * 判断redis缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        boolean result = false;
        try {
            result = redis.hasKey(key);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return result;
    }

    /**
     * redis根据key删除对应的value
     *
     * @param key
     * @return
     */
    public boolean remove(final String key) {
        boolean result = false;
        try {
            if (exists(key)) {
                redis.delete(key);
            }
            result = true;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return result;
    }

    /**
     * redis根据keys批量删除对应的value
     *
     * @param keys
     * @return
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public boolean setHashCache(final String key, final String hashKey, final Object object) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, object);
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
    }

    public Object getHashCache(final String key, final String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public boolean deleteHashCache(final String key, final String hashKey) {
        try {
            redisTemplate.opsForHash().delete(key, hashKey);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
    }
}
