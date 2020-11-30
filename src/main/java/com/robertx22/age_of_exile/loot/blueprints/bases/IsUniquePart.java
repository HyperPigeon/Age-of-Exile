package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class IsUniquePart extends BlueprintPart<Boolean, GearBlueprint> {

    public IsUniquePart(GearBlueprint blueprint) {
        super(blueprint);
    }

    public float chance = 0.5F;
    public boolean disabled = false;

    public void setupChances(LootInfo info) {

        if (info.lootOrigin == LootInfo.LootOrigin.CHEST) {
            chance += 2; // todo make configurable
        }
        if (info.world != null) {
            chance *= Database.getDimensionConfig(info.world).unique_gear_drop_multi;
        }

        if (info.playerData != null) {
            if (info.lootOrigin == LootInfo.LootOrigin.CHEST) {
                chance *= info.playerData.getUnit()
                    .getCalculatedStat(TreasureQuality.getInstance())
                    .getMultiplier();
            }
        }

        if (info.favorRank != null) {
            this.disabled = !info.favorRank.drop_unique_gears;
        }
    }

    @Override
    protected Boolean generateIfNull() {
        if (disabled) {
            return false;
        }
        return RandomUtils.roll(chance);
    }
}
