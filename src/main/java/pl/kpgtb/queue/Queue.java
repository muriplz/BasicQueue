package pl.kpgtb.queue;

import com.github.kpgtb.ktools.Ktools;
import com.github.kpgtb.ktools.manager.language.LanguageManager;
import com.github.kpgtb.ktools.manager.listener.ListenerManager;
import com.github.kpgtb.ktools.util.file.PackageUtil;
import com.github.kpgtb.ktools.util.wrapper.GlobalManagersWrapper;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.kpgtb.queue.manager.QueueManager;
import pl.kpgtb.queue.util.QueueWrapper;

public final class Queue extends JavaPlugin {

    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.adventure = BukkitAudiences.create(this);

        Ktools api = (Ktools) Bukkit.getPluginManager().getPlugin("Ktools");
        GlobalManagersWrapper apiManagers = api.getGlobalManagersWrapper();

        LanguageManager language = new LanguageManager(getDataFolder(),getConfig().getString("lang"), apiManagers.getDebugManager(), apiManagers.getGlobalLanguageManager());
        language.saveDefaultLanguage("lang/en.yml", this);
        language.refreshMessages();

        QueueManager queueManager = new QueueManager(this);

        QueueWrapper wrapper = new QueueWrapper(apiManagers,language,this,adventure,queueManager);
        PackageUtil packageUtil = new PackageUtil("pl.kpgtb.queue");

        ListenerManager listener = new ListenerManager(wrapper,getFile());
        listener.registerListeners(packageUtil.get("listener"));
    }

    @Override
    public void onDisable() {
        if(adventure != null) {
            adventure.close();
        }
    }
}
