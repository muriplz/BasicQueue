package muriplz.basicqueue.listeners;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.messages.Messages;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class onQueueJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin( PlayerLoginEvent e ){

        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();

        String addedMessage = Messages.get("added", uuid);
        String defaultMessage = Messages.get("default", uuid);

        if(Queue.estimatedTime){
            addedMessage = addedMessage.concat("\n"+Messages.get("estimation",uuid));
            defaultMessage = defaultMessage.concat("\n"+Messages.get("estimation",uuid));
        }
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
                e.setKickMessage(defaultMessage);
                Queue.resetCooldown(uuid);
            }else{
                Queue.add(uuid);
                e.setKickMessage(addedMessage);
            }
            return;
        }
        if( !Queue.isEmpty() ){
            if(Queue.hasRoomInsideServer()){
                if( !Queue.canJoin(uuid) ){
                    p.kickPlayer(defaultMessage);
                    Queue.resetCooldown(uuid);
                }else{
                    Queue.delete(uuid);
                }
            }else{
                if( !Queue.hasPlayer(uuid) ){
                    Queue.add(uuid);
                    p.kickPlayer(addedMessage);
                }else{
                    p.kickPlayer(defaultMessage);
                    Queue.resetCooldown(uuid);
                }
            }
        }else{
            if( !Queue.hasRoomInsideServer() ){
                Queue.add(uuid);
                p.kickPlayer(addedMessage);
            }
        }
    }
}

