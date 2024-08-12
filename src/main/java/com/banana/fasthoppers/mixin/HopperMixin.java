package com.banana.fasthoppers.mixin;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.banana.fasthoppers.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.banana.fasthoppers.HopperGamerule;

import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(HopperBlockEntity.class)
public class HopperMixin {

    // @Nullable Inventory from, Inventory to, ItemStack stack, int slot, @Nullable Direction side
    @Redirect(method = "transfer(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/item/ItemStack;ILnet/minecraft/util/math/Direction;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;setTransferCooldown(I)V"))
    private static void __transfer__setTransferCooldown(HopperBlockEntity hopperEntity, int amount) {
        HopperInterface hopper = ((HopperInterface) HopperBlockEntity.class.cast(hopperEntity));

        // Get the gamerule for the custom tick cooldown
        int hopperCooldown = hopperEntity.getWorld().getGameRules().getInt(HopperGamerule.HOPPER_TICK);
        // set the transferCooldown to whatever the gamerule is instead of Minecraft's static 8
        hopper.setTransferCooldownAccessor(hopperCooldown - (amount + 8));
        return;
    }

    @Redirect(method = "insertAndExtract", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;setTransferCooldown(I)V"))
    private static void __insertAndExtract__setTransferCooldown(HopperBlockEntity hopperEntity, int amount) {
        HopperInterface hopper = ((HopperInterface) HopperBlockEntity.class.cast(hopperEntity));

        // Get the gamerule for the custom tick cooldown
        int hopperCooldown = hopperEntity.getWorld().getGameRules().getInt(HopperGamerule.HOPPER_TICK);
        // set the transferCooldown to whatever the gamerule is instead of Minecraft's static 8
        hopper.setTransferCooldownAccessor(hopperCooldown);
        return;
    }

    // The method called when inserting an item/block into a slot
    @Inject(method = "insert", at = @At("HEAD"), cancellable = true)
    private static void __insert(
            World world, BlockPos pos, HopperBlockEntity blockEntity, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = world.getBlockState(blockEntity.getPos());

//        blockEntity.
        Inventory outputInv = HopperMixin.getOutputInventory(world, pos, blockEntity);
        if (outputInv == null) {
            cir.setReturnValue(false);
            return;
        }

        Direction direction = state.get(HopperBlock.FACING).getOpposite();
        // Use the shadow method
        if (HopperMixin.isInventoryFull(outputInv, direction)) {
            cir.setReturnValue(false);
            return;
        }

        // Get the max amount of items we can transfer
        int defaultMaxTransferAmount = world.getGameRules().getInt(HopperGamerule.HOPPER_ITEM_TRANSFER_COUNT);

        int amountTransferred = 0;

        // Iterate through all the slots in the hopper's inventory
        for (int i = 0; i < blockEntity.size(); ++i) {

            // Get the item stack at slot i
            ItemStack itemStack = blockEntity.getStack(i);

            if (itemStack.isEmpty())
                continue;

            boolean hasOpenSlot = false;
            for (int j = 0; j < outputInv.size(); j++) {
                if (outputInv.getStack(j).isEmpty())
                    hasOpenSlot = true;
            }

            if (outputInv.count(itemStack.getItem()) == 0 && hasOpenSlot == false)
                continue;

            // Set the max transfer amount to the max amount of items we can safely transfer
            int maxTransferAmount = defaultMaxTransferAmount - amountTransferred;

            // transfer the items with a split stack (with a max item count in this stack of maxTransferAmount)
            ItemStack itemStack2 = HopperBlockEntity.transfer(blockEntity, outputInv,
                    itemStack.split(maxTransferAmount), direction);

            // If the transfer was successful (at least one item was transferred)
            if (itemStack2.getCount() > 0) {
                amountTransferred += itemStack2.getCount();

                // Set the count of the split stack to include the remaining items
                itemStack.setCount(itemStack.getCount() + itemStack2.getCount());
                // Set the hopper inventory's stack to the split + remaining itemstack
                blockEntity.setStack(i, itemStack);
            }
            if (amountTransferred >= maxTransferAmount) {
                // Mark the inventory as dirty (idk, it's a minecraft thing, but it works)
                outputInv.markDirty();
                cir.setReturnValue(true);
                return;
            }
        }
        if (amountTransferred > 0) {
            // Mark the inventory as dirty (idk, it's a minecraft thing, but it works)
            outputInv.markDirty();
            cir.setReturnValue(true);
            return;
        }

        cir.setReturnValue(false);
        return;
    }

    private static boolean extractWithWorld(Hopper hopper, Inventory inputInv, int slot, Direction side, World world) {
        ItemStack itemStack = inputInv.getStack(slot);

        // If the item stack is not empty and can be extracted (shadow method)
        if (!itemStack.isEmpty() && HopperMixin.canExtract(hopper, inputInv, itemStack, slot, side)) {
            // Copy the item stack
            ItemStack itemStack2 = itemStack.copy();

            // Get the max amount of items we can transfer from the gamerules of this world
            int maxTransferAmount = world.getGameRules().getInt(HopperGamerule.HOPPER_ITEM_TRANSFER_COUNT);

            // transfer the items with a split stack (with a max item count in this stack of maxTransferAmount)
            ItemStack itemStack3 = HopperBlockEntity.transfer(inputInv, hopper,
                    itemStack2.split(maxTransferAmount), null);

            // If the transfer was successful (at least one item was transferred)
            if (itemStack3.getCount() < maxTransferAmount) {

                // Set the count of the split stack to include the remaining items
                itemStack2.setCount(itemStack2.getCount() + itemStack3.getCount());
                // Set the inventory's stack to the split + remaining itemstack
                inputInv.setStack(slot, itemStack2);

                // Mark the inventory as dirty (idk, it's a minecraft thing, but it works)
                inputInv.markDirty();
                return true;
            }
        }

        return false;
    }

    @Inject(method = "extract", at = @At("HEAD"), cancellable = true)
    private static void __extract(World world, Hopper hopper, CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = BlockPos.ofFloored(hopper.getHopperX(), hopper.getHopperY() + 1.0, hopper.getHopperZ());
        BlockState blockState = world.getBlockState(blockPos);
        Logger.log("Extracting items from hopper at " + blockPos);
        Inventory inputInv = HopperMixin.getInputInventory(world, hopper, blockPos, blockState);
        if (inputInv != null) {
            Logger.log("Input inventory is not null");
            Direction direction = Direction.DOWN;
//            Logger.log(inputInv.)

            // Use the shadow method
            if (inputInv.isEmpty()) {
                cir.setReturnValue(false);
                Logger.log("Input inventory is empty");
                return;
            }
            // Use the custom extractWithWorld method (to allow us to get the world's gamerules)
            cir.setReturnValue(Arrays.stream(HopperMixin.getAvailableSlots(inputInv, direction)).anyMatch(slot -> HopperMixin.extractWithWorld(hopper, inputInv, slot, direction, world)));
            return;
        }
        // Use the shadow method
        for (ItemEntity itemEntity : HopperBlockEntity.getInputItemEntities(world, hopper)) {
            if (!HopperBlockEntity.extract(hopper, itemEntity))
                continue;
            cir.setReturnValue(true);
            return;
        }
        cir.setReturnValue(false);
        return;
    }

    // Add the shadow methods
    @Shadow
    private static Inventory getOutputInventory(World world, BlockPos pos, HopperBlockEntity blockEntity) {
        return null;
    };

    @Shadow
    private static boolean isInventoryFull(Inventory inventory2, Direction direction) {
        return false;
    }

    @Shadow
    private static boolean canExtract(Inventory hopperInventory, Inventory fromInventory, ItemStack stack, int slot,
                                      Direction facing) {
        return false;
    }

    @Shadow
    private static int[] getAvailableSlots(Inventory inventory, Direction side) {
        return null;
    }

    @Shadow
    private static Inventory getInputInventory(World world, Hopper hopper, BlockPos pos, BlockState state) {
        return null;
    }
}
