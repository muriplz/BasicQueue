package muriplz.basicqueue.queue;

import muriplz.basicqueue.permissions.BasicQueuePermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Queue {

    List<String> queue = muriplz.basicqueue.BasicQueue.queue;
    public static Queue instance;
    public muriplz.basicqueue.BasicQueue BasicQueue;

    public static Queue getInstance() {
        return instance;
    }

    /**
     * Gets the queue slot a new player joining should be in.
     *
     * @return the queue slot
     */
    public Integer getQueue(){
        return queue.size() + 1;
    }

    /**
     * Gets the queue slot a new player joining with priority should be in.
     *
     * @return the queue slot
     */
    public Integer getPriorityQueue(){
        int i = 1;
        for(String uuid : queue) {

            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p==null) break;

            if(!hasPriority(p)){
                break;
            }
        }
        return i;
    }


    /**
     * Gets information about the player's priority permission.
     *
     * @param p Player
     *
     * @return turns true if the player has priority permission
     */
    public boolean hasPriority(Player p){
        return p.hasPermission(BasicQueuePermissions.queuePriority);
    }

    /**
     * Gets information about online players that have permission
     * to reserved slots
     *
     * @return the amount of online players that do not have access to reserved slots
     */
    public Integer onlinePlayersNumber(){
        return Bukkit.getServer().getOnlinePlayers().size();
    }

    public boolean hasReservedPermission(Player p){
        return p.hasPermission(BasicQueuePermissions.reservedSlots);
    }

    public Integer getReservedSlots(){
        return BasicQueue.getConfig().getInt("reserved-slots");
    }

    public Integer getUsedReservedSlots(){
        int i=0;
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(p.hasPermission(BasicQueuePermissions.reservedSlots)){
                i++;
            }
            if(i==getReservedSlots()){
                break;
            }
        }
        return i;
    }


    public boolean isOnQueue(Player p){
        return queue.contains(p.getUniqueId().toString());
    }
}
