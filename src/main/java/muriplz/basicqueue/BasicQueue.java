package muriplz.basicqueue;

import io.github.thatsmusic99.configurationmaster.CMFile;
import muriplz.basicqueue.listeners.onPlayerJoin;
import muriplz.basicqueue.listeners.testCommand;
import muriplz.basicqueue.listeners.testCommand2;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static muriplz.basicqueue.queue.Queue.cooldownOnMinutes;

public class BasicQueue extends JavaPlugin{

    public static LinkedHashMap<String,Long> queue;

    private final String locale = getConfig().getString("locale");

    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();

    public static BasicQueue instance;

    FileConfiguration config = this.getConfig();


    @Override
    public void onEnable(){
        instance = this;
        queue = new LinkedHashMap<>();

        loadConfig();
        loadMessages();

        Bukkit.getConsoleSender().sendMessage(""+cooldownOnMinutes);

        getCommand("addtoqueue").setExecutor(new testCommand());
        getCommand("deletefromqueue").setExecutor(new testCommand2());
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

//    private void loadConfig () {
//        CMFile myConfigFile = new CMFile(this, "config") {
//            @Override
//            public void loadDefaults() {
//                addLink("Github","https://github.com/muriplz/BasicQueue" );
//                addLink("Spigot", "none yet" );
//
//                addComment("Number of reserved slots. Only people with permission queue.reservedslot can join\nwhen the number of reserved slots is the same as the number of current available slots");
//                addDefault("reserved-slots","0");
//
//                addComment("The cooldown in seconds to kick a player from the queue.");
//                addDefault("queue-cooldown","180");
//
//                addComment("Select language (English: en_en, Spanish: es_es)");
//                addDefault("locale","en_en");
//
//            }
//
//        };
//        myConfigFile.load();
//    }

    private void loadMessages(){
        loadEnMessages();
        loadEsMessages();
    }



    public static YamlConfiguration getMessages(String locale){
        File messages = new File(getInstance().getDataFolder(), locale+".yml");
        return YamlConfiguration.loadConfiguration(messages);
    }
    public static String getMessage(String path){
        return ChatColor.translateAlternateColorCodes('&',getMessages(getInstance().locale).getString(path));
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
