package de.nopantscarl.purplespawner.listeners;

import de.nopantscarl.purplespawner.main.PurpleSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    public PurpleSpawner purpleSpawner;
    public BlockPlace(PurpleSpawner purpleSpawner) {
        this.purpleSpawner = purpleSpawner;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(e.getBlock().getType() == Material.SPAWNER) {
            CreatureSpawner creatureSpawner = (CreatureSpawner) e.getBlock().getState();
            String unsanitized = this.unsanitizeEntityName(e.getItemInHand().getItemMeta().getDisplayName());
            EntityType spawnedType = EntityType.fromName(unsanitized);
            if(spawnedType != null) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(PurpleSpawner.getInstance(), () -> {
                    creatureSpawner.setSpawnedType(spawnedType);
                    creatureSpawner.update();
                }, 7L);
            }
        }
    }

    private String unsanitizeEntityName(String name) {
        String toReturn = "";
        String[] splitted = name.split(" ");
        for(int i = 0; i < splitted.length; i++) {
            toReturn = toReturn + splitted[i].toUpperCase() + "_";
        }
        return toReturn.replaceAll("_SPAWNER_", " ").replaceAll("§5", " ").trim();
    }
}
