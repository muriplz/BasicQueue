package muriplz.basicqueue.listeners;

import muriplz.basicqueue.queue.Queue;
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
                    Queue.delete(p);
                }
            }else{
                if(!Queue.hasPlayer(p)){
                    Queue.add(p);
                    p.kickPlayer("you are added to queue");
                }

            }
        }else{
            if(Queue.hasRoomInsideServer()){
                //joins
                Queue.delete(p);
            }else{
                Queue.add(p);
                p.kickPlayer("you are added to queue2");
            }
        }


    }

}

