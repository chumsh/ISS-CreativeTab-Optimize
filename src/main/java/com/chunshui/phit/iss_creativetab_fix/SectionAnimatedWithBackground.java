package com.chunshui.phit.iss_creativetab_fix;

import com.mojang.blaze3d.systems.RenderSystem;
import net.mcexpanded.fancytabsections.Section.AbstractSectionWithTitle;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class SectionAnimatedWithBackground extends AbstractSectionWithTitle<SectionAnimatedWithBackground> {
    private final ResourceLocation background;
    private final ResourceLocation foreground;
    int horizontalSize = 162;
    int verticalSize = 18;
    int frames = 0;
    float frameTimeInMS = 200;
    final float red;
    final float green;
    final float blue;

    public SectionAnimatedWithBackground(ResourceLocation id, ResourceLocation background, ResourceLocation foreground, Vector3f rgb) {
        super(id);
        this.background = background;
        this.foreground = foreground;
        red = rgb.x;
        green = rgb.y;
        blue = rgb.z;


    }
    @Override
    public void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY) {
        long currentTimeMs = System.currentTimeMillis();
        int currentFrame = (int) ((currentTimeMs / (long)frameTimeInMS) % frames);

        guiGraphics.flush();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        guiGraphics.blit(
                background, topLeftX, topLeftY,
                horizontalSize, verticalSize,
                0, 0,
                horizontalSize, verticalSize,
                horizontalSize, verticalSize
        );

        guiGraphics.setColor(red, green, blue, 1);
        guiGraphics.blit(
                foreground, topLeftX, topLeftY,
                horizontalSize, verticalSize,
                0, currentFrame * verticalSize,
                horizontalSize, verticalSize,
                horizontalSize, verticalSize * frames
        );
        guiGraphics.flush();
        guiGraphics.setColor(1, 1, 1, 1);
        this.renderTitle(guiGraphics, font, topLeftX, topLeftY);
    }
    

    public SectionAnimatedWithBackground setFrames(int frames)
    {
        this.frames = frames;
        return this;
    }

    public SectionAnimatedWithBackground setFrameTimeInMS(float frameTimeInMS)
    {
        this.frameTimeInMS = frameTimeInMS;
        return this;
    }
}
