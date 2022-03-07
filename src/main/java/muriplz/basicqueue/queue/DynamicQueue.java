package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.reservedslots.ReservedSlots;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DynamicQueue {

    static List<String> queue = BasicQueue.queue;


    public static void deleteFromQueueCooldown(Player p){


        long timeInSeconds = Queue.QUEUE_COOLDOWN * 60L;
        long timeInTicks = 20 * timeInSeconds;

        new BukkitRunnable() {

            @Override
            public void run() {
                queue.remove(p.getUniqueId().toString());
            }
        }.runTaskLater(BasicQueue.getInstance(), timeInTicks);



    }

    public static boolean canJoinAndJoinQueue(Player p, PlayerJoinEvent e){

        if(! (ReservedSlots.getUsedReservedSlots() < ReservedSlots.RESERVED_SLOTS ) && ReservedSlots.hasReservedPermission(p) && leftNonReservedSlots()==0){
            e.setJoinMessage("You have joined to a reserved slot!");
            return true;
        }

        // Checks if there's anyone on the queue
        if(!queue.isEmpty()){
            addToQueue(p);

            // Checks if is first on the queue and tries to join
            if(isFirst(p)){
                return hasEnoughRoom(p);
            }
        }else{
            // Checks if there is any room for a player, whether reserved slot or not
            if(!hasEnoughRoom(p)){
                addToQueue(p);
            }else{
                return true;
            }
        }
        return false;

    }

    public static boolean isFirst(Player p){
        // Checks if the player is on the first position of the queue
        return p.getUniqueId().toString().equals(queue.get(0));
    }



    public static boolean hasEnoughRoom(Player p){
        int maxPlayers = Bukkit.getServer().getMaxPlayers();
        return !(Queue.onlinePlayersNumber() + ReservedSlots.RESERVED_SLOTS >= maxPlayers);
    }
    public static Integer leftNonReservedSlots(){
        return Bukkit.getServer().getMaxPlayers() - Queue.onlinePlayersNumber() - ReservedSlots.RESERVED_SLOTS;
    }
    public static String kickMessageToQueue(Player p){
        String s = null;
        if(!Queue.isOnQueue(p)){
            s = "The server is full, therefore you have been placed in the queue.\n";
        }
        s = s + "You are in the position: ";
        return s;
    }
}
