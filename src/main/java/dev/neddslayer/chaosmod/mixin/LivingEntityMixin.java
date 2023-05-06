package dev.neddslayer.chaosmod.mixin;

import dev.neddslayer.chaosmod.registry.EffectRegistration;
import dev.neddslayer.chaosmod.registry.ItemRegistration;
import dev.neddslayer.chaosmod.registry.SoundRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract boolean removeStatusEffect(StatusEffect type);

    @Shadow public abstract void stopRiding();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void removeEffectWhenTakenDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(EffectRegistration.CHAOS_PROTECTION) && !world.isClient()) {
            world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegistration.CHAOS_ORB_PROTECT_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            this.removeStatusEffect(EffectRegistration.CHAOS_PROTECTION);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(5), EntityPredicates.VALID_LIVING_ENTITY);
            for (LivingEntity entity : entities) {
                entity.damage(DamageSource.FLY_INTO_WALL, 4);
                entity.takeKnockback(2, entity.getX(), entity.getZ());
            }
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void applyStatusEffectsIfHasRemnant(CallbackInfo ci) {
        if (this.isPlayer()) {
            List<ItemStack> itemStackList = getAllItemsInPlayerInventory((PlayerEntity)(Object)this);
            for (ItemStack stack : itemStackList) {
                if (stack.getItem() == ItemRegistration.CHAOTIC_REMNANT) {
                    this.addStatusEffect(new StatusEffectInstance(EffectRegistration.CHAOTIC_INSTABILITY, 1, 1));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 1, 1));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 1, 1));
                }
            }
        }
    }
    public List<ItemStack> getAllItemsInPlayerInventory(PlayerEntity player) {
        List<ItemStack> allItems = new ArrayList<>();
        PlayerInventory playerInventory = player.getInventory();
        for (int i = 0; i < playerInventory.size(); i++) {
            ItemStack stack = playerInventory.getStack(i);
            if (!stack.isEmpty()) {
                allItems.add(stack);
            }
        }
        return allItems;
    }
}
