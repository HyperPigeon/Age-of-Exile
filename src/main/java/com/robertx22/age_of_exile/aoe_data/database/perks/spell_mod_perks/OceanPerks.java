package com.robertx22.age_of_exile.aoe_data.database.perks.spell_mod_perks;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.stats.spell_mod_stats.OceanSpellModStats;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MarkerStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class OceanPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        of(OceanSpellModStats.HEART_CHILL_ENEMIES, Spells.HEART_OF_ICE).build();
        of(OceanSpellModStats.HEART_MAGIC_SHIELD_RESTORE, Spells.HEART_OF_ICE).build();

        of(OceanSpellModStats.ICE_FLOWER_DMG, Spells.ICE_FLOWER).build();
        of(OceanSpellModStats.ICE_FLOWER_RESTORE, Spells.ICE_FLOWER).build();

    }

    private PerkBuilder of(MarkerStat stat, Spell spell) {
        return PerkBuilder.spellModifier(stat, spell);
    }

}
