package com.robertx22.age_of_exile.database.data.salvage_outputs;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SalvageOutput implements JsonExileRegistry<SalvageOutput>, IAutoGson<SalvageOutput> {
    public static SalvageOutput SERIALIZER = new SalvageOutput();

    LevelRange levelRange;
    List<WeightedItem> outputs = new ArrayList<>();
    String id = "";
    int weight = 1000;

    public boolean isForItem(int lvl) {
        return lvl >= levelRange.getMinLevel();
    }

    public List<ItemStack> getResult(GearRarity rar) {

        ItemStack stack = new ItemStack(RandomUtils.weightedRandom(outputs)
            .getItem());
        stack.setCount(Math.max(1, (int) rar
            .essence_per_sal.random()));

        List<ItemStack> list = new ArrayList<>();
        list.add(stack);

        return list;
    }

    public SalvageOutput(List<WeightedItem> outputs, String id, LevelRange range) {
        this.outputs = outputs;
        this.id = id;
        this.levelRange = range;
    }

    public SalvageOutput() {
    }

    @Override
    public Class<SalvageOutput> getClassForSerialization() {
        return SalvageOutput.class;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.SALVAGE_OUTPUT;
    }

    @Override
    public String GUID() {
        return id;
    }
}
