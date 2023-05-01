package dev.neddslayer.chaosmod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ChaosOrbItem extends Item {
    public ChaosOrbItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            user.sendMessage(Text.of("used it"));
            user.getStackInHand(hand).damage(2, user, (player) -> {
                player.sendMessage(Text.of("it broke"));
            } );
        }
        return super.use(world, user, hand);
    }
}
