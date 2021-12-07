package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

public class LevelProvider {

    private final LivingEntity caster;
    private final EntityData data;
    private final MaxLevelProvider maxLevelProvider;

    public LevelProvider(LivingEntity caster, MaxLevelProvider maxLevelProvider) {
        this.caster = caster;
        this.maxLevelProvider = maxLevelProvider;
        this.data = Load.Unit(caster);
    }

    public int getMaxLevel() {
        return maxLevelProvider.getMaxLevel();
    }

    public int getMaxLevelWithBonuses() {
        return maxLevelProvider.getMaxLevelWithBonuses();
    }

    public int getCurrentLevel() {
        int lvl = Load.spells(caster)
            .getLevelOf(maxLevelProvider.GUID());

        return lvl;
    }

    public int getCasterLevel() {
        return Load.Unit(caster)
            .getLevel();
    }

    public EntityData getCasterData() {
        return data;
    }
}
