package muriplz.telepost.listeners;

import muriplz.telepost.Queue;
import muriplz.telepost.BasicQueue;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class onPlayerJoin implements Listener {

    private final Queue Queue = muriplz.telepost.Queue.getInstance();


    HashMap<String,Integer> queue = BasicQueue.queue;

    @EventHandler
    public void onJoin( PlayerJoinEvent e ){
        Player p = e.getPlayer();

        if(!Queue.canJoin(p,e)){
            p.kickPlayer(Queue.kickMessageToQueue(p));
        }

        Queue.addToQueue(p);



    }

}

