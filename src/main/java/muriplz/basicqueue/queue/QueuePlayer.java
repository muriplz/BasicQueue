package muriplz.basicqueue.queue;

import org.bukkit.entity.Player;

public class QueuePlayer {

    private final Player p;

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
