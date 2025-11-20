package com.blocklegend001.flightring.event;

import com.blocklegend001.flightring.FlightRing;
import com.blocklegend001.flightring.render.WingsRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(modid = FlightRing.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        CuriosRendererRegistry.load();
        event.getSkins().forEach(skin -> {
            PlayerRenderer renderer = event.getSkin(skin);
            assert renderer != null;
            renderer.addLayer(new WingsRenderer(renderer));
        });
    }
}
