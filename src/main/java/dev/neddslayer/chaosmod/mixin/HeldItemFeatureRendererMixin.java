package dev.neddslayer.chaosmod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.neddslayer.chaosmod.ChaosMod;
import dev.neddslayer.chaosmod.registry.ItemRegistration;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.*;

import static dev.neddslayer.chaosmod.ChaosMod.CHAOS_RANDOM;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T> & ModelWithArms> extends FeatureRenderer<T, M> {
    @Shadow @Final private HeldItemRenderer heldItemRenderer;

    public HeldItemFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Unique
    private float animTime;

    /**
     * @author asdf
     * @reason asdf
     */
    @Overwrite
    public void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (!stack.isEmpty()) {
            matrices.push();
            animTime+= 0.5;
            ((ModelWithArms)this.getContextModel()).setArmAngle(arm, matrices);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            boolean bl = arm == Arm.LEFT;
            matrices.translate((double)((float)(bl ? -1 : 1) / 16.0F), 0.125, -0.625);
            if (stack.getItem() == ItemRegistration.CHAOS_ORB) matrices.translate(0, (Math.sin(animTime * 25) / 4) + 0.1, -0.05);

            this.heldItemRenderer.renderItem(entity, stack, transformationMode, bl, matrices, vertexConsumers, light);
            if (stack.getItem() == ItemRegistration.CHAOS_ORB) {
                matrices.translate(ChaosMod.randomFloat(-0.025f, 0.025f), ChaosMod.randomFloat(0f, 0.1f), ChaosMod.randomFloat(-0.025f, 0.025f));
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderColor(0.05f, 0.05f, 0.05f, 0.5f);
                for (int zxc = 0; zxc < 2; zxc++) {
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(ChaosMod.randomFloat(-20f, 20f)));
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(ChaosMod.randomFloat(-20f, 20f)));
                    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(ChaosMod.randomFloat(-20f, 20f)));
                    this.heldItemRenderer.renderItem(entity, stack, transformationMode, bl, matrices, vertexConsumers, light);
                }
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.disableBlend();
            }

            matrices.pop();
        }
    }
}
