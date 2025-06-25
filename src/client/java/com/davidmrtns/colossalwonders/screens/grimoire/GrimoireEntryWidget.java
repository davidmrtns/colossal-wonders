package com.davidmrtns.colossalwonders.screens.grimoire;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class GrimoireEntryWidget extends ClickableWidget {
    private final ItemStack icon;
    private final Text tooltip;
    private final GrimoireEntryData data;
    public int baseX;
    public int baseY;

    public GrimoireEntryWidget(int x, int y, ItemStack icon, Text tooltip, GrimoireEntryData data) {
        super(x, y, 20, 20, Text.literal(""));
        this.icon = icon;
        this.tooltip = tooltip;
        this.data = data;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        MinecraftClient.getInstance().setScreen(new GrimoireContentScreen(data));
        System.out.println("Opening the grimoire screen");
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered() && button == 0) { // left click
            this.onClick(mouseX, mouseY);
            return true; // event consumed
        }
        return false;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isHovered()) {
            context.fill(getX(), getY(), getX() + width, getY() + height, 0x55FFFFFF);
        }

        // renders the icon
        int iconX = getX() + (width - 16) / 2;
        int iconY = getY() + (height - 16) / 2;
        context.drawItem(icon, iconX, iconY);
        context.drawItemInSlot(MinecraftClient.getInstance().textRenderer, icon, iconX, iconY);
    }

    public void renderTooltip(DrawContext context, int mouseX, int mouseY) {
        if (isHovered()) {
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, tooltip, mouseX, mouseY);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
