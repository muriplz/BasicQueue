package muriplz.basicqueue;

import io.github.thatsmusic99.configurationmaster.CMFile;
import muriplz.basicqueue.listeners.onPlayerJoin;
import muriplz.basicqueue.listeners.test;
import muriplz.basicqueue.listeners.testCommand;
import muriplz.basicqueue.listeners.testCommand2;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static muriplz.basicqueue.queue.Queue.cooldownOnSeconds;

public class BasicQueue extends JavaPlugin{

    public static LinkedHashMap<Player,Long> queue;

    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();

    public static BasicQueue instance;


    @Override
    public void onEnable(){
        instance = this;
        queue = new LinkedHashMap<>();

        loadConfig();
        loadMessages();

        Bukkit.getConsoleSender().sendMessage(""+cooldownOnSeconds);

        getCommand("addtoqueue").setExecutor(new testCommand());
        getCommand("deletefromqueue").setExecutor(new testCommand2());
        Bukkit.getServer().getPluginManager().registerEvents(new onPlayerJoin(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new test(),this);

        removeExceededPlayers();

        // Plugin activated at this point
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.GRAY+" The plugin has been activated. Version: "+ChatColor.GREEN+version);

    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.WHITE+" The plugin has been deactivated.");
    }


    void loadConfig () {
        CMFile myConfigFile = new CMFile(this, "config") {
            @Override
            public void loadDefaults() {
                addLink("Github","https://github.com/muriplz/BasicQueue" );
                addLink("Spigot", "none yet" );

                addComment("Number of reserved slots. Only people with permission queue.reservedslot can join\nwhen the number of reserved slots is the same as the number of current available slots");
                addDefault("reserved-slots","0");

                addComment("The cooldown in seconds to kick a player from the queue.");
                addDefault("queue-cooldown","180");

            }

        };
        myConfigFile.load();
    }

    void loadMessages () {
        CMFile myMessagesFile = new CMFile(this, "messages") {
            @Override
            public void loadDefaults() {
                addLink("Github","https://github.com/muriplz/BasicQueue" );
                addLink("Spigot", "none yet" );

                addDefault("not-first","You are inside the queue, and your position is &a%POSITION%&F.\nReconnect within %COOLDOWNMINUTES% minutes to keep your position");

            }

        };
        myMessagesFile.load();
    }

    public static YamlConfiguration getMessages(){
        File messages = new File(getInstance().getDataFolder(), "messages.yml");
        return YamlConfiguration.loadConfiguration(messages);
    }
    public static String getMessage(String path){
        return ChatColor.translateAlternateColorCodes('&',getMessages().getString(path));
    }

    public static BasicQueue getInstance(){
        return instance;
    }

    private void removeExceededPlayers() {

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!queue.isEmpty()){
                    long timeStampMustBeMore = System.currentTimeMillis() - (cooldownOnSeconds*1000);
                    for(Map.Entry<Player,Long> p: queue.entrySet()){
                        if(p.getValue() < timeStampMustBeMore){
                            Queue.delete(p.getKey());
                        }
                    }
                }
            }
        }.runTaskTimer(this,20, 20);
    }

}
