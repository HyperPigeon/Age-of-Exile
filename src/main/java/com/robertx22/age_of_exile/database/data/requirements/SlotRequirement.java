package com.robertx22.age_of_exile.database.data.requirements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.JsonUtils;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.requirements.bases.BaseRequirement;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SlotRequirement extends BaseRequirement<SlotRequirement> {

    public List<BaseGearType> slots = new ArrayList<>();

    public SlotRequirement() {

    }

    private SlotRequirement(BaseGearType slot) {
        this.slots.add(slot);
    }

    private SlotRequirement(List<BaseGearType> slots) {
        this.slots.addAll(slots);
    }

    @Override
    public boolean meetsRequierment(GearRequestedFor requested) {

        for (BaseGearType slot : slots) {
            if (requested.forSlot.GUID()
                .equals(slot.GUID())) {
                return true;
            }
        }
        return false;

    }

    public static SlotRequirement everythingBesides(SlotFamily type) {
        return new SlotRequirement(ExileDB.GearTypes()
            .getFiltered(x -> x.family() != type));

    }

    public static SlotRequirement of(SlotFamily type) {
        return new SlotRequirement(ExileDB.GearTypes()
            .getFiltered(x -> x.family() == type));

    }

    public SlotRequirement plus(Predicate<BaseGearType> pred) {
        this.slots.addAll(ExileDB.GearTypes()
            .getFilterWrapped(pred).list);
        return this;
    }

    public static SlotRequirement of(Predicate<BaseGearType> pred) {
        return new SlotRequirement(ExileDB.GearTypes()
            .getFilterWrapped(pred).list);
    }

    public static SlotRequirement hasBaseStat(Stat stat) {
        return new SlotRequirement(ExileDB.GearTypes()
            .getFiltered(x -> x.baseStats()
                .stream()
                .anyMatch(s -> s.stat.equals(stat.GUID()))));

    }

    public static SlotRequirement of(BaseGearType.SlotTag tag) {
        return new SlotRequirement(ExileDB.GearTypes()
            .getFiltered(x -> x.getTags()
                .contains(tag)));

    }

    @Override
    public String getJsonID() {
        return "slot_req";
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.add(
            "slots",
            JsonUtils.stringListToJsonArray(slots.stream()
                .map(x -> x.GUID())
                .collect(Collectors.toList()))
        );
        return json;
    }

    @Override
    public SlotRequirement fromJson(JsonObject json) {

        try {
            SlotRequirement newobj = new SlotRequirement();

            JsonArray array = json.getAsJsonArray("slots");

            newobj.slots = JsonUtils.jsonArrayToStringList(array)
                .stream()
                .map(x -> ExileDB.GearTypes()
                    .get(x))
                .collect(Collectors.toList());

            return newobj;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info) {

        List<ITextComponent> list = new ArrayList<>();

        list.add(new SText(TextFormatting.GREEN + "Allowed on: "));

        List<BaseGearType> copy = new ArrayList<>(this.slots);

        IFormattableTextComponent comp = new SText(TextFormatting.RED + "");

        List<BaseGearType> armors = ExileDB.GearTypes()
            .getFiltered(x -> x.family()
                .equals(SlotFamily.Armor));
        if (copy.containsAll(armors)) {
            copy.removeIf(x -> x.family()
                .equals(SlotFamily.Armor));
            comp.append(" ")
                .append(new SText("All Armors"));
        }

        List<BaseGearType> weapons = ExileDB.GearTypes()
            .getFiltered(x -> x.family()
                .equals(SlotFamily.Weapon));
        if (copy.containsAll(weapons)) {
            copy.removeIf(x -> x.family()
                .equals(SlotFamily.Weapon));
            comp.append(" ")
                .append(new SText("All Weapons"));
        }

        List<BaseGearType> jewerly = ExileDB.GearTypes()
            .getFiltered(x -> x.family()
                .equals(SlotFamily.Jewelry));
        if (copy.containsAll(jewerly)) {
            copy.removeIf(x -> x.family()
                .equals(SlotFamily.Jewelry));
            comp.append(" ")
                .append(new SText("All Jewerly"));
        }
        copy.forEach(x -> {
            comp.append(" ")
                .append(x.locName());

        });

        list.add(comp);

        return list;
    }
}
