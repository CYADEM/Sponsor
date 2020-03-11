package dev.tinchx.sponsor;

import dev.tinchx.sponsor.jedis.RedisPublisher;
import dev.tinchx.sponsor.jedis.RedisSubscriber;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

public class SponsorPlugin extends JavaPlugin {

    @Getter
    private JedisPool jedisPool;
    @Getter
    private RedisPublisher redisPublisher;
    private RedisSubscriber redisSubscriber;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        load();
    }

    private void load() {
        jedisPool = new JedisPool(this.getConfig().getString("redis.host"), this.getConfig().getInt("redis.port"));
        redisPublisher = new RedisPublisher();
        redisSubscriber = new RedisSubscriber(getConfig().getString("redis.channel"));
    }

    public static SponsorPlugin getInstance() {
        return getPlugin(SponsorPlugin.class);
    }
}