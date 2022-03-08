package muriplz.basicqueue.queue;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class Queue {
    public static Long cooldownOnSeconds = 60L;
    public static LinkedHashMap<Player,Long> queue;

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
