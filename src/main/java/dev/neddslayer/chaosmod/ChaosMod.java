package dev.neddslayer.chaosmod;

import dev.neddslayer.chaosmod.registry.EffectRegistration;
import dev.neddslayer.chaosmod.registry.ItemRegistration;
import dev.neddslayer.chaosmod.registry.PacketRegistration;
import dev.neddslayer.chaosmod.registry.SoundRegistration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChaosMod implements ModInitializer {

    public static final Logger CHAOS_LOGGER = LoggerFactory.getLogger("Chaos Mod");
    public static final Random CHAOS_RANDOM = Random.create();
    public static float randomFloat(float min, float max) {
        return CHAOS_RANDOM.nextFloat() * (max - min) + min;
    }

    public static final DefaultParticleType CHAOS_FLASH = FabricParticleTypes.simple();

    @Override
    public void onInitialize() {
        CHAOS_LOGGER.info("The Chaos Orb has awoken...");

        ItemRegistration.registerItems();
        SoundRegistration.registerSounds();
        EffectRegistration.registerEffects();
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("chaosmod", "chaos_flash"), CHAOS_FLASH);
        PacketRegistration.registerHandlers();
    }
}
