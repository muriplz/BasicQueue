package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class Queue {

    public static LinkedHashMap<Player,Long> queue = BasicQueue.queue;

    public static Long cooldownOnSeconds = 180L;
    // TODO: config getter not working
    // public static Long cooldownOnSeconds = BasicQueue.getInstance().getConfig().getLong("queue-cooldown");



    public static int size(){
        return queue.size();
    }
    public static boolean isFirst(Player p){
        return p.getUniqueId().toString().equals(getFirst());
    }
    public static String getFirst(){
        return queue.entrySet().iterator().next().getKey().getUniqueId().toString();
    }

    public static void add(Player p){
        if(!queue.containsKey(p)){
            queue.put(p,System.currentTimeMillis());
        }
    }
    public static void resetCooldown(Player p){
        for(Player q : queue.keySet()){
            if(q.equals(p)){
                queue.replace(p,System.currentTimeMillis());
            }
        }
    }
    public static boolean hasPlayer(Player p){
        return queue.containsKey(p);
    }
    public static boolean isEmpty(){
        return queue.isEmpty();
    }
    public static boolean hasRoomInsideServer(){
        return Bukkit.getMaxPlayers()>Bukkit.getOnlinePlayers().size();
    }
    public static void delete(Player p){
        queue.remove(p);
    }
    public static int getPos(Player p){
        if(!queue.containsKey(p)){
            return 0;
        }
        int i=0;
        for(Player q : queue.keySet()){
            i++;
            if(p.equals(q)){
                break;
            }
        }
        return i;
    }

}
