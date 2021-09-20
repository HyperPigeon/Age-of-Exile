package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.SyncedToClientValues;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SyncAreaLevelPacket extends MyPacket<SyncAreaLevelPacket> {

    public int lvl;

    public SyncAreaLevelPacket() {

    }

    public SyncAreaLevelPacket(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "arealvl");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        lvl = tag.readInt();
    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeInt(lvl);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        SyncedToClientValues.areaLevel = lvl;
    }

    @Override
    public MyPacket<SyncAreaLevelPacket> newInstance() {
        return new SyncAreaLevelPacket();
    }
}

