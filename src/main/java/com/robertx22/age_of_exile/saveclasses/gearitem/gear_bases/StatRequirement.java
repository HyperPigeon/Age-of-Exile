package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;

public class StatRequirement implements ISerializable<StatRequirement> {

    //the float means % of lvl up points required

    public static StatRequirement EMPTY = new StatRequirement();

    public float dex_req = 0;
    public float int_req = 0;
    public float str_req = 0;

    public boolean hasAny() {
        return dex_req > 0 || int_req > 0 || str_req > 0;
    }

    public StatRequirement() {
    }

    private int getDex(GearItemData gear) {
        return (int) scale(dex_req, gear);
    }

    private int getInt(GearItemData gear) {
        return (int) scale(int_req, gear);
    }

    private int getStr(GearItemData gear) {
        return (int) scale(str_req, gear);
    }

    private float scale(float val, GearItemData gear) {
        if (val <= 0) {
            return 0;
        }

        float calc = (float) (val * gear.getRarity()
            .statReqMulti());

        return (int) Dexterity.INSTANCE.scale(calc, gear.level);
    }

    public StatRequirement dexterity(float dex_req) {
        this.dex_req = dex_req;
        return this;
    }

    public StatRequirement intelligence(float int_req) {
        this.int_req = int_req;
        return this;
    }

    public StatRequirement strength(float str_req) {
        this.str_req = str_req;
        return this;
    }

    private StatRequirement(float dex_req, float int_req, float str_req) {
        this.dex_req = dex_req;
        this.int_req = int_req;
        this.str_req = str_req;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("dex_req", dex_req);
        json.addProperty("int_req", int_req);
        json.addProperty("str_req", str_req);
        return json;

    }

    @Override
    public StatRequirement fromJson(JsonObject json) {
        return new StatRequirement(json.get("dex_req")
            .getAsFloat(), json.get("int_req")
            .getAsFloat(), json.get("str_req")
            .getAsFloat());

    }
}
