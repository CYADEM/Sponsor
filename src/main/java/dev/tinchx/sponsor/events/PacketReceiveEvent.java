package dev.tinchx.sponsor.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
@Getter
public class PacketReceiveEvent extends Event {

    @Getter
    private static HandlerList handlerList = new HandlerList();

    private String channel, command;
    private String[] arguments;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}