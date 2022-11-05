package de.nopantscarl.purplespawner.main;

import de.nopantscarl.purplespawner.commands.ReloadConfig;
import de.nopantscarl.purplespawner.commands.SpawnerGive;
import de.nopantscarl.purplespawner.listeners.BlockBreak;
import de.nopantscarl.purplespawner.listeners.PrepareAnvil;
import de.nopantscarl.purplespawner.listeners.BlockPlace;
import de.nopantscarl.purplespawner.listeners.PlayerInteract;
import de.nopantscarl.purplespawner.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PurpleSpawner extends JavaPlugin {

    private PluginManager pm;
    private ConsoleCommandSender cs;

    public FileManager fileManager;

    public static String pr;
    public static String noPerm;
    public static PurpleSpawner instance;

    @Override
    public void onEnable() {
        instance = this;

        this.fileManager = new FileManager(this);

        this.register();

        sendConsoleMessage("§e============== §5PurpleSpawner §e=============");
        sendConsoleMessage(" ");
        sendConsoleMessage("§3Author§8: §a" + this.getDescription().getAuthors());
        sendConsoleMessage("§3Version§8: §a" + this.getDescription().getVersion());
        sendConsoleMessage(" ");
        sendConsoleMessage("§a§lLOADED");
        sendConsoleMessage(" ");
        sendConsoleMessage("§e============== §5PurpleSpawner §e=============");
    }

    @Override
    public void onDisable() {
        sendConsoleMessage("§e============== §5PurpleSpawner §e=============");
        sendConsoleMessage(" ");
        sendConsoleMessage("§3Author§8: §a" + this.getDescription().getAuthors());
        sendConsoleMessage("§3Version§8: §a" + this.getDescription().getVersion());
        sendConsoleMessage(" ");
        sendConsoleMessage("§4§lUNLOADED");
        sendConsoleMessage(" ");
        sendConsoleMessage("§e============== §5PurpleSpawner §e=============");
    }

    private void register() {
        this.pm = Bukkit.getPluginManager();
        this.cs = Bukkit.getConsoleSender();

        this.getCommand("spawnergive").setExecutor(new SpawnerGive(this));
        this.getCommand("purplespawner").setExecutor(new ReloadConfig(this));

        this.pm.registerEvents(new BlockPlace(this), this);
        this.pm.registerEvents(new BlockBreak(this), this);
        this.pm.registerEvents(new PrepareAnvil(this), this);
        this.pm.registerEvents(new PlayerInteract(this), this);

        pr = ChatColor.translateAlternateColorCodes('&', this.fileManager.cfg.getString("prefix") + " §r");
        noPerm = ChatColor.translateAlternateColorCodes('&', this.fileManager.cfg.getString("noPermission"));
    }

    private void sendConsoleMessage(String msg) {
        this.cs.sendMessage(msg);
    }

    public static PurpleSpawner getInstance() {
        return instance;
    }
}
