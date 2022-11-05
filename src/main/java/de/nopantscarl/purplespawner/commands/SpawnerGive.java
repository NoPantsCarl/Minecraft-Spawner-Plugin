package de.nopantscarl.purplespawner.commands;

import de.nopantscarl.purplespawner.main.PurpleSpawner;
import de.nopantscarl.purplespawner.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SpawnerGive implements CommandExecutor {

    public PurpleSpawner purpleSpawner;
    public SpawnerGive(PurpleSpawner purpleSpawner) {
        this.purpleSpawner = purpleSpawner;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("purplespawner.givespawner")) {
            if (args.length == 3) {

                String name = args[0];
                Player target = Bukkit.getPlayer(name);

                if (target == null || !target.isOnline()) {
                    sender.sendMessage(PurpleSpawner.pr + purpleSpawner.fileManager.getTranslation("messages.targetNotOnline").replaceAll("%player%", name));
                    return true;
                }

                String amountString = args[2];
                int amount = 0;
                try {
                    amount = Integer.parseInt(amountString);
                } catch (NumberFormatException ex) {
                    sender.sendMessage(PurpleSpawner.pr + purpleSpawner.fileManager.getTranslation("messages.amountMustBeNumber"));
                    return true;
                }

                if (amount <= 0) {
                    sender.sendMessage(PurpleSpawner.pr + purpleSpawner.fileManager.getTranslation("messages.amountMustBeAtLeastOne"));
                    return true;
                }

                String type = args[1].toUpperCase();
                EntityType ent = EntityType.fromName(type);
                if(ent != null) {
                    String entityString = this.sanitizeEntityString(type);

                    ItemStack spawner = new ItemBuilder(Material.SPAWNER, amount)
                            .setDisPlayname("§5" + entityString + "Spawner")
                            .setDurability(ent.getTypeId())
                            .build();

                    if (hasInventorySlotFree(target)) {
                        target.getInventory().addItem(spawner);
                    } else {
                        target.getWorld().dropItemNaturally(target.getLocation().add(0, 1, 0), spawner);
                        sender.sendMessage(PurpleSpawner.pr + purpleSpawner.fileManager.getTranslation("messages.spawnerDroppedInvFull"));
                    }
                } else {
                    sender.sendMessage(PurpleSpawner.pr + purpleSpawner.fileManager.getTranslation("messages.invalidEntity"));
                }

            } else {
                sender.sendMessage(PurpleSpawner.pr + purpleSpawner.fileManager.getTranslation("messages.spawnerGiveHelp"));
            }
        } else {
            sender.sendMessage(PurpleSpawner.pr + PurpleSpawner.noPerm);
        }
        return true;
    }

    private boolean hasInventorySlotFree(Player player){
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if(item == null) {
                return true;
            }
        }

        return false;
    }

    private String sanitizeEntityString(String entityName) {
        String[] splices = entityName.split("_");
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < splices.length; i++) {
            result.append(splices[i].substring(0, 1).toUpperCase()).append(splices[i].substring(1).toLowerCase()).append(" ");
        }
        return result.toString();
    }
}
