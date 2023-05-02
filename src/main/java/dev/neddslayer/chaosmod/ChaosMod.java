package dev.neddslayer.chaosmod;

import dev.neddslayer.chaosmod.registry.ItemRegistration;
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
        CHAOS_LOGGER.info("The Chaos Orb has awaken...");

        ItemRegistration.registerItems();
        AttackBlockCallback.EVENT.register(((player, world, hand, pos, direction) -> {
            ItemStack itemStack = player.getStackInHand(hand);
            if (hand == player.preferredHand && itemStack.getItem() == ItemRegistration.CHAOS_ORB) {
                // Execute your code here
                System.out.println("Player left-clicked with MyItem in the air");
            }
            return ActionResult.PASS;
        }));
    }
}
