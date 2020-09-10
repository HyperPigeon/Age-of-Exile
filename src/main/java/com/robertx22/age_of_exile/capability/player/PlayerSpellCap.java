package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class PlayerSpellCap {

    private static final String PLAYER_SPELL_DATA = "player_spells_data";

    public abstract static class ISpellsCap implements ICommonPlayerCap {

        public abstract Spell getCurrentRightClickSpell();

        public abstract Spell getSpellByNumber(int key);

        public abstract SpellCastingData getCastingData();

        public abstract List<Spell> getLearnedSpells(LivingEntity en);

    }

    public static class DefaultImpl extends ISpellsCap {
        SpellCastingData spellCastingData = new SpellCastingData();

        @Override
        public CompoundTag toTag(CompoundTag nbt) {

            try {
                LoadSave.Save(spellCastingData, nbt, PLAYER_SPELL_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return nbt;

        }

        @Override
        public PlayerCaps getCapType() {
            return PlayerCaps.SPELLS;
        }

        @Override
        public void fromTag(CompoundTag nbt) {

            try {
                this.spellCastingData = LoadSave.Load(SpellCastingData.class, new SpellCastingData(), nbt, PLAYER_SPELL_DATA);

                if (spellCastingData == null) {
                    spellCastingData = new SpellCastingData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public Spell getCurrentRightClickSpell() {
            return this.spellCastingData.getSelectedSpell();
        }

        @Override
        public Spell getSpellByNumber(int key) {
            return this.spellCastingData.getSpellByNumber(key);
        }

        @Override
        public SpellCastingData getCastingData() {
            return this.spellCastingData;
        }

        @Override
        public List<Spell> getLearnedSpells(LivingEntity en) {
            List<Spell> list = new ArrayList<>();
            for (Perk x : Load.perks(en)
                .getAllAllocatedPerks()) {
                if (x.getSpell() != null && !x.getSpell()
                    .GUID()
                    .isEmpty()) {
                    Spell spell = x.getSpell();
                    list.add(spell);
                }
            }
            return list;
        }

    }

}
