package com.blocklegend001.flightring.render;

import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WingsRendererAccessories implements AccessoryRenderer {
    @Override
    public <M extends LivingEntity> void render(ItemStack stack,
                                                SlotReference reference,
                                                PoseStack matrixStack,
                                                EntityModel<M> model,
                                                MultiBufferSource multiBufferSource,
                                                int light,
                                                float limbSwing,
                                                float limbSwingAmount,
                                                float partialTicks,
                                                float ageInTicks,
                                                float netHeadYaw,
                                                float headPitch) {

        if (!(model instanceof PlayerModel<?> playerModel)) return;

        AbstractClientPlayer player = (AbstractClientPlayer) reference.entity();
        if (player == null) return;

        if (!player.isAlive()) return;

        WingsRendererLogic.render(matrixStack, multiBufferSource, light, player, playerModel);
    }
}