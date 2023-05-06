package dev.neddslayer.chaosmod.registry;

import dev.neddslayer.chaosmod.statuseffect.ChaosProtectionEffect;
import dev.neddslayer.chaosmod.statuseffect.ChaoticInstabilityEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectRegistration {

    public static final StatusEffect CHAOS_PROTECTION = new ChaosProtectionEffect();
    public static final StatusEffect CHAOTIC_INSTABILITY = new ChaoticInstabilityEffect();

    public static void registerEffects() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("chaosmod", "chaos_protection"), CHAOS_PROTECTION);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("chaosmod", "chaotic_instability"), CHAOTIC_INSTABILITY);
    }

}
