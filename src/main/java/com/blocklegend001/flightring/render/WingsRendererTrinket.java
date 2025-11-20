package com.blocklegend001.flightring.render;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class WingsRendererTrinket implements TrinketRenderer {

    @Override
    public void render(ItemStack stack,
                       SlotReference slotReference,
                       EntityModel<? extends LivingEntity> contextModel,
                       MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers,
                       int light,
                       LivingEntity entity,
                       float limbAngle,
                       float limbDistance,
                       float tickDelta,
                       float animationProgress,
                       float headYaw,
                       float headPitch) {

        if (!(entity instanceof PlayerEntity player)) return;
        if (!(contextModel instanceof PlayerEntityModel<?> playerModel)) return;

        WingsRenderLogic.renderWings(player, playerModel, matrices, vertexConsumers, light);
    }
}
