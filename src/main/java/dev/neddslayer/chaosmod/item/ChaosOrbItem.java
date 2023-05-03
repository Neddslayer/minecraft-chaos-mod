package dev.neddslayer.chaosmod.item;

import dev.neddslayer.chaosmod.registry.ItemRegistration;
import dev.neddslayer.chaosmod.registry.PacketRegistration;
import dev.neddslayer.chaosmod.registry.SoundRegistration;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
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
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundRegistration.CHAOS_ORB_RELEASE_ENERGY_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            user.getItemCooldownManager().set(this, 10);
            List<Entity> entities = world.getOtherEntities(user, user.getBoundingBox().expand(20));
            for (Entity entity:
                    entities) {
                if (entity != user && entity instanceof LivingEntity) {
                    // Calculate the direction away from the player
                    Vec3d playerPos = user.getPos();
                    Vec3d entityPos = entity.getPos();
                    Vec3d direction = entityPos.subtract(playerPos).normalize();

                    // Apply a force to push the entity away from the player
                    entity.addVelocity(direction.x * 2.0, 0.5, direction.z * 2.0);

                    ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 5));
                    ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 5));
                }
            }
            user.getStackInHand(hand).damage(2, user, (player) -> {
                player.sendMessage(Text.of("it broke"));
            } );
        }
        return super.use(world, user, hand);
    }

    public static void onLeftClick(ItemStack stack) {
        if (stack.getItem() == ItemRegistration.CHAOS_ORB) {
            PacketByteBuf buf = PacketByteBufs.create();
            ClientPlayNetworking.send(PacketRegistration.CHAOS_ORB_DASH_FORWARD, buf);
        }
    }
}
