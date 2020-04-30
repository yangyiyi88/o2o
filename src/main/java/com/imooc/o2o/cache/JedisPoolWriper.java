package com.imooc.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolWriper {
    /**
     * Redis连接池对象
     */
    private JedisPool jedisPool;

    public JedisPoolWriper(final JedisPoolConfig jedisPoolConfig, final String host, final int port) {
        try {
            this.jedisPool = new JedisPool(jedisPoolConfig, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
