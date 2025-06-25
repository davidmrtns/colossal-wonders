package com.davidmrtns.colossalwonders.screens.grimoire;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GrimoireButtonWidget extends ButtonWidget {
    private final Identifier texture;
    private final int u; // starting X position
    private final int v; // starting Y position
    private final int textureWidth; // icon spritesheet width
    private final int textureHeight; // icon spritesheet height
    private final int fullTextureWidth; // total spritesheet width
    private final int fullTextureHeight; // total spritesheet height
    private final float renderScale; // texture scale

    public GrimoireButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress,
                            Identifier texture, int u, int v, int textureWidth, int textureHeight,
                            int fullTextureWidth, int fullTextureHeight, float renderScale) {
        super(x, y, width, height, message, onPress, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.fullTextureWidth = fullTextureWidth;
        this.fullTextureHeight = fullTextureHeight;
        this.renderScale = renderScale;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int scaledIconWidth = (int) (this.textureWidth * this.renderScale);
        int scaledIconHeight = (int) (this.textureHeight * this.renderScale);

        int iconRenderX = this.getX() + (this.getWidth() - scaledIconWidth) / 2;
        int iconRenderY = this.getY() + (this.getHeight() - scaledIconHeight) / 2;

        // draws the button texture
        context.drawTexture(this.texture,
                iconRenderX, iconRenderY,
                scaledIconWidth, scaledIconHeight,
                this.u, this.v,
                this.textureWidth, this.textureHeight,
                this.fullTextureWidth, this.fullTextureHeight);

        // hover effect
        if (this.isHovered()) {
            context.fill(
                    iconRenderX, iconRenderY,
                    iconRenderX + scaledIconWidth,
                    iconRenderY + scaledIconHeight,
                    0x30FFFFFF
            );
        }

        // use if there's any tooltip
        /*if (this.isHovered() && this.getMessage() != Text.empty()) {
            context.drawTooltip(minecraftClient.textRenderer, this.getMessage(), mouseX, mouseY);
        }*/
    }
}
