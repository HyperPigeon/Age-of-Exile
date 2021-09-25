package com.robertx22.age_of_exile.gui.overlays.spell_cast_bar;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;

public class SpellCastBarOverlay extends AbstractGui {

    private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");

    static int WIDTH = 182;
    static int HEIGHT = 5;

    Minecraft mc = Minecraft.getInstance();

    public void onHudRender(MatrixStack matrix, float v) {

        EntitySpellCap.ISpellsCap data = Load.spells(mc.player);

        if (data == null) {
            return;
        }

        if (data.getCastingData()
            .isCasting() && data.getCastingData().castingTicksLeft > 0) {

            int x = mc.getWindow()
                .getGuiScaledWidth() / 2 - WIDTH / 2;
            int y = (int) (mc.getWindow()
                .getGuiScaledHeight() / 1.25F - HEIGHT / 2);

            float percent =
                ((float) data.getCastingData().lastSpellCastTimeInTicks - (float) data.getCastingData().castingTicksLeft) / (float) data
                    .getCastingData().lastSpellCastTimeInTicks;

            render(matrix, x, y, BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20, percent);
        }
    }

    private void render(MatrixStack matrix, int x, int y, BossInfo.Color color, BossInfo.Overlay overlay, float percent) {

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager()
            .bind(GUI_BARS_TEXTURES);

        blit(matrix, x, y, 0, color.ordinal() * 5 * 2, WIDTH, HEIGHT);
        if (overlay != BossInfo.Overlay.PROGRESS) {
            blit(matrix, x, y, 0, 80 + (overlay.ordinal() - 1) * 5 * 2, WIDTH, HEIGHT);
        }

        int i = (int) (percent * 183.0F);
        if (i > 0) {
            blit(matrix, x, y, 0, color.ordinal() * 5 * 2 + 5, i, HEIGHT);
            if (overlay != BossInfo.Overlay.PROGRESS) {
                blit(matrix, x, y, 0, 80 + (overlay.ordinal() - 1) * 5 * 2 + 5, i, HEIGHT);
            }
        }

    }

}
