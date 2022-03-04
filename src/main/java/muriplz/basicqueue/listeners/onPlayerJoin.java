package muriplz.basicqueue.listeners;

import muriplz.basicqueue.Queue;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class onPlayerJoin implements Listener {

    private final Queue Queue = muriplz.basicqueue.Queue.getInstance();
    HashMap<String,Integer> queue = muriplz.basicqueue.BasicQueue.queue;


    @EventHandler
    public void onJoin( PlayerJoinEvent e ){
        Player p = e.getPlayer();


        if(!Queue.hasEnoughRoom(p)){
            p.kickPlayer(Queue.kickMessageToQueue(p));
        }


        if(!Queue.isOnQueue(p)){
            if(queue.isEmpty()){

            }else{
                Queue.addToQueue(p);
            }
        }else{

        }

    }

}

