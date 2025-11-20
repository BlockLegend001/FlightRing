package com.blocklegend001.flightring.item;

import com.blocklegend001.flightring.FlightRing;
import com.blocklegend001.flightring.item.custom.FlightRingItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(FlightRing.MODID);

    public static final DeferredItem<Item> FLIGHT_RING = ITEMS.register("flight_ring",
            () -> new FlightRingItem(new Item.Properties().fireResistant().stacksTo(1)));

    public static final DeferredItem<Item> FLIGHT_WINGS = ITEMS.register("flight_wings",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
