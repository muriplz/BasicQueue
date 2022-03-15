package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.permissions.Permissions;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Queue {
    public static ListOrderedMap<String,Long> queue = BasicQueue.queue;

    public static int cooldownOnMinutes = BasicQueue.getInstance().getConfig().getInt("queue-cooldown");

    public static int prioritySize(){
        int i=0;
        if(isEmpty()){
            return 0;
        }
        do{
            if(!Queue.hasPriority(queue.get(i))){
                break;
            }
            i++;

        }while (i<queue.size());
        return i;
    }
    public static boolean hasPriority(String uuid){
        Player p = Bukkit.getPlayer(uuid);
        if(p==null) return false;
        return p.hasPermission(Permissions.queuePriority);
    }
    public static boolean hasRoomInsideServer(){
        return Bukkit.getMaxPlayers()>(Bukkit.getOnlinePlayers().size()-1);
    }
    public static boolean hasPlayer(String uuid){
        return queue.containsKey(uuid);
    }

    public static boolean isFirst(String uuid){
        return uuid.equals(queue.firstKey());
    }
    public static void addFirst(String uuid){
        Long millis = System.currentTimeMillis();
        if(!queue.containsKey(uuid)){
            queue.put(0,uuid,millis);
        }
    }
    public static void add(String uuid){
        Long millis = System.currentTimeMillis();
        if(!queue.containsKey(uuid)){
            if(hasPriority(uuid)){
                queue.put(prioritySize() + 1 , uuid , millis);
            }else{
                queue.put(uuid,millis);
            }
        }
    }
    public static void delete(String uuid){
        queue.remove(uuid);
    }

    public static void resetCooldown(String uuid){
        queue.replace(uuid,System.currentTimeMillis());
    }
    public static boolean isEmpty(){
        return queue.isEmpty();
    }
    public static int getPos(String uuid){
        if(!queue.containsKey(uuid)||isEmpty()){
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
