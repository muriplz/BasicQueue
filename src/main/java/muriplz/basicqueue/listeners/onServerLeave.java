package muriplz.basicqueue.listeners;

import muriplz.basicqueue.permissions.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static muriplz.basicqueue.BasicQueue.queue;
import static muriplz.basicqueue.queue.Queue.prioritySize;

public class onServerLeave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();

        if(!p.hasPermission(Permissions.saveLeavingSlot)){
            return;
        }
        queue.put(prioritySize() + 1 , p.getUniqueId().toString() , System.currentTimeMillis());
    }
}
