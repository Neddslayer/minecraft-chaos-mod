package dev.neddslayer.chaosmod.registry;

import dev.neddslayer.chaosmod.ChaosMod;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.sound.Sound;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import static dev.neddslayer.chaosmod.ChaosMod.CHAOS_LOGGER;

public class PacketRegistration {

    public static final Identifier CHAOS_ORB_DASH_FORWARD = new Identifier("chaosmod", "dash_forward");

    public static void registerHandlers() {
        ServerPlayNetworking.registerGlobalReceiver(CHAOS_ORB_DASH_FORWARD, ((server, player, handler, buf, responseSender) -> {
            if (player.totalExperience >= 50 || player.getAbilities().creativeMode) {
                player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistration.CHAOS_ORB_DASH_FORWARD_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                player.addStatusEffect(new StatusEffectInstance(ChaosMod.CHAOS_PROTECTION, 200));
                Vec3d lookDir = player.getRotationVector();
                player.addVelocity(lookDir.getX() * 2f, lookDir.getY() * 3f, lookDir.getZ() * 2f);
                if (!player.getAbilities().creativeMode) player.addExperience(-50);
                player.velocityModified = true;
            }
        }));
    }

}
