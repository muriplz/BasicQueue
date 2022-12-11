package muriplz.basicqueue.listeners;

import muriplz.basicqueue.messages.Messages;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class onQueueWhileFull implements Listener {

    @EventHandler
    public void onKick(AsyncPlayerPreLoginEvent e){

        if( ! (Bukkit.getOnlinePlayers().size() == Bukkit.getMaxPlayers() ) ){
            return;
        }

        String name = e.getName();

        if( Queue.hasPlayer(name) ){

            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,Messages.get("default", name));
            Queue.resetCooldown(name);

        }else{

            Queue.add(name);
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,Messages.get("added", name));
            Bukkit.getConsoleSender().sendMessage("laviehjaa");
        }
    }
}
