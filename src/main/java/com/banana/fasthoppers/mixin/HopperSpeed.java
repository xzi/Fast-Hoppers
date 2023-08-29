package com.banana.fasthoppers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.entity.HopperBlockEntity;

@Mixin(HopperBlockEntity.class)
public class HopperSpeed {
    @Redirect(method = "Lnet/minecraft/block/entity/HopperBlockEntity;serverTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/BlockState;Lnet/minecraft/block/entity/HopperBlockEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;needsCooldown()Z"))
    private static boolean redirectNeedsCooldownForServerTick(HopperBlockEntity hopper) {
        return false;
    }

    @Redirect(method = "Lnet/minecraft/block/entity/HopperBlockEntity;insertAndExtract(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/BlockState;Lnet/minecraft/block/entity/HopperBlockEntity;Ljava/util/function/BooleanSupplier;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;needsCooldown()Z"))
    private static boolean redirectNeedsCooldownForInsertAndExtract(HopperBlockEntity hopper) {
        return false;
    }

}
