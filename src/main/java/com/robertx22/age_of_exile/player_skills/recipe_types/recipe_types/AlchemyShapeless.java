package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModRecipeSerializers;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class AlchemyShapeless extends StationShapeless {

    public AlchemyShapeless(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input) {
        super(id, group, output, input);
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRegistry.RECIPE_TYPES.ALCHEMY_RECIPE;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ALCHEMY.get();
    }

}
