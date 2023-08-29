package com.banana.fasthoppers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.entity.HopperBlockEntity;

@Mixin(HopperBlockEntity.class)
public class HopperSpeed {
    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;needsCooldown()Z"))
    private static boolean redirectNeedsCooldownForServerTick(HopperBlockEntity hopper) {
        return false;
    }

    @Redirect(method = "insertAndExtract", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;needsCooldown()Z"))
    private static boolean redirectNeedsCooldownForInsertAndExtract(HopperBlockEntity hopper) {
        return false;
    }

}
