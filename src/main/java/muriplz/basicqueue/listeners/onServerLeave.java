package muriplz.basicqueue.listeners;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.permissions.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static muriplz.basicqueue.BasicQueue.queue;
import static muriplz.basicqueue.queue.Queue.prioritySize;

public class onServerLeave implements Listener {

    public static boolean leavingSlotPriority = BasicQueue.getInstance().getConfig().getBoolean("leaving-slot-priority");
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();

        if(!p.hasPermission(Permissions.saveLeavingSlot)){
            return;
        }
        if(leavingSlotPriority){
            queue.put(prioritySize() , p.getUniqueId() , System.currentTimeMillis());
        }else{
            queue.put( p.getUniqueId() , System.currentTimeMillis());
        }
    }
}
