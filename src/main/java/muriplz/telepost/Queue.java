package muriplz.telepost;

import muriplz.telepost.permissions.TelepostPermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;

public class Queue {

    HashMap<String,Integer> queue = muriplz.telepost.BasicQueue.queue;
    public static Queue instance;
    public BasicQueue BasicQueue;

    public static Queue getInstance() {
        return instance;
    }

    public Integer getQueue(){
        return queue.size() + 1;
    }
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
    public String nameNotationForQueue(Player p){
        return getPriority(p) + p.getUniqueId().toString();
    }
    public String getPriority(Player p){
        if(isPriority(p)){
            return "1";
        }else{
            return "0";
        }
    }
    public boolean isPriority(Player p){
        return p.hasPermission(TelepostPermissions.queuePriority);
    }

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
        if(!queue.containsKey(p.getName())){
            s = "The server is full, therefore you have been placed in the queue.\n";
        }
        s = s + "You are in the position: ";
        return s;
    }
    public void addToQueue(Player p) {
        if( isPriority(p) ){
            queue.put( nameNotationForQueue(p) , getPriorityQueue() );
        }
        queue.put( nameNotationForQueue(p) , getQueue() );
    }


}
