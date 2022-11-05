package de.nopantscarl.purplespawner.commands;

import de.nopantscarl.purplespawner.main.PurpleSpawner;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public class ReloadConfig implements CommandExecutor {

    private PurpleSpawner purpleSpawner;
    public ReloadConfig(PurpleSpawner purpleSpawner) {
        this.purpleSpawner = purpleSpawner;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("purplespawner.reloadConfig")) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("reloadconfig")) {
                    this.purpleSpawner.fileManager.cfg = YamlConfiguration.loadConfiguration(this.purpleSpawner.fileManager.config);
                    PurpleSpawner.pr = ChatColor.translateAlternateColorCodes('&', this.purpleSpawner.fileManager.cfg.getString("prefix") + " §r");
                    PurpleSpawner.noPerm = ChatColor.translateAlternateColorCodes('&', this.purpleSpawner.fileManager.cfg.getString("noPermission"));
                    sender.sendMessage(PurpleSpawner.pr + this.purpleSpawner.fileManager.getTranslation("messages.configReloaded"));
                } else {
                    sender.sendMessage(PurpleSpawner.pr + "§c/purplespawner reloadconfig");
                }
            } else {
                sender.sendMessage(PurpleSpawner.pr + "§c/purplespawner reloadconfig");
            }
        } else {
            sender.sendMessage(PurpleSpawner.pr + PurpleSpawner.noPerm);
        }
        return true;
    }
}
