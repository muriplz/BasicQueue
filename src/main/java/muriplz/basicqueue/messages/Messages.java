package muriplz.basicqueue.messages;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.queue.Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Messages {
    public static List<String> getMessages(){
        List<String> paths = new ArrayList<>();
        paths.add("default");
        paths.add("added");
        paths.add("estimation");
        return paths;
    }
    public static String get(String path, UUID uuid){
        String temp = "";
        for(String message : getMessages()){
            if(message.equals(path)){
                temp = BasicQueue.getMessage(message).replace("%QUEUEPOS%", Queue.getPos(uuid)+"");
                temp = temp.replace("%COOLDOWNMINUTES%",Queue.COOLDOWN_MINUTES +"");
                temp = temp.replace("%QUEUESIZE%",Queue.getSize()+"");
                return temp.replace("%ESTIMATION%",Queue.getEstimation(uuid)+"");
            }
        }
        return "Error 45nG7!";
    }

}
