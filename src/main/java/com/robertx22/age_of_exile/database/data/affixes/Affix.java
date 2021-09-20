package com.robertx22.age_of_exile.database.data.affixes;

import com.robertx22.age_of_exile.database.base.IhasRequirements;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.requirements.Requirements;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.*;

import java.util.ArrayList;
import java.util.List;

public class Affix implements IWeighted, IGUID, IAutoLocName, IhasRequirements,
    JsonExileRegistry<Affix>, IAutoGson<Affix> {

    public enum Type {
        prefix,
        suffix,
        dungeon_prefix,
        dungeon_suffix,
        implicit;

        public boolean isPrefix() {
            return this == prefix;
        }

        public boolean isSuffix() {
            return this == suffix;
        }

        public Type getOpposite() {
            if (this.isPrefix()) {
                return suffix;
            }
            if (this.isSuffix()) {
                return prefix;
            }
            return null;
        }
    }

    public String guid;
    public String loc_name;
    public boolean only_one_per_item = true;
    public int weight = 1000;
    public Requirements requirements;
    public List<String> tags = new ArrayList<>();
    public Type type;

    public List<StatModifier> stats = new ArrayList<>();

    @Override
    public boolean isRegistryEntryValid() {
        if (guid == null || loc_name == null || stats.isEmpty() || requirements == null || type == null || weight < 0) {
            return false;
        }

        return true;
    }

    @Override
    public final String datapackFolder() {
        return type.name() + "/";
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.AFFIX;
    }

    public String GUID() {
        return guid;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".affix." + GUID();
    }

    @Override
    public final AutoLocGroup locNameGroup() {
        return AutoLocGroup.Affixes;
    }

    @Override
    public final Requirements requirements() {
        return requirements;
    }

    @Override
    public int Weight() {
        return weight;
    }

    public List<StatModifier> getStats() {
        return stats;
    }

    @Override
    public String locNameForLangFile() {
        return this.loc_name;
    }

    @Override
    public Class<Affix> getClassForSerialization() {
        return Affix.class;
    }
}
