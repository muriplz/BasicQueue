package muriplz.basicqueue.listeners;

import muriplz.basicqueue.BasicQueue;
import muriplz.basicqueue.queue.DynamicQueue;
import muriplz.basicqueue.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class testCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if( ! ( sender instanceof Player)) {
            return false;
        }else {
            Player player = (Player) sender;
            DynamicQueue.getInstance().addToQueue(player);
            return true;
    }

    }
}
