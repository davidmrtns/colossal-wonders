package com.davidmrtns.colossalwonders.renderer;

import com.davidmrtns.colossalwonders.entities.custom.ShieldDomeEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

import org.joml.Matrix4f;

public class ShieldDomeRenderer extends EntityRenderer<ShieldDomeEntity> {

    public ShieldDomeRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(ShieldDomeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        // centers the shield on the player
        matrices.translate(0.0, 1.2, 0.0);

        Matrix4f matrix = matrices.peek().getPositionMatrix();
        var vertexConsumer = vertexConsumers.getBuffer(
                RenderLayer.getEntityTranslucentCull(
                        new Identifier("textures/block/glass.png"))); // placeholder texture

        float r = 0.3f, g = 0.6f, b = 1.0f, alpha = 0.3f;
        float s = 1.6f;

        // cube faces
        // BASE
        drawQuad(matrix, vertexConsumer, -s, -s, -s, s, -s, s, r, g, b, alpha, light, new Vector3f(0, -1, 0));
        // TOP
        drawQuad(matrix, vertexConsumer, -s, s, -s, s, s, s, r, g, b, alpha, light, new Vector3f(0, 1, 0));
        // FRONT (Z+)
        drawQuad(matrix, vertexConsumer, -s, -s, s, s, s, s, r, g, b, alpha, light, new Vector3f(0, 0, 1));
        // BACK (Z-)
        drawQuad(matrix, vertexConsumer, -s, -s, -s, s, s, -s, r, g, b, alpha, light, new Vector3f(0, 0, -1));
        // LEFT (X-)
        drawQuad(matrix, vertexConsumer, -s, -s, -s, -s, s, s, r, g, b, alpha, light, new Vector3f(-1, 0, 0));
        // RIGHT (X+)
        drawQuad(matrix, vertexConsumer, s, -s, -s, s, s, s, r, g, b, alpha, light, new Vector3f(1, 0, 0));

        matrices.pop();
    }

    private void drawQuad(Matrix4f matrix, VertexConsumer vc,
                          float x1, float y1, float z1,
                          float x2, float y2, float z2,
                          float r, float g, float b, float a,
                          int light, Vector3f normal) {
        vc.vertex(matrix, x1, y1, z1).color(r, g, b, a).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x(), normal.y(), normal.z()).next();
        vc.vertex(matrix, x2, y1, z1).color(r, g, b, a).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x(), normal.y(), normal.z()).next();
        vc.vertex(matrix, x2, y2, z2).color(r, g, b, a).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x(), normal.y(), normal.z()).next();

        vc.vertex(matrix, x1, y1, z1).color(r, g, b, a).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x(), normal.y(), normal.z()).next();
        vc.vertex(matrix, x2, y2, z2).color(r, g, b, a).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x(), normal.y(), normal.z()).next();
        vc.vertex(matrix, x1, y2, z2).color(r, g, b, a).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal.x(), normal.y(), normal.z()).next();
    }


    @Override
    public Identifier getTexture(ShieldDomeEntity entity) {
        return null; // currently no texture
    }
}

