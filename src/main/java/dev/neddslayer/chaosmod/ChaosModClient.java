package dev.neddslayer.chaosmod;

import dev.neddslayer.chaosmod.particle.ChaosFlash;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.FlameParticle;

public class ChaosModClient implements ClientModInitializer {

    // Register everything client side. This includes particles (shown below) and S2C (Server to Client) packets.
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ChaosMod.CHAOS_FLASH, ChaosFlash.Factory::new);
    }
}
