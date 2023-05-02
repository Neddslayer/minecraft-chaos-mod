package dev.neddslayer.chaosmod.mixin;

import dev.neddslayer.chaosmod.item.ChaosOrbItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.neddslayer.chaosmod.ChaosMod.CHAOS_LOGGER;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow @Nullable public ClientPlayerEntity player;

    @Inject(method = "doAttack", at = @At("HEAD"))
    private void leftClickHook(CallbackInfoReturnable<Boolean> cir) {
        CHAOS_LOGGER.info("got the left click");
        if (player != null) {
            ChaosOrbItem.onLeftClick(player.getMainHandStack());
        }
    }
}