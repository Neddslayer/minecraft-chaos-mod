package dev.neddslayer.chaosmod.registry;

import net.minecraft.entity.damage.DamageSource;

public class CustomDamageSources extends DamageSource {
    public CustomDamageSources(String name) {
        super(name);
    }

    public static final DamageSource CHAOTIC_INSTABILITY = new DamageSource("chaotic_instability");
}
