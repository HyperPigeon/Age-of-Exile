package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import net.minecraft.item.ItemStack;

public class GemLootGen extends BaseLootGen<GearBlueprint> {

    public GemLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) (ModConfig.get().DropRates.GEM_DROPRATE);
    }

    @Override
    public LootType lootType() {
        return LootType.Gem;
    }

    @Override
    public boolean condition() {

        if (info.favorRank != null) {
            if (!info.favorRank.drop_gems) {
                return false;
            }
        }

        return !ExileDB.Gems()
            .getFilterWrapped(x -> this.info.level >= x.getReqLevel()).list.isEmpty();
    }

    @Override
    public ItemStack generateOne() {

        Gem gem = ExileDB.Gems()
            .getFilterWrapped(x -> this.info.level >= x.getReqLevel())
            .random();

        ItemStack stack = new ItemStack(gem.getItem());

        return stack;

    }

}
