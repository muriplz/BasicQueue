package muriplz.basicqueue.messages;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.queue.Queue;

import java.util.List;

public class Messages {
    public static List<String> getMessages(){
        getMessages().add("default");
        getMessages().add("added");
        getMessages().add("estimation");
        return getMessages();
    }
    public static String get(String path,String uuid){
        String temp = "";
        for(String message : getMessages()){
            if(message.equals(path)){
                temp = BasicQueue.getMessage(message).replace("%QUEUEPOS%", Queue.getPos(uuid)+"");
                temp = temp.replace("%COOLDOWNMINUTES%",Queue.cooldownOnMinutes+"");
                temp = temp.replace("%QUEUESIZE%",Queue.getSize()+"");
                return temp.replace("%ESTIMATION%",Queue.getEstimation(uuid)+"");
            }
        }
        return "Error 45nG7!";
    }

}
