package dev.neddslayer.chaosmod.mixin;

import dev.neddslayer.chaosmod.registry.ItemRegistration;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static dev.neddslayer.chaosmod.ChaosMod.CHAOS_LOGGER;

@Mixin(net.minecraft.client.render.entity.model.BipedEntityModel.class)
public abstract class BipedEntityModel<T extends LivingEntity> {

    @Shadow public net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose leftArmPose;

    @Shadow @Final public ModelPart leftArm;

    @Shadow public net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose rightArmPose;

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart head;

    /**
     * @author Neddslayer
     * @reason because I need javadocs
     */
    @Overwrite
    private void positionRightArm(T entity) {
        if (entity.getStackInHand(entity.getActiveHand()).getItem() == Items.BAT_SPAWN_EGG) {
            this.rightArm.yaw = -0.1F + (this.head.yaw / 4);
            this.leftArm.yaw = 0.1F + (this.head.yaw / 4);
            this.rightArm.pitch = -1.5707964F + this.head.pitch;
            this.leftArm.pitch = -1.5707964F + this.head.pitch;
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
        if (entity.getStackInHand(entity.getActiveHand()).getItem() == Items.BAT_SPAWN_EGG) {
            this.rightArm.yaw = -0.1F + (this.head.yaw / 2);
            this.leftArm.yaw = 0.1F + (this.head.yaw / 2);
            this.rightArm.pitch = -1.5707964F + this.head.pitch;
            this.leftArm.pitch = -1.5707964F + this.head.pitch;
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
