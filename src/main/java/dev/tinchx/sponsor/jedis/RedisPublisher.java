package dev.tinchx.sponsor.jedis;

import dev.tinchx.sponsor.SponsorPlugin;
import dev.tinchx.sponsor.events.PacketSendingEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

public class RedisPublisher {

    private SponsorPlugin plugin = SponsorPlugin.getInstance();

    public void write(String message) {
        write(plugin.getConfig().getString("redis.channel"), message);
    }


    public void write(String con, String message) {
        try (Jedis jedis = plugin.getJedisPool().getResource()) {
            /*
            if (SponsorPlugin.getInstance().getConfig().getBoolean("redis.usepass")) {
                jedis.auth(SponsorPlugin.getInstance().getConfig().getString("redis.password"));
            }
             */
            jedis.publish(con, message);
            new BukkitRunnable() {

                @Override
                public void run() {
                    Bukkit.getPluginManager().callEvent(new PacketSendingEvent(con, message));
                }
            }.run();
        }
    }
}