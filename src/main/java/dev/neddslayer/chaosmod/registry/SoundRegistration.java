package dev.neddslayer.chaosmod.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundRegistration {

    public static final Identifier CHAOS_ORB_DASH_FORWARD = new Identifier("chaosmod","chaos_orb_dash_forward");
    public static SoundEvent CHAOS_ORB_DASH_FORWARD_EVENT = new SoundEvent(CHAOS_ORB_DASH_FORWARD);

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, CHAOS_ORB_DASH_FORWARD, CHAOS_ORB_DASH_FORWARD_EVENT);
    }

}
