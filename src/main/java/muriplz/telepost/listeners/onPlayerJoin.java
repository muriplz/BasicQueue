package muriplz.telepost.listeners;

import muriplz.telepost.Queue;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoin implements Listener {

    private final Queue Queue = muriplz.telepost.Queue.getInstance();

    @EventHandler
    public void onJoin( PlayerJoinEvent e ){
        Player p = e.getPlayer();

        if(!Queue.canJoin(p,e)){
            p.kickPlayer(Queue.kickMessageToQueue(p));
        }

        Queue.addToQueue(p);

    }

}

