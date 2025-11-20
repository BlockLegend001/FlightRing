package com.blocklegend001.flightring.client;

import com.blocklegend001.flightring.item.ModItems;
import com.blocklegend001.flightring.render.WingsRenderer;
import com.blocklegend001.flightring.render.WingsRendererAccessories;
import com.blocklegend001.flightring.render.WingsRendererTrinket;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.player.PlayerEntity;

public class FlightRingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AccessoriesRendererRegistry.registerRenderer(ModItems.FLIGHT_RING, WingsRendererAccessories::new);
        TrinketRendererRegistry.registerRenderer(ModItems.FLIGHT_RING, new WingsRendererTrinket());

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerEntityRenderer playerRenderer) {
                FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>> ctx =
                        (FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>>) (Object) playerRenderer;

                registrationHelper.register(new WingsRenderer(ctx));
            }
        });
    }
}
