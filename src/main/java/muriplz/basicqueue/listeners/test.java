package muriplz.basicqueue.listeners;

import muriplz.basicqueue.queue.Queue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class test implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        int size = Queue.getInstance().getQueue() - 1;
        String s = "Queue size: "+ size;
        e.getPlayer().sendMessage(s);
    }
}
