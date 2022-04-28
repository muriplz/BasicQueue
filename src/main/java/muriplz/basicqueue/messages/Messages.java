package muriplz.basicqueue.messages;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.queue.Queue;

import java.util.List;

public class Messages {
    public static List<String> getMessages(){
        getMessages().add("default");
        getMessages().add("added");
        return getMessages();
    }
    public static String get(String path,String uuid){
        for(String message : getMessages()){
            if(message.equals(path)){
                return BasicQueue.getMessage(message).replace("%QUEUEPOS%", Queue.getPos(uuid)+"").replace("%COOLDOWNMINUTES%",Queue.cooldownOnMinutes+"").replace("%QUEUESIZE%",Queue.getSize()+"");
            }
        }
        return "Error 45nG7!";
    }

}
