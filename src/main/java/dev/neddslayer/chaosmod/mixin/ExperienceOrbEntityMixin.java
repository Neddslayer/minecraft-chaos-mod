package dev.neddslayer.chaosmod.mixin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.neddslayer.chaosmod.registry.ItemRegistration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin extends Entity {
    @Shadow protected abstract int getMendingRepairAmount(int experienceAmount);

    @Shadow private int amount;

    public ExperienceOrbEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "repairPlayerGears", at = @At("HEAD"))
    private void fixChaosOrb(PlayerEntity player, int amount, CallbackInfoReturnable<Integer> cir) {
        List<ItemStack> entries = getEquipment(player);
        if (!entries.isEmpty()) {
            List<ItemStack> itemStacks = Lists.newArrayList();

            for (ItemStack entry : entries) {
                if (entry.getItem() == ItemRegistration.CHAOS_ORB) {
                    itemStacks.add(entry);
                }
            }

            for (ItemStack orb : itemStacks) {
                int i = Math.min(this.getMendingRepairAmount(this.amount), orb.getDamage());
                orb.setDamage(orb.getDamage() - i);
            }
        }
    }

    public List<ItemStack> getEquipment(LivingEntity entity) {
        List<ItemStack> map = Lists.newArrayList();
        EquipmentSlot[] var3 = EquipmentSlot.values();

        for (EquipmentSlot equipmentSlot : var3) {
            ItemStack itemStack = entity.getEquippedStack(equipmentSlot);
            if (!itemStack.isEmpty()) {
                map.add(itemStack);
            }
        }

        return map;
    }
}
