package muriplz.basicqueue.listeners;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.messages.Messages;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class onPlayerJoin implements Listener {


    @EventHandler
    public void onJoin( PlayerLoginEvent e ){

        Player p = e.getPlayer();

        String uuid = p.getUniqueId().toString();

        if(e.getResult()== PlayerLoginEvent.Result.KICK_BANNED || e.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST){
            return;
        }

        if(e.getResult()== PlayerLoginEvent.Result.KICK_FULL){
            if(Queue.hasPlayer(uuid)){
                e.setKickMessage("testing");
                Queue.resetCooldown(uuid);
            }else{
                e.setKickMessage("more testing");
                Queue.add(uuid);
            }
            
            return;
        }

        if(!Queue.isEmpty()){
            if(Queue.hasRoomInsideServer()){
                if(!Queue.isFirst(uuid)){
                    p.kickPlayer(Messages.get("not-first",uuid));
                    Queue.resetCooldown(uuid);

                }else{
                    //joins
                    Queue.delete(uuid);
                    p.sendMessage("you joined!");
                }
            }else{
                if(!Queue.hasPlayer(uuid)){
                    Queue.add(uuid);
                    p.kickPlayer("you are added to queue");
                }else{
                    p.kickPlayer("wait"+ Bukkit.getOnlinePlayers().size() + "---"+ Bukkit.getMaxPlayers());
                    Queue.resetCooldown(uuid);
                }

            }
        }else{
            if(!Queue.hasRoomInsideServer()){
                Queue.add(uuid);
                p.kickPlayer("you are added to queue, as the first person "+ Bukkit.getOnlinePlayers().size() + "---"+ Bukkit.getMaxPlayers());
            }
        }


    }

}

