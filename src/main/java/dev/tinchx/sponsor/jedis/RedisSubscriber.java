package dev.tinchx.sponsor.jedis;

import dev.tinchx.sponsor.SponsorPlugin;
import dev.tinchx.sponsor.events.PacketReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber {

    private SponsorPlugin plugin = SponsorPlugin.getInstance();
    private Jedis jedis = SponsorPlugin.getInstance().getJedisPool().getResource();

    public RedisSubscriber(String channel) {
        subscribe(channel);
    }

    private void subscribe(String channel) {
        if (plugin.getConfig().contains("redis.auth.enabled") && plugin.getConfig().getBoolean("redis.auth.enabled")) {
            if (plugin.getConfig().contains("redis.auth.pass")) {
                jedis.auth(plugin.getConfig().getString("redis.auth.pass"));
            }
        }
        new Thread(() -> jedis.subscribe(get(channel), channel)).start();
    }

    private JedisPubSub get(String con) {
        return new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                if (channel.equalsIgnoreCase(con)) {
                    String[] args = message.split(";");

                    String command = args[0];
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            Bukkit.getPluginManager().callEvent(new PacketReceiveEvent(channel, command, args));
                        }
                    }.runTaskLater(SponsorPlugin.getInstance(), 1L);
                }

            }
        };
    }
}