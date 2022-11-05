package de.nopantscarl.purplespawner.listeners;

import de.nopantscarl.purplespawner.main.PurpleSpawner;
import de.nopantscarl.purplespawner.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {

    private PurpleSpawner purpleSpawner;

    public BlockBreak(PurpleSpawner purpleSpawner) {
        this.purpleSpawner = purpleSpawner;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if (p.getGameMode() != GameMode.CREATIVE) {
            if (e.getBlock().getType() == Material.SPAWNER) {
                e.setCancelled(true);
                if (p.getInventory().getItemInMainHand().getType() == Material.WOODEN_PICKAXE
                        || p.getInventory().getItemInMainHand().getType() == Material.STONE_PICKAXE
                        || p.getInventory().getItemInMainHand().getType() == Material.GOLDEN_PICKAXE
                        || p.getInventory().getItemInMainHand().getType() == Material.IRON_PICKAXE
                        || p.getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE
                        || p.getInventory().getItemInMainHand().getType() == Material.NETHERITE_PICKAXE) {
                    if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                        if (p.getInventory().getItemInMainHand().getItemMeta().getEnchants().containsKey(Enchantment.SILK_TOUCH)) {
                            if (p.hasPermission("purplespawner.mine")) {

                                CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
                                String entityName = this.sanitizeEntityString(spawner.getSpawnedType().name());

                                ItemStack toDrop = new ItemBuilder(Material.SPAWNER, 1)
                                        .setDurability(spawner.getSpawnedType().getTypeId())
                                        .setDisPlayname("§5" + entityName + "Spawner")
                                        .build();

                                e.setCancelled(false);

                                if(!hasAvailableSlot(p.getInventory())) {
                                    p.getWorld().dropItemNaturally(e.getBlock().getLocation(), toDrop);
                                    return;
                                }

                                p.getInventory().addItem(toDrop);

                            } else {
                                p.sendMessage(PurpleSpawner.pr + this.purpleSpawner.fileManager.getTranslation("messages.mineNoPermission"));
                                e.setCancelled(true);
                            }
                        } else {
                            p.sendMessage(PurpleSpawner.pr + this.purpleSpawner.fileManager.getTranslation("messages.mineNoSilkTouch"));
                            e.setCancelled(true);
                        }
                    }
                } else {
                    p.sendMessage(PurpleSpawner.pr + this.purpleSpawner.fileManager.getTranslation("messages.mineNoSilkTouch"));
                    e.setCancelled(true);
                }
            }
        }

    }
    private String sanitizeEntityString(String entityName) {
        String[] splices = entityName.split("_");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < splices.length; i++) {
            result.append(splices[i].substring(0, 1).toUpperCase()).append(splices[i].substring(1).toLowerCase()).append(" ");
        }
        return result.toString();
    }

    private boolean hasAvailableSlot(Inventory inv) {
        boolean check = false;
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                check = true;
                break;
            }
        }

        return check;
    }
}
