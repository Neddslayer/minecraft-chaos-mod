package dev.neddslayer.chaosmod;

import dev.neddslayer.chaosmod.registry.ItemRegistration;
import dev.neddslayer.chaosmod.registry.PacketRegistration;
import dev.neddslayer.chaosmod.registry.SoundRegistration;
import dev.neddslayer.chaosmod.statuseffect.ChaosProtectionEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
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

    public static final StatusEffect CHAOS_PROTETCTION = new ChaosProtectionEffect();

    @Override
    public void onInitialize() {
        CHAOS_LOGGER.info("The Chaos Orb has awoken...");

        ItemRegistration.registerItems();
        SoundRegistration.registerSounds();
        Registry.register(Registry.STATUS_EFFECT, new Identifier("chaosmod", "chaos_protection"), CHAOS_PROTETCTION);
        PacketRegistration.registerHandlers();
    }
}
