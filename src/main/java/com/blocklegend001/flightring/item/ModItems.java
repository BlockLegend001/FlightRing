package com.blocklegend001.flightring.item;

import com.blocklegend001.flightring.FlightRing;
import com.blocklegend001.flightring.item.custom.FlightRingItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item FLIGHT_RING = new FlightRingItem(new Item.Settings().fireproof().maxCount(1));
    public static final Item FLIGHT_WINGS = new Item(new Item.Settings());

    public static void registerModItems() {
        Registry.register(Registries.ITEM, Identifier.of(FlightRing.MOD_ID, "flight_ring"), FLIGHT_RING);
        Registry.register(Registries.ITEM, Identifier.of(FlightRing.MOD_ID, "flight_wings"), FLIGHT_WINGS);

        FlightRing.LOGGER.info("Registering Mod Items for " + FlightRing.MOD_ID);
    }
}
