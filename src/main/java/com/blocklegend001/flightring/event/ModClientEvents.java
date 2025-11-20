package com.blocklegend001.flightring.event;

import com.blocklegend001.flightring.FlightRing;
import com.blocklegend001.flightring.render.WingsRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = FlightRing.MODID)
public class ModClientEvents {

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(skin -> {
            PlayerRenderer renderer = event.getSkin(skin);
            assert renderer != null;
            renderer.addLayer(new WingsRenderer(renderer));
        });
    }
}
