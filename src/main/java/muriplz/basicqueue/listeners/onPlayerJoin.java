package muriplz.basicqueue.listeners;

import muriplz.basicqueue.queue.Queue;
import muriplz.basicqueue.queue.QueuePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoin implements Listener {


    @EventHandler
    public void onJoin( PlayerJoinEvent e ){
        Player p = e.getPlayer();

        if(!Queue.isEmpty()){
            if(Queue.hasRoomInsideServer()){
                if(!Queue.whoFirst().equals(p)){
                    p.kickPlayer("you are not first");
                }else{
                    //joins
                }
            }else{
                if(!Queue.hasPlayer(p)){
                    QueuePlayer queuePlayer = new QueuePlayer(p);
                }

            }
        }else{
            if(Queue.hasRoomInsideServer()){
                //joins
            }else{

            }
        }


    }

}

