package com.blocklegend001.flightring.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class WingsRendererCurios implements ICurioRenderer {
    @Override
    public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(ItemStack stack,
                                                                                             SlotContext slotContext,
                                                                                             PoseStack poseStack,
                                                                                             @NotNull MultiBufferSource renderTypeBuffer,
                                                                                             int packedLight,
                                                                                             S renderState,
                                                                                             RenderLayerParent<S, M> renderLayerParent,
                                                                                             EntityRendererProvider.Context context,
                                                                                             float yRotation,
                                                                                             float xRotation) {
        PlayerModel playerModel;
        try {
            playerModel = (PlayerModel) renderLayerParent.getModel();
        } catch (ClassCastException e) {
            return;
        }

        AbstractClientPlayer player = slotContext.entity() instanceof AbstractClientPlayer p ? p : Minecraft.getInstance().player;
        if (player == null || !player.isAlive()) return;

        WingsRendererLogic.render(poseStack, renderTypeBuffer, packedLight, player, playerModel);

        ICurioRenderer.super.render(stack, slotContext, poseStack, renderTypeBuffer, packedLight, renderState, renderLayerParent, context, yRotation, xRotation);
    }
}
