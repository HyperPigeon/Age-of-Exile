package com.robertx22.age_of_exile.database.data.mob_affixes;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.JsonUtils;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobAffix implements JsonExileRegistry<MobAffix>, ISerializable<MobAffix>, IAutoLocName, IApplyableStats {

    List<StatModifier> stats = new ArrayList<>();
    String id = "";
    int weight = 1000;
    public String icon = "";
    public TextFormatting format;
    transient String locName;

    public MobAffix(String id, String locName, TextFormatting format) {
        this.id = id;
        this.locName = locName;
        this.format = format;
    }

    public MobAffix setMods(StatModifier... mods) {
        this.stats = Arrays.asList(mods);
        return this;
    }

    public MobAffix icon(String icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = getDefaultJson();

        json.addProperty("format", format.name());

        json.addProperty("icon", icon);

        if (stats != null) {
            JsonUtils.addStats(stats, json, "stats");
        }

        return json;
    }

    @Override
    public MobAffix fromJson(JsonObject json) {

        MobAffix affix = new MobAffix(
            getGUIDFromJson(json),
            "",
            TextFormatting.valueOf(json.get("format")
                .getAsString()));

        try {
            affix.stats = JsonUtils.getStats(json, "stats");
            affix.icon = json.get("icon")
                .getAsString();
            affix.weight = json.get("weight")
                .getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return affix;
    }

    public MobAffix setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.MOB_AFFIX;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".mob_affix." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locName;
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        try {
            this.stats.forEach(x -> stats.add(x.ToExactStat(100, Load.Unit(en)
                .getLevel())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.MOB_AFFIX, stats));
    }

}
