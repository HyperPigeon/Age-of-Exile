package com.robertx22.age_of_exile.gui.screens.dungeon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.TeamSize;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.gui.ItemSlotButton;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class DungeonInfoScreen extends BaseScreen {

    public BlockPos teleporterPos = new BlockPos(0, 0, 0);
    public DungeonData selectedDungeon = null;

    public DungeonInfoScreen(BlockPos pos, DungeonData dungeon) {
        super(Minecraft.getInstance()
            .getWindow()
            .getGuiScaledWidth(), Minecraft.getInstance()
            .getWindow()
            .getGuiScaledHeight());

        this.selectedDungeon = dungeon;
        this.teleporterPos = pos;

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    static int START_X = 130;
    static int START_Y = 30;
    static int LOOT_Y = 120;

    @Override
    protected void init() {
        super.init();

        try {
            RPGPlayerData maps = Load.playerRPGData(mc.player);

            int xoff = this.width / 2;
            int yoff = this.height / 2 - 125;

            // xoff = 0;
            // yoff = 0; // TODO

            if (selectedDungeon == null) {
                return;
            }
            int teamsY = yoff + 185;

            for (TeamSize size : TeamSize.values()) {
                addButton(new StartDungeonButton(size, this, xoff - StartDungeonButton.SIZE_X / 2, teamsY));
                teamsY += 16;

            }

            this.addButton(new DifficultyButton(selectedDungeon.getDifficulty(), xoff - DifficultyButton.xSize / 2 + 20, yoff + 20));
            this.addButton(new DungeonButton(selectedDungeon, xoff - DifficultyButton.xSize / 2 - 20, yoff + 20));

            int x = xoff - 50;
            int y = yoff + LOOT_Y;

            ItemStack randomitem = new ItemStack(Items.CHEST);
            randomitem.setHoverName(new StringTextComponent(TextFormatting.DARK_PURPLE + "Random Unique"));
            this.publicAddButton(new ItemSlotButton(randomitem, x, y));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void renderText(MatrixStack matrix) {

        if (selectedDungeon != null) {

            int xoff = this.width / 2;
            int yoff = this.height / 2 - 125;

            GuiUtils.renderScaledText(matrix, guiLeft + xoff, yoff - 5, 1D, "Dungeon Info", TextFormatting.RED);

        }
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        try {

            ResourceLocation BG = SlashRef.guiId("dungeon/map");

            int BGX = 123;
            int BGY = 235;

            int xoff = this.width / 2;
            int yoff = this.height / 2;

            mc.getTextureManager()
                .bind(BG);
            blit(matrix, xoff - BGX / 2, yoff - BGY / 2, 0, 0, BGX, BGY);

            super.render(matrix, x, y, ticks);

            renderText(matrix);

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Widget b : buttons) {
            b.renderToolTip(matrix, x, y);
        }
    }
}