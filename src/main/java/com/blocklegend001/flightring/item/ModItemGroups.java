package com.blocklegend001.flightring.item;

import com.blocklegend001.flightring.FlightRing;
import com.blocklegend001.flightring.item.custom.FlightRingItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup IMMERSIVEORES = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(FlightRing.MOD_ID, "flightring"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.FLIGHT_RING))
                    .displayName(Text.translatable("itemGroup.flightring").formatted(Formatting.DARK_AQUA))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.FLIGHT_RING);
                    }).build());

    public static void registerItemGroups() {
        FlightRing.LOGGER.info("Registering Item Groups for " + FlightRing.MOD_ID);
    }
}