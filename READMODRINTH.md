<img src="https://github.com/BananaBaagel/Fast-Hoppers/blob/main/src/main/resources/assets/fast-hoppers/icon_rounded.png?raw=true" width=128 style="border-radius: 20%;">

# Fast Hoppers
It makes hopper tick cooldown a variable.

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
* Make another mod that adds a sorting hopper (server-side only)
  * This is to fix large-scale sorting systems
  * Will likely be a regular hopper with completely custom insert code that only takes from the first slot
* Learn new skills so I can actually do all of this

___
*To those who understand, I lost the game. To those who don't, if you think about the game, you lose. You appear to be reading this, so you just lost the game.*
