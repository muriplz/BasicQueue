package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class Queue {

    public static LinkedHashMap<Player,Long> queue = BasicQueue.queue;
    public static Long cooldownOnSeconds = 60L;

    public static int size(){
        return queue.size();
    }
    public static Player whoFirst(){
        return queue.entrySet().iterator().next().getKey();
    }
    public static void add(Player p){
        if(!queue.containsKey(p)){
            queue.put(p,cooldownOnSeconds);
        }
    }
    public static void resetCooldown(Player p){
        for(Map.Entry<Player,Long> q : queue.entrySet()){
            if(q.getKey().equals(p)){
                queue.replace(p,cooldownOnSeconds);
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
        for(Player q : queue.keySet()){
            if(q.equals(p)){
                queue.remove(p);
            }
        }
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
