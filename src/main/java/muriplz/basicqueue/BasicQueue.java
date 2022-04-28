package muriplz.basicqueue;

import io.github.thatsmusic99.configurationmaster.CMFile;
import muriplz.basicqueue.listeners.onQueueJoin;
import muriplz.basicqueue.listeners.onServerLeave;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Iterator;

import static muriplz.basicqueue.queue.Queue.cooldownOnMinutes;

public class BasicQueue extends JavaPlugin{

    public static ListOrderedMap<String,Long> queue;

    private final String locale = getConfig().getString("locale");

    private final String spigotLink = "https://www.spigotmc.org/resources/basicqueue.101072/";

    private final String githubLink = "https://github.com/muriplz/BasicQueue";
    PluginDescriptionFile pdffile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+pdffile.getName()+ChatColor.YELLOW+"]";
    public String version = pdffile.getVersion();

    public static BasicQueue instance;


    @Override
    public void onEnable(){
        instance = this;
        queue = new ListOrderedMap<>();

        loadConfig();
        loadMessages();

        Bukkit.getServer().getPluginManager().registerEvents(new onQueueJoin(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new onServerLeave(),this);

        removeExceededPlayers();

        // Plugin activated at this point
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.GRAY+" The plugin has been activated. v"+ChatColor.GREEN+version);

    }
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(name+ChatColor.WHITE+" The plugin has been deactivated.");
    }
    private void loadConfig () {
        CMFile myConfigFile = new CMFile(this, "config") {
            @Override
            public void loadDefaults() {
                addLink("Github",githubLink );
                addLink("Spigot", spigotLink );

                addComment("Select language. Available are downloaded upon plugin load\nIf you want to translate your own, you can create a new file and set here the name of that new file");
                addDefault("locale","en_en");

                addComment("Set the time in minutes after which a player is removed from the queue if he/she does not reconnect");
                addDefault("queue-cooldown",3);

                addComment("Set the amount of reserved slots for the queue. \nHaving queue.reservedslot permissions lets you use slots that can't be used by default");
                addDefault("reserved-slots",0);

                addComment("If on true, players leaving with queue.saveleavingslot permission will join the queue with priority");
                addDefault("leaving-slot-priority",true);
            }
        };
        myConfigFile.load();
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
                    Iterator<String> it = queue.keySet().iterator();
                    long timeStampMustBeMore = System.currentTimeMillis() - (cooldownOnMinutes*60*1000L);
                    while (it.hasNext())
                    {
                        Long timeStamp = queue.get(it.next());
                        if (timeStamp < timeStampMustBeMore){
                            it.remove();
                        }
                        if (it.hasNext()){
                            it.next();
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
                addLink("Github",githubLink );
                addLink("Spigot", spigotLink );
                addComment("Placeholders:\n%QUEUEPOS% - Posición en la cola\n%QUEUESIZE% - tamaño de la cola\n%QUEUECOOLDOWN% - Cooldown en minutos");
                addComment("Mesajes de expulsión de la cola:");
                addDefault("default","Estás dentro de la cola, tu posición es: &a%QUEUEPOS%&f de %QUEUESIZE%.\nReconéctate en menos de %COOLDOWNMINUTES% minutos para conservar tu posición");
                addDefault("added","Has sido añadido a la cola, tu posición es: &a%QUEUEPOS%&f de %QUEUESIZE%.\nReconéctate en menos de %COOLDOWNMINUTES% minutos para conservar tu posición");
            }
        };
        myMessagesFile.load();
    }
    private void loadEnMessages () {
        CMFile myMessagesFile = new CMFile(this, "en_en") {
            @Override
            public void loadDefaults() {
                addLink("Github",githubLink );
                addLink("Spigot", spigotLink );

                addComment("Available placeholders:\n%QUEUEPOS% - Position in queue\n%QUEUESIZE% - Size of queue\n%QUEUECOOLDOWN% - Cooldown in minutes");
                addComment("Queue kick Messages");
                addDefault("default","You are inside the queue, and your position is: &a%QUEUEPOS%&f out of %QUEUESIZE%.\nReconnect within %COOLDOWNMINUTES% minutes to keep your position");
                addDefault("added","You have been added to the queue, and your position is: &a%QUEUEPOS%&f out of %QUEUESIZE%.\nReconnect within %COOLDOWNMINUTES% minutes to keep your position");
            }
        };
        myMessagesFile.load();
    }
}
