package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.permissions.BasicQueuePermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Queue {
    public static final int QUEUE_COOLDOWN = 2;

    static List<String> queue = BasicQueue.queue;


    /**
     * Gets the queue slot a new player joining should be in.
     *
     * @return the queue slot
     */
    public static int getQueue(){
        return queue.size() + 1;
    }

    /**
     * Gets the queue slot a new player joining with priority should be in.
     *
     * @return the queue slot
     */
    public static int getPriorityQueue(){
        int i = 1;
        for(String uuid : queue) {

            Player p = Bukkit.getServer().getPlayer(uuid);

            if(!QueuePlayer.getPlayer(p).hasPriority()){
                break;
            }
            i++;
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


    /**
     * Gets information about online players that have permission
     * to reserved slots
     *
     * @return the amount of online players that do not have access to reserved slots
     */
    public static int onlinePlayersNumber(){
        int i=0;
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(!p.hasPermission(BasicQueuePermissions.reservedSlots)){
                i++;
            }
        }
        return i;
    }




    public static boolean isOnQueue(Player p){
        return queue.contains(p.getUniqueId().toString());
    }
}
