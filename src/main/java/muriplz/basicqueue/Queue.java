package muriplz.basicqueue;

import muriplz.basicqueue.permissions.TelepostPermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Queue {

    List<String> queue = muriplz.basicqueue.BasicQueue.queue;
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

    public boolean canJoinAndJoinQueue(Player p, PlayerJoinEvent e){

        // Checks if there is any room for a player, whether reserved slot or not
        if(!hasEnoughRoom(p)){
            return false;
        }

        // Checks if there's anyone on the queue
        if(!queue.isEmpty()){
            return false;
        }

        if(!isOnQueue(p)){
            addToQueue(p);
        }else{
            String uuid = queue.get(0);

            // Checks if the player is on the first position of the queue
            return p.getUniqueId().toString().equals(uuid);

        }

        return true;

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
            if(i==getReservedSlots()){
                break;
            }
        }
        return i;
    }
    public String kickMessageToQueue(Player p){
        String s = null;
        if(!isOnQueue(p)){
            s = "The server is full, therefore you have been placed in the queue.\n";
        }
        s = s + "You are in the position: ";
        return s;
    }
    public void addToQueue(Player p) {
        if( hasPriority(p) ){
            queue.add( getPriorityQueue() - 1 ,p.getUniqueId().toString() );
        }

        queue.add( getQueue() - 1 , p.getUniqueId().toString() );
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
        return queue.contains(p.getUniqueId().toString());
    }
}
