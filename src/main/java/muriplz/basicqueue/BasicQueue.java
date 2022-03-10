package muriplz.basicqueue;

import io.github.thatsmusic99.configurationmaster.CMFile;
import muriplz.basicqueue.listeners.onPlayerJoin;
import muriplz.basicqueue.queue.Queue;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Map;

import static muriplz.basicqueue.queue.Queue.cooldownOnMinutes;

public class BasicQueue extends JavaPlugin{

    public static ListOrderedMap<String,Long> queue;

    private final String locale = getConfig().getString("locale");

    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();


    public static BasicQueue instance;

    FileConfiguration config = this.getConfig();


    @Override
    public void onEnable(){
        instance = this;
        queue = new ListOrderedMap<>();

        loadConfig();
        loadMessages();

        Bukkit.getServer().getPluginManager().registerEvents(new onPlayerJoin(),this);

        removeExceededPlayers();

        // Plugin activated at this point
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.GRAY+" The plugin has been activated. Version: "+ChatColor.GREEN+version);

    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.WHITE+" The plugin has been deactivated.");
    }

    private void loadConfig(){
        config.addDefault("queue-cooldown",3);
        config.addDefault("locale","en_en");
        config.options().copyDefaults(true);
        saveConfig();
    }

    private void loadMessages(){
        loadEnMessages();
        loadEsMessages();
    }



    public static YamlConfiguration getMessages(String locale){
        File messages = new File(getInstance().getDataFolder(), locale+".yml");
        return YamlConfiguration.loadConfiguration(messages);
    }
    public static String getMessage(String path){
        String s = getMessages(getInstance().locale).getString(path);
        if (s==null){
            return "Error 213Ej8 (file "+getInstance().locale+".yml not found)";
        }
        return ChatColor.translateAlternateColorCodes('&',s);
    }

    public static BasicQueue getInstance(){
        return instance;
    }

    private void removeExceededPlayers() {

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!queue.isEmpty()){
                    long timeStampMustBeMore = System.currentTimeMillis() - (cooldownOnMinutes*60*1000L);
                    for(Map.Entry<String,Long> p: queue.entrySet()){
                        if(p.getValue() < timeStampMustBeMore){
                            Queue.delete(p.getKey());
                        }
                    }
                }
            }
        }.runTaskTimer(this,cooldownOnMinutes*60*20L, 40);
    }

    private void loadEsMessages () {
        CMFile myMessagesFile = new CMFile(this, "es_es") {
            @Override
            public void loadDefaults() {
                addLink("Github","https://github.com/muriplz/BasicQueue" );
                addLink("Spigot", "ninguno todavía" );

                addComment("Mesajes de expulsión de la cola");
                addDefault("default","Estás dentro de la cola, tu posición es: &a%POSITION%&f.\nReconéctate en menos de %COOLDOWNMINUTES% minutos para conservar tu posición");
                addDefault("added","Has sido añadido a la cola, tu posición es: &a%POSITION%&f.\nReconéctate en menos de %COOLDOWNMINUTES% minutos para conservar tu posición");
            }

        };
        myMessagesFile.load();
    }

    private void loadEnMessages () {
        CMFile myMessagesFile = new CMFile(this, "en_en") {
            @Override
            public void loadDefaults() {
                addLink("Github","https://github.com/muriplz/BasicQueue" );
                addLink("Spigot", "none yet" );

                addComment("Queue kick Messages");
                addDefault("default","You are inside the queue, and your position is: &a%POSITION%&f.\nReconnect within %COOLDOWNMINUTES% minutes to keep your position");
                addDefault("added","You have been added to the queue, and your position is: &a%POSITION%&f.\nReconnect within %COOLDOWNMINUTES% minutes to keep your position");
            }

        };
        myMessagesFile.load();
    }

}
