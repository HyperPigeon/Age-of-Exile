package com.robertx22.age_of_exile.gui.overlays.mob_bar;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LookUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class MobBarScreen extends DrawableHelper implements HudRenderCallback {

    private MinecraftClient mc;

    Identifier TEX = new Identifier(Ref.MODID, "textures/gui/mob_bar/mob_bar.png");

    public MobBarScreen() {
        super();
        this.mc = MinecraftClient.getInstance();

    }

    int xSize = 102;
    int ySize = 7;

    LivingEntity en;
    int ticks = 0;

    @Override
    public void onHudRender(MatrixStack matrix, float v) {

        try {

            if (true) {
                return;
            }
            if (!ModConfig.get().client.RENDER_SIMPLE_MOB_BAR) {
                return;
            }

            Entity e = LookUtils.getEntityLookedAt(mc.player);

            if (e instanceof LivingEntity) {
                en = (LivingEntity) e;
            } else {
                ticks++;
                if (ticks > 20) {
                    en = null;
                    ticks = 0;
                }
            }

            if (en != null) {

                EntityData data = Load.Unit(en);

                if (data != null && data.getUnit() != null) {
                    int currentHp = (int) en.getHealth();
                    int maxHP = (int) HealthUtils.getMaxHealth(en);
                    int percent = Math.round((float) currentHp / (float) maxHP * 100);

                    int height = mc.getWindow()
                        .getScaledHeight();
                    int width = mc.getWindow()
                        .getScaledWidth();

                    int x = width / 2 - xSize / 2;
                    int y = 30;

                    mc.getTextureManager()
                        .bindTexture(TEX);

                    drawTexture(matrix, x, y, 0, 0, xSize, ySize); // the bar

                    drawTexture(matrix, x, y, 0, ySize, (int) ((float) xSize * percent / 100F), ySize); // inner fill texture

                    String name = CLOC.translate(data.getName());

                    mc.textRenderer.drawWithShadow(matrix, name, width / 2 - mc.textRenderer.getWidth(name) / 2,
                        y - 10, Formatting.WHITE.getColorValue()
                    );

                    String hpText = (int) currentHp + "/" + (int) maxHP;

                    GuiUtils.renderScaledText(matrix,
                        width / 2, (int) (y + (float) ySize / 2) + 1, 0.75F, hpText, Formatting.GREEN);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
