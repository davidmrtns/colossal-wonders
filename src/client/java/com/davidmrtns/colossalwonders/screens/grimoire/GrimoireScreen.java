package com.davidmrtns.colossalwonders.screens.grimoire;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.stream.Collectors;

public class GrimoireScreen extends Screen {
    private final Map<GrimoireTreeNode, GrimoireEntryWidget> entryWidgets = new java.util.HashMap<>();

    private int dragStartX, dragStartY;
    private int offsetX = 0, offsetY = 0;
    private boolean dragging = false;

    public GrimoireScreen() {
        super(Text.literal("Grimoire Screen"));
    }

    @Override
    protected void init() {
        entryWidgets.clear();

        // mounts the tree nodes
        Map<String, GrimoireEntryData> entryMap = GrimoireDataLoader.ENTRIES.values().stream()
                .collect(Collectors.toMap(e -> e.id, e -> e));
        var roots = GrimoireTreeBuilder.buildTree(entryMap);

        // layout parameters
        int baseX = width / 2;
        int baseY = 40;
        int spacingX = 80;
        int spacingY = 80;

        int[] col = new int[]{0};

        for (GrimoireTreeNode root : roots) {
            renderTree(root, baseX, baseY, col, 0, spacingX, spacingY);
        }
    }

    private void renderTree(GrimoireTreeNode node, int baseX, int baseY, int[] col, int depth, int spacingX, int spacingY) {
        int x = baseX + col[0] * spacingX;
        int y = baseY + depth * spacingY;

        GrimoireEntryData data = node.data;
        ItemStack icon = new ItemStack(Registries.ITEM.getOrEmpty(new Identifier(data.icon)).orElse(Items.BARRIER));

        GrimoireEntryWidget widget = new GrimoireEntryWidget(x + offsetX, y + offsetY, icon, Text.translatable(data.tooltip), data);

        widget.baseX = x;
        widget.baseY = y;

        this.addDrawableChild(widget);
        entryWidgets.put(node, widget);

        for (GrimoireTreeNode child : node.children) {
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

        for (GrimoireEntryWidget widget : entryWidgets.values()) {
            widget.setPosition(widget.baseX + offsetX, widget.baseY + offsetY);
        }

        // draws the lines between related nodes
        context.getMatrices().push();
        context.getMatrices().translate(0, 0, 0);

        for (Map.Entry<GrimoireTreeNode, GrimoireEntryWidget> entry : entryWidgets.entrySet()) {
            GrimoireTreeNode parent = entry.getKey();
            GrimoireEntryWidget parentWidget = entry.getValue();

            int parentX = parentWidget.getX() + parentWidget.getWidth() / 2;
            int parentY = parentWidget.getY() + parentWidget.getHeight() / 2;

            for (GrimoireTreeNode child : parent.children) {
                GrimoireEntryWidget childWidget = entryWidgets.get(child);
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
        for (GrimoireEntryWidget widget : entryWidgets.values()) {
            widget.renderTooltip(context, mouseX, mouseY);
        }
    }

    // mouse drag event
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            // checks for clicks
            for (GrimoireEntryWidget widget : entryWidgets.values()) {
                if (widget.mouseClicked(mouseX, mouseY, button)) {
                    return true; // clicou num widget, não inicia drag
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
}
