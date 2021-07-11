# TODO List:

## General:
- Implement Registries for Feats, Skills and Paths
- Clean-up Code

## Path-Specific:
- Implement PathFeatures properly into the Path object.
  - Talk to Ash on how to make these Serializable when each PathFeature Implementation can have different Codecs.

## Skill-Specific:
- Implement some kind of serializable Action -> Experience system for Skills


## Feat-Specific:
- Implement 'Abilities' (Active Feats)
- Figure out how to implement event management system for Feats 
  - This one is up in the air if we should implement or just have people rely on Event Subscribers?
    


# Initially Planned:
## Path(s):
### Combat Path(s):
- Warrior
  - Adds bonuses to Melee Combat with "Traditional" Melee weapons (Axe, Sword, Shield).
  
- Ranger
  - Adds bonuses to Ranged Combat with "Traditional" Ranged weapons (Bow, Crossbow).
  
- Monk
  - Adds bonuses to mainly non-combative skills as well as with Unarmed and Improvised (Shovels, Hoes) Combat.
  
### Non-Combat Path(s):
- Fisherman
  - Adds bonuses to Fishing and makes Fishing more "Lucurative".
  
- Miner
  - Adds bonuses to Mining and makes Manual Mining easier/faster/more rewarding.
  
- Lumberjack
  - Adds bonuses to Woodcutting and makes Manual Woodcutting easier/faster/more rewarding.
  
- Trader
  - Adds bonuses to Trading with Villagers and unlocks more/better trading options with villagers.
  
- Farmer
  - Adds bonuses to Passive Farming abilities and Animal-Husbandry.
  

## Skill(s):
- Melee
- Ranged
- Constitution
- Farming
- Mining
- Fishing
- Woodcutting


## Feat(s)
- Spirit of Battle
  - Gives the player a buff based off the mob you hit last.
    - This buff lingers for 5 seconds
  
- Rapid Angler
  - Lowers the cooldown for a "Catch" when fishing
    - REMEMBER: YOU CAN NOT APPLY A FISHING SPEED BONUS OVER LEVEL 5!
  
-  Cascading Excavations
   - Allows the user to mine out entire **>ORE<** veins in simply by breaking one block of the vein.

- Timber!
  - Allows the user to chop down an entire tree by simply mining a single block.
  
- ?
  - Damage Reduction
  - Absorption Hearts(?)
  - Chance to make animals breed when you're around(?)
  - Chance to spawn an extra baby when breeding.
  - Increased Crop Yield (GML?)
  - Chance to make things grow faster when you're around(?)
  - Chance to get Bone-Meal to apply to a larger area (25% 3x3, 10% 5x5, 5% 7x7)
  - Mining Bare-Handed
  - Heal on Attack(?) (Monk?)
  - Wolf Feats (Heals when around you, Deals extra damage, etc)
  - Potion Tipped Arrows (?)
  - Ranged dealing more damage(?)
  - Chance to not consume arrows (?)
  - Piercing Arrows
