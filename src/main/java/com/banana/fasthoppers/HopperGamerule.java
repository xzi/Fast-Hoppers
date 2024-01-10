package com.banana.fasthoppers;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Category;

public class HopperGamerule implements ModInitializer {
    public static final GameRules.Key<GameRules.IntRule> HOPPER_TICK = GameRuleRegistry
            .register("hopperTickSpeed", Category.UPDATES, GameRuleFactory.createIntRule(3, 1));
    public static final GameRules.Key<GameRules.IntRule> HOPPER_ITEM_TRANSFER_COUNT = GameRuleRegistry
            .register("hopperItemsPerTransfer", Category.UPDATES, GameRuleFactory.createIntRule(1, 1, 64));
    

    @Override
    public void onInitialize() {
        Logger.info("Registered 2 gamerules");
        Logger.info("Initialized Fast Hoppers!");
        return;
    }
}
