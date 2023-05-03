package dev.neddslayer.chaosmod.mixin;

import dev.neddslayer.chaosmod.ChaosMod;
import dev.neddslayer.chaosmod.registry.SoundRegistration;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void removeEffectWhenTakenDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(ChaosMod.CHAOS_PROTETCTION) && !world.isClient()) {
            world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegistration.CHAOS_ORB_PROTECT_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            this.removeStatusEffect(ChaosMod.CHAOS_PROTETCTION);
            cir.setReturnValue(false);
        }
    }
}
