package dev.tinchx.sponsor.jedis;

import dev.tinchx.sponsor.SponsorPlugin;
import dev.tinchx.sponsor.events.PacketReceiveEvent;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber {

    private Jedis jedis = SponsorPlugin.getInstance().getJedisPool().getResource();

    public RedisSubscriber(String channel) {
        subscribe(channel);
    }

    private void subscribe(String channel) {
        new Thread(() -> jedis.subscribe(get(channel), channel)).start();
    }

    private JedisPubSub get(String con) {
        return new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                if (channel.equalsIgnoreCase(con)) {
                    String[] args = message.split(";");

                    String command = args[0];
                    Bukkit.getPluginManager().callEvent(new PacketReceiveEvent(channel, command, args));
                }
            }
        };
    }
}