package com.robertx22.age_of_exile.database.data.ingredient;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class SlashIngredient implements JsonExileRegistry<SlashIngredient>, IAutoGson<SlashIngredient>, IAutoLocName {
    public static SlashIngredient SERIALIZER = new SlashIngredient();

    public String id;
    public transient String locname;
    public String item_id;
    public String oaok = ""; // one of a kind
    public int weight;

    public String rar;

    public List<StatModifier> stats = new ArrayList<>();
    public List<String> allowed_in = new ArrayList<>();

    public boolean isOneOfAKind() {
        return !this.oaok.isEmpty();
    }

    public String getOneOfAKindId() {
        return this.oaok;
    }

    public Item getItem() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(item_id));
    }

    public boolean isAllowedInProfession(String id) {
        return allowed_in.contains(id);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.INGREDIENT;
    }

    @Override
    public Class<SlashIngredient> getClassForSerialization() {
        return SlashIngredient.class;
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
        return AutoLocGroup.Item_Sets;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".ingredient." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
