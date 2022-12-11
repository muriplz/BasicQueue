package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.permissions.Permissions;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

import static muriplz.basicqueue.BasicQueue.api;

public class Queue {
    public static ListOrderedMap<String,Long> queue = BasicQueue.queue;

    public static boolean IS_ESTIMATION_ENABLED = BasicQueue.getInstance().getConfig().getBoolean("estimated-time");

    public static int COOLDOWN_MINUTES = BasicQueue.getInstance().getConfig().getInt("queue-cooldown");
    public static int RESERVED_SLOTS = BasicQueue.getInstance().getConfig().getInt("reserved-slots");

    public static int getSize() {
        return queue.size();
    }
    public static int prioritySize(){
        int i=0;
        if(isEmpty()){
            return 1;
        }
        do{
            if(!Queue.hasPriority(queue.get(i))){
                break;
            }
            i++;

        }while (i<queue.size());
        return i;
    }
    public static boolean hasReserved(String name){
        User user = api.getUserManager().getUser(name);
        if(user==null) return false;
        return user.getCachedData().getPermissionData().checkPermission(Permissions.reservedSlot).asBoolean();
    }
    public static boolean hasPriority(String name){
        User user = api.getUserManager().getUser(name);
        if(user==null) return false;
        return user.getCachedData().getPermissionData().checkPermission(Permissions.queuePriority).asBoolean();
    }
    public static boolean hasRoomInsideServer(String name){

        if(hasReserved(name)){

            return Bukkit.getMaxPlayers()>(Bukkit.getOnlinePlayers().size()-1);
        }

        return Bukkit.getMaxPlayers()>(Bukkit.getOnlinePlayers().size()-1)+RESERVED_SLOTS;
    }
    public static boolean hasPlayer(String name){
        return queue.containsKey(name);
    }

    public static void add(String name){
        Long millis = System.currentTimeMillis();
        if(!queue.containsKey(name)){
            if(hasPriority(name)){
                queue.put(prioritySize() , name , millis);
            }else{
                queue.put(name,millis);
            }
        }
    }
    public static void delete(String name){
        queue.remove(name);
    }

    public static void resetCooldown(String name){
        queue.replace(name,System.currentTimeMillis());
    }
    public static boolean isEmpty(){
        return queue.isEmpty();
    }
    public static int getPos(String name){
        if(!queue.containsKey(name)||isEmpty()){
            return 1;
        }
        int i=0;
        for(String n : queue.keySet()){
            i++;
            if(n.equals(name)){
                break;
            }
        }
        return i;
    }
    public static boolean canJoin(String name){
        return getRoomOnServer(name) >= getPos(name);
    }
    public static int getRoomOnServer(String name){
        int maxPlayers = Bukkit.getMaxPlayers();
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        if(!hasReserved(name)){
            maxPlayers-=RESERVED_SLOTS;
        }
        return maxPlayers-onlinePlayers;
    }
    public static int getEstimation(String name){
        return getPos(name)* COOLDOWN_MINUTES;
    }
}
