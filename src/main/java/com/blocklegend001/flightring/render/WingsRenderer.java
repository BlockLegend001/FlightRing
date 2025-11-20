package com.blocklegend001.flightring.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;

public class WingsRenderer extends RenderLayer<PlayerRenderState, PlayerModel> {

    public WingsRenderer(RenderLayerParent<PlayerRenderState, PlayerModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight,
                       PlayerRenderState state,
                       float limbSwing,
                       float limbSwingAmount) {

        PlayerModel model = this.getParentModel();

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.level != null ? Minecraft.getInstance().player : null;

        if (!(player instanceof AbstractClientPlayer clientPlayer)) return;
        if (!clientPlayer.isAlive()) return;

        WingsRendererLogic.render(poseStack, buffer, packedLight, clientPlayer, model);
    }
}
