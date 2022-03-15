package muriplz.basicqueue.listeners;

import muriplz.basicqueue.messages.Messages;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class onQueueJoin implements Listener {

    @EventHandler
    public void onJoin( PlayerLoginEvent e ){

        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();

        // Essentials compatibility
        if( p.hasPermission("essentials.joinfullserver") ) {
            Queue.delete(uuid);
            return;
        }

        if( e.getResult() == PlayerLoginEvent.Result.KICK_OTHER || e.getResult() == PlayerLoginEvent.Result.KICK_BANNED || e.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST){
            return;
        }

        if( e.getResult() == PlayerLoginEvent.Result.KICK_FULL ){
            if( Queue.hasPlayer(uuid) ){
                e.setKickMessage(Messages.get("default", uuid));
                Queue.resetCooldown(uuid);
            }else{
                Queue.add(uuid);
                e.setKickMessage(Messages.get("added", uuid));
            }
            return;
        }
        if( !Queue.isEmpty() ){
            if(Queue.hasRoomInsideServer()){
                if( !Queue.isFirst(uuid) ){
                    p.kickPlayer(Messages.get("default",uuid));
                    Queue.resetCooldown(uuid);
                }else{
                    //joins
                    Bukkit.getConsoleSender().sendMessage("This is testing IMPORTANT");
                    Queue.delete(uuid);
                    p.sendMessage("you joined!, you were deleted from the queue");
                }
            }else{
                if( !Queue.hasPlayer(uuid) ){
                    Queue.add(uuid);
                    p.kickPlayer(Messages.get("added", uuid));
                }else{
                    p.kickPlayer(Messages.get("default", uuid));
                    Queue.resetCooldown(uuid);
                }
            }
        }else{
            if( !Queue.hasRoomInsideServer() ){
                Queue.add(uuid);
                p.kickPlayer(Messages.get("added", uuid));
            }else{
                Bukkit.getConsoleSender().sendMessage("This is testing IMPORTANT2");
                p.sendMessage("test");
                Queue.delete(uuid);
            }
        }
    }
}

