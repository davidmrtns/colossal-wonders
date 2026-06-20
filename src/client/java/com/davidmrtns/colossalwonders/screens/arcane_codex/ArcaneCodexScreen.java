package com.davidmrtns.colossalwonders.screens.arcane_codex;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.stream.Collectors;

public class ArcaneCodexScreen extends Screen {
    private final Map<ArcaneCodexTreeNode, ArcaneCodexEntryWidget> entryWidgets = new java.util.HashMap<>();

    private int dragStartX, dragStartY;
    private int offsetX = 0, offsetY = 0;
    private boolean dragging = false;

    // stores the instance to return to when closing content screens
    private static ArcaneCodexScreen lastInstance = null;

    public ArcaneCodexScreen() {
        super(Text.literal("Arcane Codex Screen"));
    }

    @Override
    protected void init() {
        entryWidgets.clear();

        // mounts the tree nodes
        Map<String, ArcaneCodexEntryData> entryMap = ArcaneCodexDataLoader.ENTRIES.values().stream()
                .collect(Collectors.toMap(e -> e.id, e -> e));
        var roots = ArcaneCodexTreeBuilder.buildTree(entryMap);

        // layout parameters
        int baseX = width / 2;
        int baseY = 40;
        int spacingX = 80;
        int spacingY = 80;

        int[] col = new int[]{0};

        for (ArcaneCodexTreeNode root : roots) {
            renderTree(root, baseX, baseY, col, 0, spacingX, spacingY);
        }

        // save this instance
        lastInstance = this;
    }

    private void renderTree(ArcaneCodexTreeNode node, int baseX, int baseY, int[] col, int depth, int spacingX, int spacingY) {
        int x = baseX + col[0] * spacingX;
        int y = baseY + depth * spacingY;

        ArcaneCodexEntryData data = node.data;
        ItemStack icon = new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(data.icon)).orElse(Items.BARRIER));

        ArcaneCodexEntryWidget widget = new ArcaneCodexEntryWidget(x + offsetX, y + offsetY, icon, Text.translatable(data.tooltip), data);

        widget.baseX = x;
        widget.baseY = y;

        this.addDrawableChild(widget);
        entryWidgets.put(node, widget);

        for (ArcaneCodexTreeNode child : node.children) {
            col[0]++;
            renderTree(child, baseX, baseY, col, depth + 1, spacingX, spacingY);
        }
    }

    private void drawLine(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        if (x1 == x2) {
            context.fill(x1, Math.min(y1, y2), x1 + 1, Math.max(y1, y2), color);
        }

        else if (y1 == y2) {
            context.fill(Math.min(x1, x2), y1, Math.max(x1, x2), y1 + 1, color);
        } else {
            int steps = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
            for (int i = 0; i < steps; i++) {
                int x = x1 + i * (x2 - x1) / steps;
                int y = y1 + i * (y2 - y1) / steps;
                context.fill(x, y, x + 1, y + 1, color);
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        for (ArcaneCodexEntryWidget widget : entryWidgets.values()) {
            widget.setPosition(widget.baseX + offsetX, widget.baseY + offsetY);
        }

        // draws the lines between related nodes
        context.getMatrices().push();
        context.getMatrices().translate(0, 0, 0);

        for (Map.Entry<ArcaneCodexTreeNode, ArcaneCodexEntryWidget> entry : entryWidgets.entrySet()) {
            ArcaneCodexTreeNode parent = entry.getKey();
            ArcaneCodexEntryWidget parentWidget = entry.getValue();

            int parentX = parentWidget.getX() + parentWidget.getWidth() / 2;
            int parentY = parentWidget.getY() + parentWidget.getHeight() / 2;

            for (ArcaneCodexTreeNode child : parent.children) {
                ArcaneCodexEntryWidget childWidget = entryWidgets.get(child);
                if (childWidget != null) {
                    int childX = childWidget.getX() + childWidget.getWidth() / 2;
                    int childY = childWidget.getY() + childWidget.getHeight() / 2;

                    drawLine(context, parentX, parentY, childX, childY, 0xFFFFFFFF);
                }
            }
        }

        context.getMatrices().pop();

        super.render(context, mouseX, mouseY, delta);

        // tooltips
        for (ArcaneCodexEntryWidget widget : entryWidgets.values()) {
            widget.renderTooltip(context, mouseX, mouseY);
        }
    }

    // mouse drag event
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            // checks for clicks
            for (ArcaneCodexEntryWidget widget : entryWidgets.values()) {
                if (widget.mouseClicked(mouseX, mouseY, button)) {
                    return true; // clicked a widget, do not start dragging
                }
            }

            // starts the mouse dragging event
            dragStartX = (int) mouseX;
            dragStartY = (int) mouseY;
            dragging = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging && button == 0) {
            offsetX += (int) (mouseX - dragStartX);
            offsetY += (int) (mouseY - dragStartY);

            dragStartX = (int) mouseX;
            dragStartY = (int) mouseY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging) {
            dragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public static ArcaneCodexScreen getLastInstance() {
        if (lastInstance == null) {
            lastInstance = new ArcaneCodexScreen();
        }
        return lastInstance;
    }
}
