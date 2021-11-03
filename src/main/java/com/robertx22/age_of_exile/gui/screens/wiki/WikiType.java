package com.robertx22.age_of_exile.gui.screens.wiki;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.gui.screens.wiki.entries.*;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.library_of_exile.registry.Database;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum WikiType {

    UNIQUE_GEARS("unique_gear") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return ExileDB.UniqueGears()
                .getList()
                .stream()
                .map(x -> new UniqueGearEntry(x))
                .collect(Collectors.toList());
        }
    },

    EFFECTS("effect") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return ExileDB.ExileEffects()
                .getList()
                .stream()
                .map(x -> new EffectEntry(x))
                .collect(Collectors.toList());
        }
    },
    SETS("set") {
        @Override
        public List<WikiEntry> getAllEntries() {
            return ExileDB.Sets()
                .getList()
                .stream()
                .map(x -> new SetEntry(x))
                .collect(Collectors.toList());
        }
    },
    DIMENSIONS("dimension") {
        @Override
        public List<WikiEntry> getAllEntries() {

            List<DimensionConfig> configs = ExileDB.DimensionConfigs()
                .getList();
            configs.add((DimensionConfig) Database.getRegistry(ExileRegistryTypes.DIMENSION_CONFIGS)
                .getDefault());

            configs.sort(Comparator.comparingInt(x -> x.min_lvl));

            return configs.stream()
                .map(x -> new DimensionsEntry(x))
                .collect(Collectors.toList());
        }
    };

    List<WikiEntry> craftExp(PlayerSkillEnum skill) {
        List<WikiEntry> list = new ArrayList<>();
        ExileDB.PlayerSkills()
            .get(skill.id).item_craft_exp.forEach(x -> {
                list.add(new CraftingExpEntry(x.getItem(), x.exp));
            });
        return list;
    }

    private String icon;

    WikiType(String icon) {
        this.icon = icon;
    }

    public boolean showsInWiki() {
        return true;
    }

    public abstract List<WikiEntry> getAllEntries();

    public ResourceLocation getIconLoc() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/wiki/buttons/" + icon + ".png");
    }
}
