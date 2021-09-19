package com.robertx22.age_of_exile.gui.screens.wiki.entries;

import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.unique_items.drop_filters.DropFilters;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiEntry;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class UniqueGearEntry extends WikiEntry {

    public UniqueGear uniq;

    public UniqueGearEntry(UniqueGear uniq) {
        this.uniq = uniq;
    }

    @Override
    public ItemStack createMainStack() {

        GearBlueprint blueprint = new GearBlueprint(1, 0);
        blueprint.actionsAfterGeneration.clear();

        blueprint.rarity.set(ExileDB.GearRarities()
            .get(uniq.uniqueRarity));
        blueprint.uniquePart.set(uniq);
        blueprint.gearItemSlot.set(blueprint.uniquePart.get()
            .getBaseGear());

        return blueprint.createStack();

    }

    @Override
    public List<ITextComponent> getExtraInfo() {
        List<ITextComponent> list = new ArrayList<>();

        if (!uniq.filters.list.isEmpty()) {
            list.add(new StringTextComponent(""));
            uniq.filters.list.forEach(x -> list.addAll(DropFilters.get(x.type)
                .getTooltip(x)));
        }

        return list;
    }

    @Override
    public String getName() {
        return CLOC.translate(uniq.locName());
    }

    @Override
    public TextFormatting getFormat() {
        return TextFormatting.RED;
    }
}
