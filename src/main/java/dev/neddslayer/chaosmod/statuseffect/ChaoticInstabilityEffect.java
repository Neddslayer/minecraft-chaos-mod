package dev.neddslayer.chaosmod.statuseffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ChaoticInstabilityEffect extends StatusEffect {

    public ChaoticInstabilityEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x650B0B);
    }
    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // It gets removed when the player takes damage, so this can just stay true.
        return true;
    }
}
