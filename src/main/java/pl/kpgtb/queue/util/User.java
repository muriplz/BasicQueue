package pl.kpgtb.queue.util;

import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class User {
    private final UUID uuid;
    private final Priority priority;
    private long lastLogin;
    private BukkitTask checker;

    public User(UUID uuid, Priority priority, long lastLogin, BukkitTask checker) {
        this.uuid = uuid;
        this.priority = priority;
        this.lastLogin = lastLogin;
        this.checker = checker;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Priority getPriority() {
        return priority;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public BukkitTask getChecker() {
        return checker;
    }

    public void setChecker(BukkitTask checker) {
        this.checker = checker;
    }
}
