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
     * The uuid as a String.
     *
     * @param p Player
     *
     * @return uuid as String
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

    public boolean canJoin(Player p, PlayerJoinEvent e){

        // Checks if there is any room for a player, whether reserved slot or not
        if(!hasEnoughRoom(p)){
            return false;
        }

        // Checks if there's anyone on the queue
        if(!queue.isEmpty()){
            return false;
        }

        if(!isOnQueue(p)){
            if(queue.isEmpty()){


            }else{
                addToQueue(p);
            }
        }else{

        }

        //
        return true;

    }

    public void tryToJoinBeingFirst(Player p){
        if(queue.inde)
    }

    
    public boolean hasEnoughRoom( Player p ){
        int maxPlayers = Bukkit.getServer().getMaxPlayers();

        if(p.hasPermission(TelepostPermissions.reservedSlots)){
            return !(onlinePlayersNumber() + getUsedReservedSlots() >= maxPlayers);
        }else{
            return !(onlinePlayersNumber() + getReservedSlots() >= maxPlayers);
        }

    }
    public Integer getReservedSlots(){
        return BasicQueue.getConfig().getInt("reserved-slots");
    }

    public Integer getUsedReservedSlots(){
        int i=0;
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(p.hasPermission(TelepostPermissions.reservedSlots)){
                i++;
            }
        }
        return i;
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
    public boolean isOnQueue(Player p){
        return queue.containsKey(p.getUniqueId().toString());
    }
}
