package com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import com.robertx22.age_of_exile.vanilla_mc.blocks.BaseTileContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class RuneWordStationContainer extends BaseTileContainer {

    IInventory tile;

    public RuneWordStationContainer(int num, PlayerInventory invPlayer, PacketBuffer packet) {
        this(num, invPlayer, new Inventory(RuneWordStationTile.SLOT_COUNT), packet.readBlockPos());
    }

    public RuneWordStationContainer(int i, PlayerInventory invPlayer, IInventory inventory,
                                    BlockPos pos) {

        super(RuneWordStationTile.SLOT_COUNT, SlashContainers.RUNEWORD.get(), i, invPlayer);
        this.tile = inventory;
        this.pos = pos;
        int count = 0;

        for (int runeslot : RuneWordStationTile.RUNE_SLOTS) {
            int spacing = runeslot > 2 ? 31 : 0;
            addSlot(new Slot(inventory, count++, 111 + spacing + runeslot * 21, 27));
        }

        addSlot(new Slot(inventory, count++, 179, 26));

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 9; y++) {
                int xpos = PLAYER_INVENTORY_XPOS() + y * 18;
                int ypos = PLAYER_INVENTORY_YPOS() - 40 + x * 18;
                addSlot(new Slot(inventory, count++, xpos, ypos));
            }
        }

    }

    @Override
    public int PLAYER_INVENTORY_XPOS() {
        return 108;
    }

    @Override
    public int PLAYER_INVENTORY_YPOS() {
        return 113;
    }

    @Override
    protected int HOTBAR_YPOS() {
        return 171;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return tile.stillValid(player);
    }

}