package com.banana.fasthoppers.mixin;


import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.pcal.copperhopper.CopperInventory;

@Mixin(HopperBlockEntity.class)
public class CopperHopperMixin {

    private static boolean isCopperHopper(Inventory target) {
        return target instanceof CopperInventory;
    }
}
