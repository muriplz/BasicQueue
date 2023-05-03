package pl.kpgtb.queue.util;

import com.github.kpgtb.ktools.manager.language.LanguageManager;
import com.github.kpgtb.ktools.util.wrapper.GlobalManagersWrapper;
import com.github.kpgtb.ktools.util.wrapper.ToolsObjectWrapper;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import pl.kpgtb.queue.manager.QueueManager;

public class QueueWrapper extends ToolsObjectWrapper {
    private final QueueManager queueManager;

    public QueueWrapper(GlobalManagersWrapper globalManagersWrapper, LanguageManager languageManager, JavaPlugin plugin, BukkitAudiences adventure, QueueManager queueManager) {
        super(globalManagersWrapper, languageManager, plugin, adventure);
        this.queueManager = queueManager;
    }

    public QueueManager getQueueManager() {
        return queueManager;
    }
}
