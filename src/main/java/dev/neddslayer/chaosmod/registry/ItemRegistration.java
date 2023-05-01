package dev.neddslayer.chaosmod.registry;

import dev.neddslayer.chaosmod.item.ChaosOrbItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ItemRegistration {

    public static final Item CHAOS_REMNANT = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON));
    public static final Item CHAOS_SHARD = new Item(new FabricItemSettings().recipeRemainder(CHAOS_REMNANT).rarity(Rarity.RARE));
    public static final Item CHAOS_ORB = new ChaosOrbItem(new FabricItemSettings().rarity(Rarity.EPIC).maxDamage(10));

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier("chaosmod", name), item);
    }

    public static void registerItems() {
        register("chaos_remnant", CHAOS_REMNANT);
        register("chaos_shard", CHAOS_SHARD);
        register("chaos_orb", CHAOS_ORB);
    }

}
