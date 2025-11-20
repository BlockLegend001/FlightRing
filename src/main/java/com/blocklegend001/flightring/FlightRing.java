package com.blocklegend001.flightring;

import com.blocklegend001.flightring.item.ModItemGroups;
import com.blocklegend001.flightring.item.ModItems;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class FlightRing implements ModInitializer {
    public static final String MOD_ID = "flightring";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();
    }
}
