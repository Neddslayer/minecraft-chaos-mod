package dev.neddslayer.chaosmod.registry;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class PacketRegistration {

    public static final Identifier CHAOS_ORB_DASH_FORWARD = new Identifier("chaosmod", "dash_forward");

    // Register the packet handlers for the server. This is used for the chaos orb dashing forward as hitting nothing is not sent to the server, so we must do it our own way.
    public static void registerHandlers() {
        ServerPlayNetworking.registerGlobalReceiver(CHAOS_ORB_DASH_FORWARD, ((server, player, handler, buf, responseSender) -> {
            if (player.totalExperience >= 50 || player.getAbilities().creativeMode) {
                player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistration.CHAOS_ORB_DASH_FORWARD_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                player.addStatusEffect(new StatusEffectInstance(EffectRegistration.CHAOS_PROTECTION, 200));
                Vec3d lookDir = player.getRotationVector();
                player.addVelocity(lookDir.getX() * 2f, lookDir.getY() * 3f, lookDir.getZ() * 2f);
                if (!player.getAbilities().creativeMode) player.addExperience(-50);
                player.velocityModified = true;
            }
        }));
        ServerTickEvents.END_SERVER_TICK.register((world) -> {
            for (ServerPlayerEntity player : world.getPlayerManager().getPlayerList()) {
                if (player.hasStatusEffect(EffectRegistration.CHAOTIC_INSTABILITY) && player.age % 100 == 0) {
                    player.damage(CustomDamageSources.CHAOTIC_INSTABILITY, 4);
                }
            }
        });
    }

}
