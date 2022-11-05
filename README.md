# Purple-Spawner
Minecraft Open Source Plugin 1.17, 1.18.1 tested on papper and spigot

Prevent anvil rename of spawners, Spawners go into player's inventory, consume only one spawn egg per usage, 
and prevent the spawner breaking if the player doesnt have silk touch or prerms.

Commands:
/spawnergive <Name> <Type> <Amount>
The type must be writte as a real minecraft entity name
An Endercrystal for example would be ender_crystal or a wandering trader would be wandering_trader
Here you can find a list, how to you need to specify the type https://technical-minecraft.fandom.com/wiki/Entity

/purplespawner reloadconfig

-----------------------------------------------
Permissions:
for the command /spawnergive - purplespawner.givespawner
for the command /purplespawner - purplespawner.reloadconfig
to break a spawner - purplespawner.mine
to edit a spawner with spawn egg - purplespawner.eggchange
