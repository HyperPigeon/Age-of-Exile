package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PurifyItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {

    @Override
    public String GUID() {
        return "currency/clear_corruption";
    }

    public static final String ID = SlashRef.MODID + ":currency/clear_corruption";

    @Override
    public int getWeight() {
        return 0;
    }

    public PurifyItem() {
        super(ID);
    }

    @Override
    public int getTier() {
        return 1;
    }

    @Override
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        gear.c = false;
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE);
    }

    @Override
    public float getInstability() {
        return 0;
    }

    @Override
    public String getRarityRank() {
        return IRarity.EPIC_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb of Purification";
    }

    @Override
    public String locDescForLangFile() {
        return "Removes corruption from an item.";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
            .define('t', ModRegistry.CURRENCIES.ORB_OF_TRANSMUTATION)
            .define('v', ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(SkillItemTier.TIER0)
                .get())
            .define('o', SlashItems.T3_DUST())
            .pattern("ovo")
            .pattern("vtv")
            .pattern("ovo")
            .unlockedBy("player_level", trigger());
    }

}