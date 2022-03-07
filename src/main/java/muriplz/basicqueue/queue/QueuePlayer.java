package muriplz.basicqueue.queue;

import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.UUID;

public class QueuePlayer {

    private Player p;

   public QueuePlayer(Player p){
       this.p = p;
       add();
   }

   public Player getPlayer(){
       return p;
   }
   public void add(){
       Queue.add(p);
   }
   public int getPos(){
       return Queue.getPos(p);
   }

}
