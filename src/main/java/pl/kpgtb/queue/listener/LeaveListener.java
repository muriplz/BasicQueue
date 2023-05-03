package pl.kpgtb.queue.listener;

import com.github.kpgtb.ktools.manager.listener.Klistener;
import com.github.kpgtb.ktools.util.wrapper.ToolsObjectWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.kpgtb.queue.manager.QueueManager;
import pl.kpgtb.queue.util.QueueWrapper;

public class LeaveListener extends Klistener {
    private final QueueManager queueManager;

    public LeaveListener(ToolsObjectWrapper toolsObjectWrapper) {
        super(toolsObjectWrapper);
        this.queueManager = ((QueueWrapper)toolsObjectWrapper).getQueueManager();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("queue.queueLeaving")) {
            queueManager.addToQueue(player.getUniqueId(), true);
        }
    }
}
