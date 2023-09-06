package com.banana.fasthoppers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.banana.fasthoppers.HopperGamerule;

import net.minecraft.block.entity.HopperBlockEntity;

@Mixin(HopperBlockEntity.class)
public class HopperMixin {

    // @Nullable Inventory from, Inventory to, ItemStack stack, int slot, @Nullable Direction side
    @Redirect(method = "Lnet/minecraft/block/entity/HopperBlockEntity;transfer(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/item/ItemStack;ILnet/minecraft/util/math/Direction;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;setTransferCooldown(I)V"))
    private static void redirectTransfer(HopperBlockEntity hopperEntity, int amount) {
        HopperInterface hopper = ((HopperInterface) HopperBlockEntity.class.cast(hopperEntity));
        int hopperCooldown = hopperEntity.getWorld().getGameRules().getInt(HopperGamerule.HOPPER_TICK);
        hopper.setTransferCooldownAccessor(hopperCooldown - (amount + 8));
        return;
    }

    @Redirect(method = "insertAndExtract", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;setTransferCooldown(I)V"))
    private static void redirectInsertAndExtract(HopperBlockEntity hopperEntity, int amount) {
        HopperInterface hopper = ((HopperInterface) HopperBlockEntity.class.cast(hopperEntity));
        int hopperCooldown = hopperEntity.getWorld().getGameRules().getInt(HopperGamerule.HOPPER_TICK);
        hopper.setTransferCooldownAccessor(hopperCooldown);
        return;
    }

}
