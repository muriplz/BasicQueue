package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class Queue {

    public static LinkedHashMap<String,Long> queue = BasicQueue.queue;


    // TODO: config getter not working
    public static int cooldownOnMinutes = BasicQueue.getInstance().getConfig().getInt("queue-cooldown");



    public static int size(){
        return queue.size();
    }
    public static boolean isFirst(String uuid){
        return uuid.equals(getFirst());
    }
    public static String getFirst(){
        return queue.entrySet().iterator().next().getKey();
    }

    public static void add(String uuid){
        if(!queue.containsKey(uuid)){
            queue.put(uuid,System.currentTimeMillis());
        }
    }
    public static void resetCooldown(String uuid){
        for(String id : queue.keySet()){
            if(id.equals(uuid)){
                queue.replace(uuid,System.currentTimeMillis());
            }
        }
    }
    public static boolean hasPlayer(String uuid){
        return queue.containsKey(uuid);
    }
    public static boolean isEmpty(){
        return queue.isEmpty();
    }
    public static boolean hasRoomInsideServer(){
        return Bukkit.getMaxPlayers()>(Bukkit.getOnlinePlayers().size()-1);
    }
    public static void delete(String uuid){
        queue.remove(uuid);
    }
    public static int getPos(String uuid){
        if(!queue.containsKey(uuid)){
            return 0;
        }
        int i=0;
        for(String id : queue.keySet()){
            i++;
            if(id.equals(uuid)){
                break;
            }
        }
        return i;
    }

}
