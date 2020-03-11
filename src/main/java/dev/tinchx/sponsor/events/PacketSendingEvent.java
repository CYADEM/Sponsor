package dev.tinchx.sponsor.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
@Getter
public class PacketSendingEvent extends Event {

    @Getter
    private static HandlerList handlerList = new HandlerList();

    private String channel, message;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}