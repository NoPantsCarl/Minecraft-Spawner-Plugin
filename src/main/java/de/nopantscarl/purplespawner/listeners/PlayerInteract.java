package de.nopantscarl.purplespawner.listeners;

import de.nopantscarl.purplespawner.main.PurpleSpawner;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class PlayerInteract implements Listener {

    private PurpleSpawner purpleSpawner;
    public PlayerInteract(PurpleSpawner purpleSpawner) {
        this.purpleSpawner = purpleSpawner;
    }

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent event) {
        Action action = event.getAction();
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (action != Action.RIGHT_CLICK_BLOCK || block.getType() != Material.SPAWNER) {
            return;
        }

        Optional<EntityType> entityTypeOptional = spawnEggToEntityType(item);

        if (!entityTypeOptional.isPresent()) {
            return;
        }

        EntityType entityType = entityTypeOptional.get();

        CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
        if (creatureSpawner.getSpawnedType().equals(entityType)) {
            event.setCancelled(true);
        }

    }

    private static Optional<EntityType> spawnEggToEntityType(ItemStack spawnEgg) {
        if (spawnEgg == null || !spawnEgg.getType().name().contains("SPAWN_EGG")) {
            return Optional.empty();
        }

        if (spawnEgg.getType() == Material.MOOSHROOM_SPAWN_EGG) {
            return Optional.of(EntityType.MUSHROOM_COW);
        }

        try {
            return Optional.of(EntityType.valueOf(spawnEgg.getType().name().split("_SPAWN_EGG")[0]));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

}
