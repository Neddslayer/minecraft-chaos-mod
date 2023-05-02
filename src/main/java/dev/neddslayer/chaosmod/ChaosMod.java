package dev.neddslayer.chaosmod;

import dev.neddslayer.chaosmod.registry.ItemRegistration;
import dev.neddslayer.chaosmod.registry.PacketRegistration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.HitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChaosMod implements ModInitializer {

    public static final Logger CHAOS_LOGGER = LoggerFactory.getLogger("Chaos Mod");

    @Override
    public void onInitialize() {
        CHAOS_LOGGER.info("The Chaos Orb has awoken...");

        ItemRegistration.registerItems();
        PacketRegistration.registerHandlers();
    }
}
