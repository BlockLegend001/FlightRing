package com.blocklegend001.flightring;

import com.blocklegend001.flightring.event.ModEventHandler;
import com.blocklegend001.flightring.item.ModItems;
import com.blocklegend001.flightring.item.custom.ModCreativeModTabs;
import com.blocklegend001.flightring.render.WingsRendererAccessories;
import com.blocklegend001.flightring.render.WingsRendererCurios;
import com.mojang.logging.LogUtils;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(FlightRing.MODID)
public class FlightRing {
    public static final String MODID = "flightring";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FlightRing(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(ModEventHandler.class);
        ModItems.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(ModItems.FLIGHT_RING.get(), WingsRendererCurios::new);
        AccessoriesRendererRegistry.registerRenderer(ModItems.FLIGHT_RING.get(), WingsRendererAccessories::new);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info(MODID + " loaded!");
    }
}