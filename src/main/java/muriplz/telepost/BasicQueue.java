package muriplz.telepost;

import io.github.thatsmusic99.configurationmaster.CMFile;
import muriplz.telepost.listeners.onPlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class BasicQueue extends JavaPlugin implements Listener{

    public static HashMap<String,Integer> queue = new HashMap<>();
    public static BasicQueue instance;


    @Override
    public void onEnable(){
        queue = null;
        instance = this;

        Bukkit.getServer().getPluginManager().registerEvents(new onPlayerJoin(),this);

    }
    public BasicQueue getInstance(){
        return instance;
    }

    void loadConfig () {
        CMFile myConfigFile = new CMFile(this, "config") {
            @Override
            public void loadDefaults() {
                addLink("Github","none yet" );
                addLink("Spigot", "none yet" );

                addComment("Number of reserved slots. Only people with permission telep.reservedslot can join when the number of reserved slots is the same as the number of current available slots");
                addDefault("reserved-slots","2");

            }

        };
        myConfigFile.load();
    }
}
