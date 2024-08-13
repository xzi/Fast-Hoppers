<a href="https://modrinth.com/mod/Zs79Mkhy">
<img src="src/main/resources/assets/fast-hoppers/icon_rounded.png" width=128>
</a>

# Fast-Hoppers

[![Modrinth Downloads](https://img.shields.io/modrinth/dt/Zs79Mkhy?logo=modrinth)](https://modrinth.com/mod/Zs79Mkhy)
[![Curseforge Downloads](https://img.shields.io/curseforge/dt/912142?logo=curseforge&label=Downloads)](https://www.curseforge.com/minecraft/mc-mods/fast-hoppers)
[![Modrinth Version](https://img.shields.io/modrinth/v/Zs79Mkhy?logo=Modrinth&label=Lastest%20Version&color=14a551)](https://modrinth.com/mod/Zs79Mkhy)
[![Modrinth Game Versions](https://img.shields.io/modrinth/game-versions/Zs79Mkhy?logo=Modrinth&label=Supported%20Game%20Versions&color=14a551)](https://modrinth.com/mod/Zs79Mkhy)


It makes hopper tick cooldown a variable.

**PORT IS BY [@_Coln](https://github.com/Colnn), NOT BY ME!!!*** (I accidentally nuked his git log screwing around ;_;)

## How to use
Once you have the [mod](https://modrinth.com/mod/Zs79Mkhy) installed, go into any world (existing or new) and hoppers will transfer every 3 ticks (~6.7 transfers/second) instead of Minecraft's default 8 (2.5 transfers/sec)

## What does a "transfer" mean?
Read about the [configuration option](#hopperitemspertransfer-020)

## Configuration
### hopperTickSpeed
> In your world, you can change the tick cooldown of your hoppers with the gamerule command.
> ```
>  /gamerule hopperTickSpeed [ticks (1+)]
> ```
> Remember, the higher the number, the less items moved per second.
> This setting will likely break many redstone storage systems if it is below 6. If you only have one set of repeaters on the lowest setting, I recommend setting the speed to 6 ticks (~3.3 transfers/second). That is what worked for me on my server. I **highly** recommend testing your storage system with this mod in a creative world to find the right speed that works with your system.


### hopperItemsPerTransfer (0.2.0+)
> **ANY VALUE ABOVE 1 WILL LIKELY BREAK YOUR REDSTONE SORTING SYSTEMS UNLESS SPECIFICALLY MADE TO ACCOUNT FOR THIS.**
> ```
> /gamerule hopperItemsPerTransfer [Amount (1-64)]
> ```
> For example, if your HTS (hopperTickSpeed) is 20 (1 transfer/second) and your HIPT (hopperItemsPerTransfer) is 16, your total throughput will be 16 items/second.
>
> Another example, if your HTS is 2 (10 transfers/second) and your HIPT is 64, your total throughtput will be 640 items/second


## Can I use it in a modpack?
Yes, please use it however you want!


# Plans for the future
* Fix compatibility with the [Copper Hopper](https://github.com/pcal43/copper-hopper) mod

___
*To those who understand, I lost the game. To those who don't, if you think about the game, you lose. You appear to be reading this, so you just lost the game.*
