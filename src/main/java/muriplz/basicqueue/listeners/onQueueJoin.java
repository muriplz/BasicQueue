package muriplz.basicqueue.listeners;

import muriplz.basicqueue.messages.Messages;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class onQueueJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin( PlayerLoginEvent e ){

        String name = e.getPlayer().getName();

        String addedMessage = Messages.get("added", name);
        String defaultMessage = Messages.get("default", name);

        if(Queue.IS_ESTIMATION_ENABLED){
            addedMessage = addedMessage.concat("\n"+Messages.get("estimation",name));
            defaultMessage = defaultMessage.concat("\n"+Messages.get("estimation",name));
        }

        if( Queue.isEmpty() ){

            if( !Queue.hasRoomInsideServer(name) ){

                Queue.add(name);
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER,addedMessage);
                return;
            }

        }

        if(Queue.hasRoomInsideServer(name)){

            if( !Queue.canJoin(name) ){
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER,addedMessage);
                Queue.resetCooldown(name);
            }else{
                Queue.delete(name);
            }
        }else{

            if( !Queue.hasPlayer(name) ){
                Queue.add(name);
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER,addedMessage);

            }else{
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER,defaultMessage);

                Queue.resetCooldown(name);
            }
        }

    }
}

