package muriplz.basicqueue.listeners;

import muriplz.basicqueue.permissions.Permissions;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onServerLeave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();

        if(!p.hasPermission(Permissions.saveLeavingSlot)){
            return;
        }

        Queue.addFirst(p.getUniqueId().toString());

    }
}
