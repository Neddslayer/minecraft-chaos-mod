package dev.neddslayer.chaosmod.mixin;

import dev.neddslayer.chaosmod.ChaosMod;
import dev.neddslayer.chaosmod.registry.ItemRegistration;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin extends EntityRenderer<ItemEntity> {
    @Shadow @Final private Random random;

    @Shadow @Final private ItemRenderer itemRenderer;

    @Shadow protected abstract int getRenderedAmount(ItemStack stack);

    protected ItemEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private void renderOrbDuplicates(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        ItemStack itemStack = itemEntity.getStack();
        if (itemStack.getItem() == ItemRegistration.CHAOS_ORB) {
            matrixStack.push();
            int j = itemStack.isEmpty() ? 187 : Item.getRawId(itemStack.getItem()) + itemStack.getDamage();
            this.random.setSeed((long) j);
            BakedModel bakedModel = this.itemRenderer.getModel(itemStack, itemEntity.world, (LivingEntity) null, itemEntity.getId());
            boolean bl = bakedModel.hasDepth();
            int k = this.getRenderedAmount(itemStack);
            float l = MathHelper.sin(((float) itemEntity.getItemAge() + g) / 10.0F + itemEntity.uniqueOffset) * 0.1F + 0.1F;
            float m = bakedModel.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
            matrixStack.translate(0.0, (double) (l + 0.25F * m), 0.0);
            float n = itemEntity.getRotation(g);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(n));
            float o = bakedModel.getTransformation().ground.scale.getX();
            float p = bakedModel.getTransformation().ground.scale.getY();
            float q = bakedModel.getTransformation().ground.scale.getZ();
            float s;
            float t;
            if (!bl) {
                float r = -0.0F * (float) (k - 1) * 0.5F * o;
                s = -0.0F * (float) (k - 1) * 0.5F * p;
                t = -0.09375F * (float) (k - 1) * 0.5F * q;
                matrixStack.translate((double) r, (double) s, (double) t);
            }

            for (int u = 0; u < 2; ++u) {
                if (u > 0) {
                    if (bl) {
                        s = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        t = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        float v = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        matrixStack.translate((double) s, (double) t, (double) v);
                    } else {
                        s = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                        t = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                        matrixStack.translate((double) s, (double) t, 0.0);
                    }
                }

                matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(ChaosMod.randomFloat(-180f, 180f)));
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(ChaosMod.randomFloat(-180f, 180f)));
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(ChaosMod.randomFloat(-180f, 180f)));
                matrixStack.translate(ChaosMod.randomFloat(-0.1f, 0.1f), ChaosMod.randomFloat(-0.1f, 0.1f), ChaosMod.randomFloat(-0.1f, 0.1f));
                itemEntity.world.addParticle(ChaosMod.CHAOS_FLASH, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 2.5, 0, 0);
                this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, 0, OverlayTexture.DEFAULT_UV, bakedModel);
            }
            matrixStack.pop();
        }
    }

}
