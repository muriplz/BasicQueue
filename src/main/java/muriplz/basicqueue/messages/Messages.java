package muriplz.basicqueue.messages;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.queue.Queue;

public class Messages {
    public static String get(String path, String uuid){
        if(path.equals("not-first")){
            return BasicQueue.getMessage("not-first").replace("%POSITION%", Queue.getPos(uuid)+"").replace("%COOLDOWNMINUTES%",Queue.cooldownOnSeconds/60+"");
        }
        return null;
    }
}
