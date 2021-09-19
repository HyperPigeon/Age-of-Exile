package com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists;

import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class WeightedMobEntry implements IWeighted {

    public int weight = 1000;
    public String id = "";

    public WeightedMobEntry(int weight, EntityType<?> type) {
        this.weight = weight;
        this.id = Registry.ENTITY_TYPE.getKey(type)
            .toString();
    }

    public WeightedMobEntry() {
    }

    @Override
    public int Weight() {
        return weight;
    }
}
