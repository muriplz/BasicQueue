package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.permissions.Permissions;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Queue {
    public static ListOrderedMap<UUID,Long> queue = BasicQueue.queue;

    public static boolean IS_ESTIMATION_ENABLED = BasicQueue.getInstance().getConfig().getBoolean("estimated-time");

    public static int COOLDOWN_MINUTES = BasicQueue.getInstance().getConfig().getInt("queue-cooldown");
    public static int RESERVED_SLOTS = BasicQueue.getInstance().getConfig().getInt("reserved-slots");

    public static int getSize() {
        return queue.size();
    }
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
    public static boolean hasReserved(UUID uuid){
        Player p = Bukkit.getPlayer(uuid);
        if(p==null) return false;
        return p.hasPermission(Permissions.reservedSlot);
    }
    public static boolean hasPriority(UUID uuid){
        Player p = Bukkit.getPlayer(uuid);
        if(p==null) return false;
        return p.hasPermission(Permissions.queuePriority);
    }
    public static boolean hasRoomInsideServer(){
        return Bukkit.getMaxPlayers()>(Bukkit.getOnlinePlayers().size()-1);
    }
    public static boolean hasPlayer(UUID uuid){
        return queue.containsKey(uuid);
    }

    public static void add(UUID uuid){
        Long millis = System.currentTimeMillis();
        if(!queue.containsKey(uuid)){
            if(hasPriority(uuid)){
                queue.put(prioritySize() + 1 , uuid , millis);
            }else{
                queue.put(uuid,millis);
            }
        }
    }
    public static void delete(UUID uuid){
        queue.remove(uuid);
    }

    public static void resetCooldown(UUID uuid){
        queue.replace(uuid,System.currentTimeMillis());
    }
    public static boolean isEmpty(){
        return queue.isEmpty();
    }
    public static int getPos(UUID uuid){
        if(!queue.containsKey(uuid)||isEmpty()){
            return 0;
        }
        int i=0;
        for(UUID id : queue.keySet()){
            i++;
            if(id.equals(uuid)){
                break;
            }
        }
        return i;
    }
    public static boolean canJoin(UUID uuid){
        return getRoomOnServer(uuid) > getPos(uuid);
    }
    public static int getRoomOnServer(UUID uuid){
        Player p = Bukkit.getPlayer(uuid);
        int maxPlayers = Bukkit.getMaxPlayers();
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        if(!hasReserved(uuid)){
            maxPlayers-= RESERVED_SLOTS;
        }
        return maxPlayers-onlinePlayers;
    }
    public static int getEstimation(UUID uuid){
        return getPos(uuid)* COOLDOWN_MINUTES;
    }
}
