package pl.kpgtb.queue.manager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.kpgtb.queue.util.Priority;
import pl.kpgtb.queue.util.User;

import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

public class QueueManager {
    private final JavaPlugin plugin;

    private final LinkedList<User> queue;

    public QueueManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.queue = new LinkedList<>();
    }

    public int addToQueue(UUID uuid, boolean leaving) {
        int position = getPosition(uuid);
        if(position > -1) {
            User user = queue.get(position);
            user.getChecker().cancel();
            user.setLastLogin(System.currentTimeMillis());
            user.setChecker(prepareTask(uuid));
            return position;
        }

        Player player = Bukkit.getPlayer(uuid);
        Priority priority = leaving ? Priority.LEAVE : player.hasPermission("queue.priority") ? Priority.PRIORITY : Priority.NORMAL;
        int i = queue.size();

        if(!priority.equals(Priority.NORMAL)) {
            for (User user : queue) {
                if(priority.equals(Priority.LEAVE)) {
                    if(!user.getPriority().equals(Priority.LEAVE)) {
                        i = queue.indexOf(user);
                        break;
                    }
                    continue;
                }
                if(user.getPriority().equals(Priority.NORMAL)) {
                    i = queue.indexOf(user);
                    break;
                }
            }
        }

        queue.add(i, new User(uuid, priority, System.currentTimeMillis(), prepareTask(uuid)));
        return i;
    }

    public void removeFromQueue(UUID uuid) {
        int position = getPosition(uuid);
        if(position == -1) {
            return;
        }
        User user = queue.get(position);
        user.getChecker().cancel();
        queue.remove(user);
    }

    public int getPosition(UUID uuid) {
        Optional<User> userOpt = queue.stream().filter(u -> u.getUuid().equals(uuid)).findFirst();
        if(!userOpt.isPresent()) {
            return -1;
        }
        User user = userOpt.get();
        return queue.indexOf(user);
    }

    public Priority getPriorityInQueue(UUID uuid) {
        int pos = getPosition(uuid);
        if(pos == -1) {
            return null;
        }
        return queue.get(pos).getPriority();
    }

    public Priority getPriorityInQueue(int position) {
        return queue.get(position).getPriority();
    }

    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }

    public int getQueueSize() {
        return queue.size();
    }

    private BukkitTask prepareTask(UUID uuid) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                int position = getPosition(uuid);
                if(position == -1) {
                    return;
                }
                User user = queue.get(position);

                long endTime = user.getLastLogin() + (3L * 60L * 1000L);
                if(endTime <= System.currentTimeMillis()) {
                    queue.remove(user);
                }
            }
        }.runTaskLaterAsynchronously(plugin, ((3L * 60L * 20L) + 5L));
    }
}
