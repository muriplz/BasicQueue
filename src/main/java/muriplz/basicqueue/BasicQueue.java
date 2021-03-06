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
import java.util.UUID;

import static muriplz.basicqueue.queue.Queue.COOLDOWN_MINUTES;
import static org.bukkit.Bukkit.getPluginManager;

public class BasicQueue extends JavaPlugin{

    public static ListOrderedMap<UUID,Long> queue;

    private final String locale = getConfig().getString("locale");

    private final String spigotLink = "https://www.spigotmc.org/resources/basicqueue.101072/";

    private final String githubLink = "https://github.com/muriplz/BasicQueue";
    PluginDescriptionFile pdfFile = getDescription();
    public String name = ChatColor.YELLOW+"["+ChatColor.WHITE+ pdfFile.getName()+ChatColor.YELLOW+"]";
    public String version = pdfFile.getVersion();

    public static BasicQueue instance;


    @Override
    public void onEnable(){
        instance = this;
        queue = new ListOrderedMap<>();

        loadConfig();
        loadMessages();

        getPluginManager().registerEvents(new onQueueJoin(),this);
        getPluginManager().registerEvents(new onServerLeave(),this);

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

                addComment("Adds estimated time on kick message");
                addDefault("estimated-time",false);
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
                    Iterator<UUID> it = queue.keySet().iterator();
                    long timeStampMustBeMore = System.currentTimeMillis() - (COOLDOWN_MINUTES *60*1000L);
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
        }.runTaskTimer(this, COOLDOWN_MINUTES *60*20L, 40);
    }
    private void loadEsMessages () {
        CMFile myMessagesFile = new CMFile(this, "es_es") {
            @Override
            public void loadDefaults() {
                addLink("Github",githubLink );
                addLink("Spigot", spigotLink );
                addComment("Placeholders:\n%QUEUEPOS% - Posici??n en la cola\n%QUEUESIZE% - tama??o de la cola\n%QUEUECOOLDOWN% - Cooldown en minutos\n%ESTIMATION% - Tiempo estimado de espera");
                addComment("Mesajes de expulsi??n de la cola:");
                addDefault("default","Est??s dentro de la cola, tu posici??n es: &a%QUEUEPOS%&f de %QUEUESIZE%.\nRecon??ctate en menos de %COOLDOWNMINUTES% minutos para conservar tu posici??n");
                addDefault("added","Has sido a??adido a la cola, tu posici??n es: &a%QUEUEPOS%&f de %QUEUESIZE%.\nRecon??ctate en menos de %COOLDOWNMINUTES% minutos para conservar tu posici??n");
                addDefault("estimation","Tiempo estimado de espera: %ESTIMATION%&f minutos");

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

                addComment("Available placeholders:\n%QUEUEPOS% - Position in queue\n%QUEUESIZE% - Size of queue\n%QUEUECOOLDOWN% - Cooldown in minutes\n%ESTIMATION% - Estimated wait time in minutes");
                addComment("Queue kick Messages");
                addDefault("default","You are inside the queue, and your position is: &a%QUEUEPOS%&f out of %QUEUESIZE%.\nReconnect within %COOLDOWNMINUTES% minutes to keep your position");
                addDefault("added","You have been added to the queue, and your position is: &a%QUEUEPOS%&f out of %QUEUESIZE%.\nReconnect within %COOLDOWNMINUTES% minutes to keep your position");
                addDefault("estimation","Estimated waiting time: %ESTIMATION%&f minutes");
            }
        };
        myMessagesFile.load();
    }
}
