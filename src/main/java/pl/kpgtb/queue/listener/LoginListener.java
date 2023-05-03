package pl.kpgtb.queue.listener;

import com.github.kpgtb.ktools.manager.language.LanguageLevel;
import com.github.kpgtb.ktools.manager.language.LanguageManager;
import com.github.kpgtb.ktools.manager.listener.Klistener;
import com.github.kpgtb.ktools.util.wrapper.ToolsObjectWrapper;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kpgtb.queue.manager.QueueManager;
import pl.kpgtb.queue.util.QueueWrapper;

import java.util.stream.Collectors;

public class LoginListener extends Klistener {
    private final QueueManager queueManager;
    private final LanguageManager language;
    private final JavaPlugin plugin;

    public LoginListener(ToolsObjectWrapper toolsObjectWrapper) {
        super(toolsObjectWrapper);
        this.language = toolsObjectWrapper.getLanguageManager();
        this.plugin = toolsObjectWrapper.getPlugin();
        this.queueManager = ((QueueWrapper)toolsObjectWrapper).getQueueManager();
    }

    @EventHandler
    public void onPreJoin(PlayerLoginEvent event) {
        if(event.getResult().equals(PlayerLoginEvent.Result.KICK_FULL)) {
            event.allow();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int freeSlots = Bukkit.getMaxPlayers() - (Bukkit.getOnlinePlayers().size() - 1);
        int reservedSlots = plugin.getConfig().getInt("reservedSlots");
        long realReserved = reservedSlots - Bukkit.getOnlinePlayers()
                .stream()
                .filter(p -> !p.getUniqueId().equals(player.getUniqueId()))
                .filter(p -> p.hasPermission("queue.reserved"))
                .count();
        if(realReserved < 0) {
            realReserved = 0;
        }

        if(player.hasPermission("queue.reserved") && freeSlots > 0 && realReserved > 0) {
            queueManager.removeFromQueue(player.getUniqueId());
            return;
        }

        long realFreeSlots = freeSlots - realReserved;

        if(realFreeSlots > 0 && queueManager.isQueueEmpty()) {
            queueManager.removeFromQueue(player.getUniqueId());
            return;
        }

        if(realFreeSlots > queueManager.getQueueSize()) {
            queueManager.removeFromQueue(player.getUniqueId());
            return;
        }

        int position = queueManager.getPosition(player.getUniqueId());
        int realPosition = position+1;

        if(position != -1 && realFreeSlots >= realPosition) {
            queueManager.removeFromQueue(player.getUniqueId());
            return;
        }

        // REFRESH
        position = queueManager.addToQueue(player.getUniqueId(), false);
        realPosition = position+1;

        String kickReason = String.join("\n", language.getString(
                LanguageLevel.PLUGIN,
                "kickMessage",
                Placeholder.unparsed("position", realPosition + ""),
                Placeholder.unparsed("size", queueManager.getQueueSize() + ""),
                Placeholder.unparsed("priority", queueManager.getPriorityInQueue(position).name())
        ));
        new BukkitRunnable() {
            @Override
            public void run() {
                player.kickPlayer(kickReason);
            }
        }.runTaskLater(plugin, 3);
    }
}
