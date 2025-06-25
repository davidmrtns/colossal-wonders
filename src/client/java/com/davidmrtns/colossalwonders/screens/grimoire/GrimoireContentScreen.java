package com.davidmrtns.colossalwonders.screens.grimoire;

import com.davidmrtns.colossalwonders.ColossalWonders;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class GrimoireContentScreen extends Screen {
    private static final Identifier BOOK_TEXTURE = new Identifier(ColossalWonders.MOD_ID, "textures/gui/open_grimoire.png");
    private final GrimoireEntryData entryData;
    private int bookX, bookY;
    private int scaledWidth, scaledHeight;
    private int currentPage = 0;

    private static final int BOOK_TEXTURE_WIDTH = 185;
    private static final int BOOK_TEXTURE_HEIGHT = 182;
    private static final int SPRITESHEET_WIDTH = 256;
    private static final float SCALE = 1.25f;
    private static final float TEXT_RENDER_SCALE = 0.9f;

    public GrimoireContentScreen(GrimoireEntryData entryData) {
        super(Text.translatable(entryData.tooltip));
        this.entryData = entryData;
    }

    @Override
    protected void init() {
        this.scaledWidth = (int) (BOOK_TEXTURE_WIDTH * SCALE);
        this.scaledHeight = (int) (BOOK_TEXTURE_HEIGHT * SCALE);

        int marginY = 10;

        this.bookX = (this.width - scaledWidth) / 2;
        this.bookY = (this.height - scaledHeight) / 2;

        int availableHeight = this.height - (marginY * 2);
        if (scaledHeight > availableHeight) {
            this.bookY = marginY;
        } else {
            this.bookY = (this.height - scaledHeight) / 2;
        }

        renderButtons();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        renderBookTexture(context);
        super.render(context, mouseX, mouseY, delta);
        renderText(context);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.getMatrices().push();
        context.getMatrices().translate(0, 0, -100);
        context.fill(0, 0, this.width, this.height, 0x80000000);
        context.getMatrices().pop();
    }

    private void renderText(DrawContext context) {
        String text = entryData.content.get(currentPage).text;

        // margins
        int leftMargin = (int) (36 * SCALE);
        int rightMargin = (int) (35 * SCALE);

        int topMargin = 18;

        // content width considering the margins
        int contentWidth = (int) ((scaledWidth - leftMargin - rightMargin) / TEXT_RENDER_SCALE);

        // absolute position
        int contentX = bookX + leftMargin;
        int contentY = bookY + topMargin;

        List<OrderedText> lines = this.textRenderer.wrapLines(Text.translatable(text), contentWidth);

        context.getMatrices().push();
        context.getMatrices().translate(contentX, contentY, 0);
        context.getMatrices().scale(TEXT_RENDER_SCALE, TEXT_RENDER_SCALE, 1.0f);

        for (int i = 0; i < lines.size(); i++) {
            int lineY = i * this.textRenderer.fontHeight;
            context.drawText(this.textRenderer, lines.get(i), 0, lineY, 0x000000, false);
        }

        context.getMatrices().pop();
    }


    private void renderButtons() {
        // relative positions without scaling
        int nextButtonOffsetX = 130;
        int prevButtonOffsetX = 36;
        int buttonOffsetY = 160;

        int nextButtonX = (int) (bookX + nextButtonOffsetX * SCALE);
        int prevButtonX = (int) (bookX + prevButtonOffsetX * SCALE);
        int buttonY = (int) (bookY + buttonOffsetY * SCALE);

        // previous button
        this.addDrawableChild(new GrimoireButtonWidget(
                prevButtonX, buttonY,
                24, 14,
                Text.translatable("gui.colossal_wonders.previous_page"),
                button -> {
                    if (currentPage > 0) {
                        currentPage--;
                        this.init();
                    }
                },
                BOOK_TEXTURE,
                21, 184,
                22, 11,
                SPRITESHEET_WIDTH, SPRITESHEET_WIDTH,
                SCALE
        ));

        // next button
        this.addDrawableChild(new GrimoireButtonWidget(
                nextButtonX, buttonY,
                24, 14,
                Text.translatable("gui.colossal_wonders.next_page"),
                button -> {
                    if (currentPage < entryData.content.size() - 1) {
                        currentPage++;
                        this.init();
                    }
                },
                BOOK_TEXTURE,
                45, 184,
                22, 11,
                SPRITESHEET_WIDTH, SPRITESHEET_WIDTH,
                SCALE
        ));
    }

    private void renderBookTexture(DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().translate(bookX, bookY, 0);
        context.getMatrices().scale(SCALE, SCALE, 1.0f);

        // draws the book texture
        context.drawTexture(
                BOOK_TEXTURE,
                0, 0,
                BOOK_TEXTURE_WIDTH,
                BOOK_TEXTURE_HEIGHT,
                0, 0,
                BOOK_TEXTURE_WIDTH,
                BOOK_TEXTURE_HEIGHT,
                SPRITESHEET_WIDTH, SPRITESHEET_WIDTH
        );

        context.getMatrices().pop();
    }

    @Override
    public boolean shouldPause() {
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // returns to the grimoire screen if the content screen is closed
        if (keyCode == 256) {
            MinecraftClient.getInstance().setScreen(new GrimoireScreen());
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

}
