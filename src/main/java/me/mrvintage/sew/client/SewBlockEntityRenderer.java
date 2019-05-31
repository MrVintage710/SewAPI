package me.mrvintage.sew.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.Identifier;

import static org.lwjgl.opengl.GL11.*;

@Environment(EnvType.CLIENT)
public abstract class SewBlockEntityRenderer<T extends BlockEntity> extends BlockEntityRenderer<T> {

    protected SewModel model;

    public SewBlockEntityRenderer(SewModel model) {
        this.model = model;
    }

    @Override
    public void render(T blockEntity, double cameraX, double cameraY, double cameraZ, float deltaTickTime, int breakingStage) {
        GlStateManager.enableDepthTest();
        GlStateManager.depthFunc(GL_LEQUAL);
        GlStateManager.depthMask(true);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        GlStateManager.matrixMode(GL_MODELVIEW);

        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)cameraX + 0.5f, (float)cameraY, (float)cameraZ + 0.5f);
        this.bindTexture(new Identifier("textures/block/acacia_log.png"));

        model.render();

        GlStateManager.popMatrix();
    }

    protected abstract void decideState(T blockEntity);
}
