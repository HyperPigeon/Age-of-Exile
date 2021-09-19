package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.player_skills.items.backpacks.IGatheringMat;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class PlantProduceItem extends Item implements IAutoLocName, IAutoModel, IGatheringMat {
    public SkillItemTier tier;

    public PlantProduceItem(SkillItemTier tier) {
        super(new Properties().tab(CreativeTabs.Professions));
        this.tier = tier;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Wheat";
    }

    @Override
    public String GUID() {
        return "";
    }
}
