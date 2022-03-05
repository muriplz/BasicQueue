package muriplz.basicqueue;

import io.github.thatsmusic99.configurationmaster.CMFile;
import muriplz.basicqueue.listeners.onPlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BasicQueue extends JavaPlugin{

    public static List<String> queue = new ArrayList<>();
    public static BasicQueue instance;

    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();


    @Override
    public void onEnable(){
        queue = null;
        instance = this;

        loadConfig();

        Bukkit.getServer().getPluginManager().registerEvents(new onPlayerJoin(),this);

        // Plugin activated at this point
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.GRAY+" The plugin has been activated. Version: "+ChatColor.GREEN+version);

    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.WHITE+" The plugin has been deactivated.");
    }

    public static BasicQueue getInstance(){
        return instance;
    }

    void loadConfig () {
        CMFile myConfigFile = new CMFile(this, "config") {
            @Override
            public void loadDefaults() {
                addLink("Github","https://github.com/muriplz/BasicQueue" );
                addLink("Spigot", "none yet" );

                addComment("Number of reserved slots. Only people with permission queue.reservedslot can join when the number of reserved slots is the same as the number of current available slots");
                addDefault("reserved-slots","2");

                addComment("The cooldown in minutes to kick a player from the queue.");
                addDefault("queue-cooldown","5");

            }

        };
        myConfigFile.load();
    }
}
