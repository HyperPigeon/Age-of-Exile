package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.a_libraries.dmg_number_particle.DamageParticleAdder;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class DmgNumPacket extends MyPacket<DmgNumPacket> {

    public String string;
    public int id;
    public boolean iscrit = false;
    public TextFormatting format = TextFormatting.RED;

    public DmgNumPacket() {

    }

    public DmgNumPacket(LivingEntity entity, String str, boolean iscrit, TextFormatting format) {
        string = str;
        this.id = entity.getId();
        this.iscrit = iscrit;
        this.format = format;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "dmgnum");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        string = tag.readUtf(500);
        id = tag.readInt();
        this.iscrit = tag.readBoolean();
        this.format = TextFormatting.getByName(tag.readUtf(100));

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeUtf(string);
        tag.writeInt(id);
        tag.writeBoolean(iscrit);
        tag.writeUtf(format.getName());

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        if (ClientConfigs.getConfig().ENABLE_FLOATING_DMG.get()) {
            DamageParticleAdder.displayParticle(ctx.getPlayer().level.getEntity(id), this);
        }
    }

    @Override
    public MyPacket<DmgNumPacket> newInstance() {
        return new DmgNumPacket();
    }
}
