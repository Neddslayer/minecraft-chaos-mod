package dev.neddslayer.chaosmod;

import dev.neddslayer.chaosmod.registry.ItemRegistration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChaosMod implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Chaos Mod");

    @Override
    public void onInitialize() {
        LOGGER.info("The Chaos Orb has awaken...");

        ItemRegistration.registerItems();

    }
    @Environment(EnvType.CLIENT)
    public static enum ArmPose {
        CHAOS_ORB(false);

        private final boolean twoHanded;

        private ArmPose(boolean twoHanded) {
            this.twoHanded = twoHanded;
        }

        public boolean isTwoHanded() {
            return this.twoHanded;
        }
    }
}
