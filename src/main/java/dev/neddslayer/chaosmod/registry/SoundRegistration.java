package dev.neddslayer.chaosmod.registry;

import net.minecraft.client.sound.Sound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundRegistration {

    public static final Identifier CHAOS_ORB_DASH_FORWARD = new Identifier("chaosmod","chaos_orb_dash");
    public static SoundEvent CHAOS_ORB_DASH_FORWARD_EVENT = new SoundEvent(CHAOS_ORB_DASH_FORWARD);
    public static final Identifier CHAOS_ORB_RELEASE_ENERGY = new Identifier("chaosmod", "chaos_orb_release_energy");
    public static SoundEvent CHAOS_ORB_RELEASE_ENERGY_EVENT = new SoundEvent(CHAOS_ORB_RELEASE_ENERGY);
    public static final Identifier CHAOS_ORB_PROTECT = new Identifier("chaosmod", "chaos_orb_protect");
    public static SoundEvent CHAOS_ORB_PROTECT_EVENT = new SoundEvent(CHAOS_ORB_PROTECT);
    public static final Identifier CHAOS_ORB_DISAPPEAR = new Identifier("chaosmod", "chaos_orb_disappear");
    public static SoundEvent CHAOS_ORB_DISAPPEAR_EVENT = new SoundEvent(CHAOS_ORB_DISAPPEAR);


    // Register all the sounds declared in this file
    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, CHAOS_ORB_DASH_FORWARD, CHAOS_ORB_DASH_FORWARD_EVENT);
        Registry.register(Registry.SOUND_EVENT, CHAOS_ORB_RELEASE_ENERGY, CHAOS_ORB_RELEASE_ENERGY_EVENT);
        Registry.register(Registry.SOUND_EVENT, CHAOS_ORB_PROTECT, CHAOS_ORB_PROTECT_EVENT);
        Registry.register(Registry.SOUND_EVENT, CHAOS_ORB_DISAPPEAR, CHAOS_ORB_DISAPPEAR_EVENT);
    }

}
