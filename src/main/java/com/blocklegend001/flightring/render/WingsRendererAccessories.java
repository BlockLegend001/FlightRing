package com.blocklegend001.flightring.render;

import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class WingsRendererAccessories implements AccessoryRenderer {

    @Override
    public <M extends LivingEntity> void render(ItemStack stack,
                                                SlotReference slotRef,
                                                MatrixStack matrices,
                                                EntityModel<M> entityModel,
                                                VertexConsumerProvider vertexConsumers,
                                                int light,
                                                float limbSwing,
                                                float limbSwingAmount,
                                                float partialTick,
                                                float ageInTicks,
                                                float netHeadYaw,
                                                float headPitch) {

        if (!(entityModel instanceof PlayerEntityModel<?> playerModel)) return;
        PlayerEntity player = (PlayerEntity) slotRef.entity();
        if (player == null) return;

        WingsRenderLogic.renderWings(player, playerModel, matrices, vertexConsumers, light);
    }
}
