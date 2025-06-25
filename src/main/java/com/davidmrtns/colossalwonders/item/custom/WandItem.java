package com.davidmrtns.colossalwonders.item.custom;

import com.davidmrtns.colossalwonders.components.ModComponents;
import com.davidmrtns.colossalwonders.item.custom.cores.WandCore;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class WandItem extends Item {
    public WandItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(hand != Hand.MAIN_HAND) return TypedActionResult.fail(user.getStackInHand(hand));

        ItemStack itemStack = user.getStackInHand(hand);

        if(user.isSneaking()) {
            dropAllBundledItems(itemStack, user);
        }else{
            BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)itemStack.get(DataComponentTypes.BUNDLE_CONTENTS);
            if(bundleContentsComponent != null && !bundleContentsComponent.isEmpty()){
                WandCore core = (WandCore) bundleContentsComponent.get(0).getItem();
                float energy = WandItem.getEnergy(itemStack);
                if(core.calculateWaste() <= energy){
                    float waste = core.performSpell(world, user, this);
                    WandItem.consumeEnergy(waste, itemStack);
                }
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        float currentEnergy = WandItem.getEnergy(stack);

        if(currentEnergy >= 71){
            tooltip.add(Text.translatable("item.colossal_wonders.wand.tooltip_energy", currentEnergy).formatted(Formatting.DARK_GREEN));
        }else if(currentEnergy >= 36 && currentEnergy <= 70){
            tooltip.add(Text.translatable("item.colossal_wonders.wand.tooltip_energy", currentEnergy).formatted(Formatting.YELLOW));
        }else if(currentEnergy <= 35){
            tooltip.add(Text.translatable("item.colossal_wonders.wand.tooltip_energy", currentEnergy).formatted(Formatting.DARK_RED));
        }

        BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if(bundleContentsComponent != null && !bundleContentsComponent.isEmpty()){
            ItemStack itemStack = bundleContentsComponent.get(0);
            tooltip.add(Text.translatable("item.colossal_wonders.wand.tooltip_wand_core", itemStack.toHoverableText()).formatted(Formatting.DARK_AQUA));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    // temporary method for debug purposes
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack itemStack = context.getStack();
        float energy = WandItem.getEnergy(itemStack);
        WandItem.setEnergy(itemStack, energy + 20);
        return super.useOnBlock(context);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
            if (bundleContentsComponent == null) {
                return false;
            } else {
                ItemStack itemStack = slot.getStack();
                BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
                if (itemStack.isEmpty()) {
                    ItemStack itemStack2 = builder.removeFirst();
                    if (itemStack2 != null) {
                        ItemStack itemStack3 = slot.insertStack(itemStack2);
                        builder.add(itemStack3);
                    }
                } else if (slot.inventory.getStack(slot.getIndex()).getItem() instanceof WandCore
                        && itemStack.getItem().canBeNested()) {
                    builder.add(slot, player);
                }

                stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
                return true;
            }
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
            if (bundleContentsComponent == null) {
                return false;
            } else {
                BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
                if (otherStack.isEmpty()) {
                    ItemStack itemStack = builder.removeFirst();
                    if (itemStack != null) {
                        cursorStackReference.set(itemStack);
                    }
                } else if (slot.inventory.getStack(slot.getIndex()).getItem() instanceof WandCore
                        && stack.getItem().canBeNested()) {
                    builder.add(slot, player);
                }

                stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)entity.getStack().get(DataComponentTypes.BUNDLE_CONTENTS);
        if (bundleContentsComponent != null) {
            entity.getStack().set(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT);
            ItemUsage.spawnItemContents(entity, bundleContentsComponent.iterateCopy());
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return isEquipped(stack);
    }

    public static float getEnergy(ItemStack stack){
        return stack.getOrDefault(ModComponents.WAND_ENERGY, 0f);
    }

    public static void setEnergy(ItemStack stack, float energy){
        stack.set(ModComponents.WAND_ENERGY, energy);
    }

    public static void consumeEnergy(float value, ItemStack stack){
        float energy = WandItem.getEnergy(stack);
        WandItem.setEnergy(stack, energy - value);
    }

    public static ItemStack getActiveWandCore(ItemStack stack){
        BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (bundleContentsComponent != null && !bundleContentsComponent.isEmpty()) {
            return bundleContentsComponent.get(0);
        }
        return null;
    }

    public static void swapWandCore(PlayerEntity player, ItemStack wandStack, int selectedWandCoreSlot) {
        BundleContentsComponent contents = (BundleContentsComponent) wandStack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if(contents == null) return;

        BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(contents);

        ItemStack selectedWandCore = player.getInventory().getStack(selectedWandCoreSlot);

        // if the wand already have an eqquiped core, removes it
        ItemStack oldWandCore = builder.removeFirst();

        if(oldWandCore == selectedWandCore) return;

        // adds the new wand core to the wand
        builder.add(selectedWandCore);
        wandStack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());

        // if there was an eqquiped core before, returns it to the inventory
       if (oldWandCore != null && !oldWandCore.isEmpty()) {
            boolean inserted = player.getInventory().insertStack(oldWandCore);
            if (!inserted) {
                // drops the core if the inventory is full
                player.dropItem(oldWandCore, false);
            }
       }
    }

    public static void dropWandCore(PlayerEntity player, ItemStack wandStack) {
        BundleContentsComponent contents = (BundleContentsComponent) wandStack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if(contents == null) return;

        BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(contents);
        ItemStack oldWandCore = builder.removeFirst();
        wandStack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());

        if (oldWandCore != null && !oldWandCore.isEmpty()) {
            boolean inserted = player.getInventory().insertStack(oldWandCore);
            if (!inserted) {
                // drops the core if the inventory is full
                player.dropItem(oldWandCore, false);
            }
        }
    }

    public static boolean isEquipped(ItemStack stack){
        BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        return bundleContentsComponent != null && !bundleContentsComponent.isEmpty();
    }

    public boolean dropAllBundledItems(ItemStack stack, PlayerEntity player) {
        BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (bundleContentsComponent != null && !bundleContentsComponent.isEmpty()) {
            stack.set(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT);
            if (player instanceof ServerPlayerEntity) {
                bundleContentsComponent.iterateCopy().forEach((stackx) -> player.getInventory().insertStack(stackx));
            }
            return true;
        } else {
            return false;
        }
    }
}
