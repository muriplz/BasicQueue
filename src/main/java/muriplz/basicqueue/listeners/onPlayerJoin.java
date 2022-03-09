package muriplz.basicqueue.listeners;

import muriplz.basicqueue.BasicQueue;
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
                if(!Queue.isFirst(p)){
                    p.kickPlayer(BasicQueue.getMessage("not-first").replace("%POSITION%",Queue.getPos(p)+"").replace("%COOLDOWNMINUTES%",Queue.cooldownOnSeconds/60+""));
                    Queue.resetCooldown(p);

                }else{
                    //joins
                    Queue.delete(p);
                }
            }else{
                if(!Queue.hasPlayer(p)){
                    Queue.add(p);
                    p.kickPlayer("you are added to queue");
                }else{
                    p.kickPlayer("wait");
                    Queue.resetCooldown(p);
                }

            }
        }else{
            if(!Queue.hasRoomInsideServer()){
                Queue.add(p);
                p.kickPlayer("you are added to queue2");
            }
        }


    }

}

