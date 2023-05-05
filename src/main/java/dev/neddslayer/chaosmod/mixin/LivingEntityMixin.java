package dev.neddslayer.chaosmod.mixin;

import dev.neddslayer.chaosmod.ChaosMod;
import dev.neddslayer.chaosmod.registry.SoundRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract boolean removeStatusEffect(StatusEffect type);

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void removeEffectWhenTakenDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(ChaosMod.CHAOS_PROTECTION) && !world.isClient()) {
            world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegistration.CHAOS_ORB_PROTECT_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            this.removeStatusEffect(ChaosMod.CHAOS_PROTECTION);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(5), EntityPredicates.VALID_LIVING_ENTITY);
            for (LivingEntity entity : entities) {
                entity.damage(DamageSource.FLY_INTO_WALL, 4);
                entity.takeKnockback(2, entity.getX(), entity.getZ());
            }
            cir.setReturnValue(false);
        }
    }
}
