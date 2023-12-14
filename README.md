# cTweaks

PaperMC plugin which adds various tweaks and items to enhance survival gameplay.

## Commands:
* **/ctweaks** - Main ctweaks command. Usage: /ctweaks [item] <item>

## Config:
In *config.yml* you can configure the following:
  * Disable specific items (all are turned on by default)
  * Change the secondary crafting recipe material for specific items (see: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)
  * Disable specific functionalities

## Items:
| Item               | Effect                                                         | Ingredient 1    | Ingredient 2 |
| ------------------ | -------------------------------------------------------------- | --------------- | ------------ |
| Tunneler's Pickaxe | Mines 3x3 through natural stone-type blocks.                   | Diamond Pickaxe | Nether Star |
| Blink Pearl        | Ender pearl that doesn't get used up. Has a 5 second cooldown. | Ender Pearl     | Nether Star |

## Functions:
| Function           | Effect                                                         |
| ------------------ | ---------------------------------------------------------------| 
| DispenserInteractCauldron | Allows Dispensers to interact (pick up and place water/lava) with cauldrons| 
