package dev.neddslayer.chaosmod.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ChaosFlash extends SpriteBillboardParticle {
    ChaosFlash(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
        this.maxAge = 4;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.setAlpha(0.6F - ((float)this.age + tickDelta - 1.0F) * 0.25F * 0.5F);
        super.buildGeometry(vertexConsumer, camera, tickDelta);
    }

    public float getSize(float tickDelta) {
        return 3;
    }
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            ChaosFlash chaosFlash = new ChaosFlash(clientWorld, d, e, f);
            chaosFlash.setSprite(this.spriteProvider);
            return chaosFlash;
        }
    }
}