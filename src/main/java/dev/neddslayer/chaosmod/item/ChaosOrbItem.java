package dev.neddslayer.chaosmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ChaosOrbItem extends Item {
    public ChaosOrbItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            user.addVelocity(0, 10, 0);
            user.velocityModified = true;
            user.getItemCooldownManager().set(this, 60);
            List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class, user.getBoundingBox().expand(20));
            for (LivingEntity entity:
                    entities) {
                Vec3d lookDir = entity.getRotationVector();
                entity.addVelocity(lookDir.getX() * -5,1,lookDir.getZ() * -5);
            }
            user.getStackInHand(hand).damage(2, user, (player) -> {
                player.sendMessage(Text.of("it broke"));
            } );
        }
        return super.use(world, user, hand);
    }
}
