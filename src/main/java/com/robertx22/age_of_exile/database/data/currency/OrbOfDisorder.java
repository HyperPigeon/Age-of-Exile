package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.GearEnumLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class OrbOfDisorder extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/randomize_affixes";
    }

    private static final String name = SlashRef.MODID + ":currency/randomize_affixes";

    public OrbOfDisorder() {

        super(name);

    }

    @Override
    public int getTier() {
        return 2;
    }

    @Override
    public int getWeight() {
        return 250;
    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);

        gear.affixes.randomize(gear);
        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.IS_NOT_UNIQUE, GearEnumLocReq.AFFIXES);
    }

    @Override
    public float getInstability() {
        return 15;
    }

    @Override
    public String getRarityRank() {
        return IRarity.COMMON_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb Of Disorder";
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls all affixes.";
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return ShapedRecipeBuilder.shaped(CurrencyItems.ORB_OF_DISORDER.get(), 1)
            .define('#', SlashItems.INFUSED_IRON.get())
            .define('t', CurrencyItems.ORB_OF_TRANSMUTATION.get())
            .define('v', Items.GOLD_NUGGET)
            .define('o', SlashItems.T3_DUST())
            .pattern("o#o")
            .pattern("oto")
            .pattern("vvv")
            .unlockedBy("player_level", trigger());

    }

}
