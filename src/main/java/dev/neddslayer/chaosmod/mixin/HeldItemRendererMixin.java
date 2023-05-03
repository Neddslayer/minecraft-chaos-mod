package dev.neddslayer.chaosmod.mixin;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.neddslayer.chaosmod.ChaosMod;
import dev.neddslayer.chaosmod.registry.ItemRegistration;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Shadow protected abstract void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    @Shadow private ItemStack mainHand;

    @Shadow private float equipProgressMainHand;

    @Shadow private float prevEquipProgressMainHand;

    @Shadow private float prevEquipProgressOffHand;

    @Shadow private float equipProgressOffHand;

    @Shadow private ItemStack offHand;

    @Shadow
    static HeldItemRenderer.HandRenderType getHandRenderType(ClientPlayerEntity player) {
        return null;
    }

    @Inject(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At("HEAD"))
    public void renderItem(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light, CallbackInfo ci) {
        if (mainHand.getItem() == ItemRegistration.CHAOS_ORB) {
            for (int zxc = 0; zxc < 2; zxc++) {
                float f = player.getHandSwingProgress(tickDelta);
                Hand hand = (Hand) MoreObjects.firstNonNull(player.preferredHand, Hand.MAIN_HAND);
                float g = MathHelper.lerp(tickDelta, player.prevPitch, player.getPitch());
                HeldItemRenderer.HandRenderType handRenderType = getHandRenderType(player);
                float h = MathHelper.lerp(tickDelta, player.lastRenderPitch, player.renderPitch);
                float i = MathHelper.lerp(tickDelta, player.lastRenderYaw, player.renderYaw);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((player.getPitch(tickDelta) - h) * 0.1F));
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((player.getYaw(tickDelta) - i) * 0.1F));
                float j;
                float k;
                if (handRenderType != null && handRenderType.renderMainHand) {
                    j = hand == Hand.MAIN_HAND ? f : 0.0F;
                    k = 1.0F - MathHelper.lerp(tickDelta, this.prevEquipProgressMainHand, this.equipProgressMainHand);
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(ChaosMod.randomFloat(-1f, 1f)));
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(ChaosMod.randomFloat(-1f, 1f)));
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(ChaosMod.randomFloat(-1f, 1f)));
                    this.renderFirstPersonItem(player, tickDelta, g, Hand.MAIN_HAND, j, this.mainHand, k, matrices, vertexConsumers, 0);
                }

                if (handRenderType != null && handRenderType.renderOffHand) {
                    j = hand == Hand.OFF_HAND ? f : 0.0F;
                    k = 1.0F - MathHelper.lerp(tickDelta, this.prevEquipProgressOffHand, this.equipProgressOffHand);
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(ChaosMod.randomFloat(-1f, 1f)));
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(ChaosMod.randomFloat(-1f, 1f)));
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(ChaosMod.randomFloat(-1f, 1f)));
                    this.renderFirstPersonItem(player, tickDelta, g, Hand.OFF_HAND, j, this.offHand, k, matrices, vertexConsumers, 0);
                }

                vertexConsumers.draw();
            }

        }
    }
}
