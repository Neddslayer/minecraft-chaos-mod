package dev.neddslayer.chaosmod.mixin;

import dev.neddslayer.chaosmod.registry.ItemRegistration;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.client.render.entity.model.BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {

    @Shadow public net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose leftArmPose;

    @Shadow @Final public ModelPart leftArm;

    @Shadow public net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose rightArmPose;

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart head;

    @Shadow protected abstract Arm getPreferredArm(T entity);

    @Shadow protected abstract ModelPart getArm(Arm arm);

    @Shadow @Final public ModelPart body;

    /**
     * @author Neddslayer
     * @reason because I need javadocs
     */
    @Overwrite
    public void animateArms(T entity, float animationProgress) {
        if (!(this.handSwingProgress <= 0.0F) && entity.getStackInHand(entity.getActiveHand()).getItem() != ItemRegistration.CHAOS_ORB) {
            Arm arm = this.getPreferredArm(entity);
            ModelPart modelPart = this.getArm(arm);
            float f = this.handSwingProgress;
            this.body.yaw = MathHelper.sin(MathHelper.sqrt(f) * 6.2831855F) * 0.2F;
            ModelPart var10000;
            if (arm == Arm.LEFT) {
                var10000 = this.body;
                var10000.yaw *= -1.0F;
            }

            this.rightArm.pivotZ = MathHelper.sin(this.body.yaw) * 5.0F;
            this.rightArm.pivotX = -MathHelper.cos(this.body.yaw) * 5.0F;
            this.leftArm.pivotZ = -MathHelper.sin(this.body.yaw) * 5.0F;
            this.leftArm.pivotX = MathHelper.cos(this.body.yaw) * 5.0F;
            var10000 = this.rightArm;
            var10000.yaw += this.body.yaw;
            var10000 = this.leftArm;
            var10000.yaw += this.body.yaw;
            var10000 = this.leftArm;
            var10000.pitch += this.body.yaw;
            f = 1.0F - this.handSwingProgress;
            f *= f;
            f *= f;
            f = 1.0F - f;
            float g = MathHelper.sin(f * 3.1415927F);
            float h = MathHelper.sin(this.handSwingProgress * 3.1415927F) * -(this.head.pitch - 0.7F) * 0.75F;
            modelPart.pitch -= g * 1.2F + h;
            modelPart.yaw += this.body.yaw * 2.0F;
            modelPart.roll += MathHelper.sin(this.handSwingProgress * 3.1415927F) * -0.4F;
        }
    }

    /**
     * @author Neddslayer
     * @reason because I need javadocs
     */
    @Overwrite
    private void positionRightArm(T entity) {
        if (entity.getStackInHand(entity.getActiveHand()).getItem() == ItemRegistration.CHAOS_ORB) {
            this.rightArm.yaw = -0.1F;
            this.leftArm.yaw = 0.1F;
            this.rightArm.pitch = -1.5707964F;
            this.leftArm.pitch = -1.5707964F;
        }
        else
        {
            switch (this.rightArmPose) {
                case EMPTY -> this.rightArm.yaw = 0.0F;
                case BLOCK -> {
                    this.rightArm.pitch = this.rightArm.pitch * 0.5F - 0.9424779F;
                    this.rightArm.yaw = -0.5235988F;
                }
                case ITEM -> {
                    this.rightArm.pitch = this.rightArm.pitch * 0.5F - 0.31415927F;
                    this.rightArm.yaw = 0.0F;
                }
                case THROW_SPEAR -> {
                    this.rightArm.pitch = this.rightArm.pitch * 0.5F - 3.1415927F;
                    this.rightArm.yaw = 0.0F;
                }
                case BOW_AND_ARROW -> {
                    this.rightArm.yaw = -0.1F + this.head.yaw;
                    this.leftArm.yaw = 0.1F + this.head.yaw + 0.4F;
                    this.rightArm.pitch = -1.5707964F + this.head.pitch;
                    this.leftArm.pitch = -1.5707964F + this.head.pitch;
                }
                case CROSSBOW_CHARGE -> CrossbowPosing.charge(this.rightArm, this.leftArm, entity, true);
                case CROSSBOW_HOLD -> CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
                case SPYGLASS -> {
                    this.rightArm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622F - (entity.isInSneakingPose() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
                    this.rightArm.yaw = this.head.yaw - 0.2617994F;
                }
                case TOOT_HORN -> {
                    this.rightArm.pitch = MathHelper.clamp(this.head.pitch, -1.2F, 1.2F) - 1.4835298F;
                    this.rightArm.yaw = this.head.yaw - 0.5235988F;
                }
            }
        }

    }

    /**
     * @author Neddslayer
     * @reason because I need javadocs
     */
    @Overwrite
    private void positionLeftArm(T entity) {
        if (entity.getStackInHand(entity.getActiveHand()).getItem() == ItemRegistration.CHAOS_ORB) {
            this.rightArm.yaw = -0.1F;
            this.leftArm.yaw = 0.1F;
            this.rightArm.pitch = -1.5707964F;
            this.leftArm.pitch = -1.5707964F;
        }
        else {
            switch (this.leftArmPose) {
                case EMPTY -> this.leftArm.yaw = 0.0F;
                case BLOCK -> {
                    this.leftArm.pitch = this.leftArm.pitch * 0.5F - 0.9424779F;
                    this.leftArm.yaw = 0.5235988F;
                }
                case ITEM -> {
                    this.leftArm.pitch = this.leftArm.pitch * 0.5F - 0.31415927F;
                    this.leftArm.yaw = 0.0F;
                }
                case THROW_SPEAR -> {
                    this.leftArm.pitch = this.leftArm.pitch * 0.5F - 3.1415927F;
                    this.leftArm.yaw = 0.0F;
                }
                case BOW_AND_ARROW -> {
                    this.rightArm.yaw = -0.1F + this.head.yaw - 0.4F;
                    this.leftArm.yaw = 0.1F + this.head.yaw;
                    this.rightArm.pitch = -1.5707964F + this.head.pitch;
                    this.leftArm.pitch = -1.5707964F + this.head.pitch;
                }
                case CROSSBOW_CHARGE -> CrossbowPosing.charge(this.rightArm, this.leftArm, entity, false);
                case CROSSBOW_HOLD -> CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, false);
                case SPYGLASS -> {
                    this.leftArm.pitch = MathHelper.clamp(this.head.pitch - 1.9198622F - (entity.isInSneakingPose() ? 0.2617994F : 0.0F), -2.4F, 3.3F);
                    this.leftArm.yaw = this.head.yaw + 0.2617994F;
                }
                case TOOT_HORN -> {
                    this.leftArm.pitch = MathHelper.clamp(this.head.pitch, -1.2F, 1.2F) - 1.4835298F;
                    this.leftArm.yaw = this.head.yaw + 0.5235988F;
                }
            }
        }

    }
}
