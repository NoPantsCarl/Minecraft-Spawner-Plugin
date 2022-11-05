package de.nopantscarl.purplespawner.listeners;

import de.nopantscarl.purplespawner.main.PurpleSpawner;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class PrepareAnvil implements Listener {

    private PurpleSpawner purpleSpawner;
    public PrepareAnvil(PurpleSpawner purpleSpawner) {
        this.purpleSpawner = purpleSpawner;
    }

    @EventHandler
    public void onAnvilRenaming(PrepareAnvilEvent e) {
        if(e.getInventory().getItem(0) != null) {
            if(e.getInventory().getItem(0).getType() == Material.SPAWNER) {
                e.setResult(null);
                return;
            }
        }

        if(e.getInventory().getItem(1) != null) {
            if(e.getInventory().getItem(1).getType() == Material.SPAWNER) {
                e.setResult(null);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClickedInventory() != null) {
            if (e.getInventory().getType() == InventoryType.ANVIL) {
                if(e.getCurrentItem() != null) {
                    if(e.getCurrentItem().getType() == Material.SPAWNER && e.getSlot() == 2) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
