package dev.neddslayer.chaosmod.registry;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import static dev.neddslayer.chaosmod.ChaosMod.CHAOS_LOGGER;

public class PacketRegistration {

    public static final Identifier CHAOS_ORB_DASH_FORWARD = new Identifier("chaosmod", "dash_forward");

    public static void registerHandlers() {
        ServerPlayNetworking.registerGlobalReceiver(CHAOS_ORB_DASH_FORWARD, ((server, player, handler, buf, responseSender) -> {
            Vec3d lookDir = player.getRotationVector();
            player.addVelocity(lookDir.getX() * 3f,lookDir.getY() * 3f,lookDir.getZ() * 3f);
            player.velocityModified = true;
        }));
    }

}
