package muriplz.basicqueue.listeners;

import muriplz.basicqueue.queue.DynamicQueue;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoin implements Listener {


    @EventHandler
    public void onJoin( PlayerJoinEvent e ){
        Player p = e.getPlayer();

        if(!DynamicQueue.canJoinAndJoinQueue(p,e)){
            p.kickPlayer(DynamicQueue.kickMessageToQueue(p));
        }

    }

}

