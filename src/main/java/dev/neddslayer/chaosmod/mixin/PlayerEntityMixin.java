package dev.neddslayer.chaosmod.mixin;

import dev.neddslayer.chaosmod.item.ChaosOrbItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "attack", at = @At(value = "HEAD"))
    private void onAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack stack = player.getStackInHand(player.preferredHand);
        if (stack.getItem() instanceof ChaosOrbItem) {
            // Execute your code here
            System.out.println("Player left-clicked with MyItem");
        }
    }
}