package muriplz.basicqueue;

import io.github.thatsmusic99.configurationmaster.CMFile;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static muriplz.basicqueue.queue.Queue.cooldownOnSeconds;
import static muriplz.basicqueue.queue.Queue.queue;

public class BasicQueue extends JavaPlugin{

    public static BasicQueue instance;

    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();


    @Override
    public void onEnable(){
        instance = this;

        removeExceededPlayers();
        loadConfig();

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

                addComment("Number of reserved slots. Only people with permission queue.reservedslot can join\nwhen the number of reserved slots is the same as the number of current available slots");
                addDefault("reserved-slots","0");

                addComment("The cooldown in minutes to kick a player from the queue.");
                addDefault("queue-cooldown","2");

            }

        };
        myConfigFile.load();
    }

    private void removeExceededPlayers() {

        new BukkitRunnable() {

            @Override
            public void run() {
                // What you want to schedule goes here
                long timeStampMustBeMore = System.currentTimeMillis() - (cooldownOnSeconds*1000);
                for(Map.Entry<Player,Long> p: queue.entrySet()){
                    if(p.getValue() < timeStampMustBeMore){
                        Queue.delete(p.getKey());
                    }
                }
            }

        }.runTaskLater(this, 20);
    }

}
