package muriplz.basicqueue.queue;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.permissions.BasicQueuePermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DynamicQueue {

    List<String> queue = muriplz.basicqueue.BasicQueue.queue;
    Queue Queue = muriplz.basicqueue.queue.Queue.getInstance();
    public static DynamicQueue instance;

    public static DynamicQueue getInstance(){
        return instance;
    }
    public void addToQueue(Player p) {
        if(queue.contains(p.getUniqueId().toString())){
            return;
        }
        if( Queue.hasPriority(p) ){
            queue.add( Queue.getPriorityQueue() - 1 ,p.getUniqueId().toString() );
        }

        queue.add( Queue.getQueue() - 1 , p.getUniqueId().toString() );
        deleteFromQueueCooldown(p);
    }
    public void deleteFromQueueCooldown(Player p){

        long queueCooldown = BasicQueue.getInstance().getConfig().getLong("queue-cooldown");

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                queue.remove(p.getUniqueId().toString());
                timer.cancel();
            }
        },20*(queueCooldown*60));

    }
    public boolean canJoinAndJoinQueue(Player p){


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

    public boolean isFirst(Player p){
        // Checks if the player is on the first position of the queue
        return p.getUniqueId().toString().equals(queue.get(0));
    }



    public boolean hasEnoughRoom( Player p ){
        int maxPlayers = Bukkit.getServer().getMaxPlayers();

        if(p.hasPermission(BasicQueuePermissions.reservedSlots)){
            return !(Queue.onlinePlayersNumber() + Queue.getUsedReservedSlots() >= maxPlayers);
        }else{
            return !(Queue.onlinePlayersNumber() + Queue.getReservedSlots() >= maxPlayers);
        }

    }
    public String kickMessageToQueue(Player p){
        String s = null;
        if(!Queue.isOnQueue(p)){
            s = "The server is full, therefore you have been placed in the queue.\n";
        }
        s = s + "You are in the position: ";
        return s;
    }
}
