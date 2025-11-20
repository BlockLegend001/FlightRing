package com.blocklegend001.flightring.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class WingsRendererCurios implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
                                                                          SlotContext slotContext,
                                                                          PoseStack matrixStack,
                                                                          RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer,
                                                                          int light,
                                                                          float limbSwing,
                                                                          float limbSwingAmount,
                                                                          float partialTicks,
                                                                          float ageInTicks,
                                                                          float netHeadYaw,
                                                                          float headPitch) {

        PlayerModel<?> playerModel;
        try {
            playerModel = (PlayerModel<?>) renderLayerParent.getModel();
        } catch (ClassCastException e) {
            return;
        }

        AbstractClientPlayer player = slotContext.entity() instanceof AbstractClientPlayer p ? p : Minecraft.getInstance().player;
        if (player == null || !player.isAlive()) return;

        WingsRendererLogic.render(matrixStack, renderTypeBuffer, light, player, playerModel);
    }
}
