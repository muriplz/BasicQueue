package muriplz.basicqueue;

import muriplz.basicqueue.permissions.TelepostPermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Queue {

    HashMap<String,Integer> queue = muriplz.basicqueue.BasicQueue.queue;
    public static Queue instance;
    public BasicQueue BasicQueue;

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
        for(Map.Entry<String, Integer> entry : queue.entrySet()) {
            String uuid = entry.getKey();
            Integer queuePos = entry.getValue();

            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p==null) break;

            if(!p.hasPermission(TelepostPermissions.queuePriority)){
                break;
            }
        }
        return i;
    }

    /**
     * Adds a "1" or a "0" to the first position of the uuid as a String.
     *
     * @param p Player
     *
     * @return formatted String that has information about priority
     */
    public String nameNotationForQueue(Player p){
        return p.getUniqueId().toString();
    }

    /**
     * Kicks player with custom kick message.
     *
     * @param p Player
     *
     * @return 1 or a 0, it being Priority and Non-priority respectively
     */
    public Integer getPriority(Player p){
        if(hasPriority(p)){
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * Gets information about the player's priority permission.
     *
     * @param p Player
     *
     * @return turns true if the player has priority permission
     */
    public boolean hasPriority(Player p){
        return p.hasPermission(TelepostPermissions.queuePriority);
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

    
    public boolean canJoin(Player p , PlayerJoinEvent e ){
        int maxPlayers = Bukkit.getServer().getMaxPlayers();

        if( onlinePlayersNumber() + getReservedSlots() == maxPlayers ){
            if(p.hasPermission(TelepostPermissions.reservedSlots)){
                e.setJoinMessage("You have joined to a reserved slot!");
                return true;
            }

        }
        return false;

    }
    public Integer getReservedSlots(){
        return BasicQueue.getConfig().getInt("reserved-slots");
    }
    public String kickMessageToQueue(Player p){
        String s = null;
        if(!queue.containsKey(p.getUniqueId().toString())){
            s = "The server is full, therefore you have been placed in the queue.\n";
        }
        s = s + "You are in the position: ";
        return s;
    }
    public void addToQueue(Player p) {
        if( hasPriority(p) ){
            queue.put( nameNotationForQueue(p) , getPriority(p) );
        }
        queue.put( nameNotationForQueue(p) , getPriority(p) );
        deleteFromQueueCooldown(p);
    }
    public void deleteFromQueueCooldown(Player p){

        long queueCooldown = BasicQueue.getConfig().getLong("queue-cooldown");

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                queue.remove(p.getUniqueId().toString());
                timer.cancel();
            }
        },20*(queueCooldown*60));

    }
}
