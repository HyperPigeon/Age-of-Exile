package com.robertx22.age_of_exile.database.data.currency.upgrades;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.UpgradeData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PlusOneUpgradeItem extends CurrencyItem implements ICurrencyItemEffect, IShapelessRecipe {

    @Override
    public String GUID() {
        return "currency/plus_one_upgrade";
    }

    public static final String ID = SlashRef.MODID + ":currency/plus_one_upgrade";

    @Override
    public int getWeight() {
        return 1000;
    }

    public PlusOneUpgradeItem() {
        super(ID);
    }

    @Override
    public int getTier() {
        return 3;
    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        if (RandomUtils.roll(75)) {
            gear.onUpgrade(ctx.player, UpgradeData.SlotType.UP1);
        }
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_EMPTY_UPGRADE_SLOTS);
    }

    @Override
    public String getRarityRank() {
        return IRarity.UNCOMMON;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Magical Upgrade Stone";
    }

    @Override
    public String locDescForLangFile() {
        return "75% to upgrade the item";
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(this, 1);
        fac.requires(SlashItems.SOURCE_OF_STRENGTH.get(), 9);
        return fac.unlockedBy("player_level", trigger());
    }
}