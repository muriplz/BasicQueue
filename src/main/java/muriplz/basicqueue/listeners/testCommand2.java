package muriplz.basicqueue.listeners;

import muriplz.basicqueue.queue.Queue;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class testCommand2 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if( ! ( sender instanceof Player)) {
            return false;
        }else {
            Player player = (Player) sender;
            Queue.delete(player);
            return true;
        }

    }
}
