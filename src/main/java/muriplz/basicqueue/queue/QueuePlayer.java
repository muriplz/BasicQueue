package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.permissions.BasicQueuePermissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class QueuePlayer {
    static List<String> queue = BasicQueue.queue;

    private UUID uuid;
    private boolean hasPriority;

    private static final HashMap<String, QueuePlayer> playersOnQueue = new HashMap<>();


    public QueuePlayer(Player player){
        this(player.getUniqueId(), player.getName());
    }

    public QueuePlayer(@Nullable UUID uuid, @Nullable String name){
        if (uuid == null || name == null) return;
        playersOnQueue.put(name.toLowerCase(), this);
    }

    public static QueuePlayer getPlayer(Player player){
        return playersOnQueue.containsKey(player.getName().toLowerCase()) ? playersOnQueue.get(player.getName().toLowerCase()) : new QueuePlayer(player);
    }
    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public boolean hasPriority(){
        Player p = Bukkit.getPlayer(uuid);
        if(p==null) return false;
        return p.hasPermission(BasicQueuePermissions.queuePriority);
    }


    public boolean isOnQueue(){
        if(newqueue.isEmpty()){
           return false;
        }else if(newqueue.containsKey(this.playerId))
    }

    public void addToQueue() {

        if(queue.contains(this.playerId)){
            return;
        }

        if( hasPriority() ){
            queue.add( Queue.getPriorityQueue() - 1 ,p.getUniqueId().toString() );

        }else{
            queue.add( Queue.getQueue() - 1 , p.getUniqueId().toString() );
        }

        deleteFromQueueCooldown(p);
    }

    public void queueCooldown(){

    }
    public void resetCooldown(){

    }
    public int getQueue(){
        for(String s : queue){
            if()
        }
    }


}
