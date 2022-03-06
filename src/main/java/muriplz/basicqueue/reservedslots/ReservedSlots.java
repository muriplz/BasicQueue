package muriplz.basicqueue.reservedslots;

import muriplz.basicqueue.permissions.BasicQueuePermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReservedSlots {
    public static final int RESERVED_SLOTS = 0;

    public static boolean hasReservedPermission(Player p){
        return p.hasPermission(BasicQueuePermissions.reservedSlots);
    }

    public static int getUsedReservedSlots(){
        int i=0;
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            if(p.hasPermission(BasicQueuePermissions.reservedSlots)){
                i++;
            }
            if(i == ReservedSlots.RESERVED_SLOTS){
                break;
            }
        }
        return i;
    }

}
