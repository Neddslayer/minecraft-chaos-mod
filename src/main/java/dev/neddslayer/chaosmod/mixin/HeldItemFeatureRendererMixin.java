package dev.neddslayer.chaosmod.mixin;

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
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.*;

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
            if (stack.getItem() == ItemRegistration.CHAOS_ORB) {
                //matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) (Math.toDegrees(Math.sin(animTime * 100)) * 150)));
                //matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((float) (Math.toDegrees(Math.sin(animTime * 100)) * 150)));
                //matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float) (Math.toDegrees(Math.sin(animTime * 100)) * 150)));
                matrices.translate(0, Math.sin(animTime * 25) / 4, 0);
            }
            this.heldItemRenderer.renderItem(entity, stack, transformationMode, bl, matrices, vertexConsumers, light);
            matrices.pop();
        }
    }
}
