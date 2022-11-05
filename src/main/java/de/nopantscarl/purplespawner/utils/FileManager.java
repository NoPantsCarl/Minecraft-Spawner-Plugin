package de.nopantscarl.purplespawner.utils;

import de.nopantscarl.purplespawner.main.PurpleSpawner;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public File config;
    public FileConfiguration cfg;

    //Create instance of the FileManager
    public FileManager(PurpleSpawner purpleSpawner) {
        this.config = new File("plugins/PurpleSpawner/config.yml");
        this.cfg = YamlConfiguration.loadConfiguration(this.config);

        this.setConfigDefaults();
    }

    // Set defaults to config to customize message
    public void setConfigDefaults() {
        this.cfg.options().copyDefaults(true);
        this.cfg.addDefault("prefix", "&8▌ &5PurpleSpawner &7»");
        this.cfg.addDefault("noPermission", "&cYou don't have the correct permission to perform this action!");
        this.cfg.addDefault("messages.spawnerGiveHelp", "&c/spawnergive <Player> <Type> <Amount>");
        this.cfg.addDefault("messages.targetNotOnline", "&cThe target Player '%player%' is not online.");
        this.cfg.addDefault("messages.amountMustBeNumber", "&cThe amount must be a number.");
        this.cfg.addDefault("messages.invalidEntity", "&cThat is not a valid entity type.");
        this.cfg.addDefault("messages.amountMustBeAtLeastOne", "&cThe amount must be at least 1");
        this.cfg.addDefault("messages.spawnerDroppedInvFull", "&cThe players inventory is full, the spawner has been dropped at his location.");
        this.cfg.addDefault("messages.mineNoSilkTouch", "&cYou need a silk touch pickaxe to break a monster spawner.");
        this.cfg.addDefault("messages.mineNoPermission", "&cYou don't have permission to mine monster spawners.");
        this.cfg.addDefault("messages.eggChangeNoPermission", "&cYou don't have permission to change the mob of a monster spawner.");
        this.cfg.addDefault("messages.configReloaded", "&aThe config has been reloaded.");
        this.saveConfig();
    }

    public String getTranslation(String translationKey) {
        if(this.cfg.getString(translationKey) != null) {
            return ChatColor.translateAlternateColorCodes('&', this.cfg.getString(translationKey));
        }
        return "Translation Error";
    }

    //Save config file
    public void saveConfig() {
        try {
            this.cfg.save(this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
