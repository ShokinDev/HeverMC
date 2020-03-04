package br.com.hevermc.commons.backend.jedis;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Getter
public class Redis {

	public JedisPool j;

	public Redis(String host, int port) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(128);
		j = new JedisPool(config, host, port, 0);
		System.out.println("[REDIS] Conectado.");
	}

	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = j.getResource();
			if (contains(key))
				del(key);
			jedis.set(key, value);
		} finally {
			jedis.close();
		}
	}

	public boolean contains(String key) {
		Jedis jedis = null;
		try {
			jedis = j.getResource();
			return jedis.exists(key);

		} finally {
			jedis.close();
		}
	}

	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = j.getResource();
			if (contains(key))
				jedis.del(key);
		} finally {
			jedis.close();
		}
	}

	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = j.getResource();
			return jedis.get(key);
		} finally {
			jedis.close();
		}
	}

}
