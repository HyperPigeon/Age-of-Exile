package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class TooltipContext {

    public TooltipContext(ItemStack stack, List<Text> tooltip, EntityData data) {
        this.stack = stack;
        this.tooltip = tooltip;
        this.data = data;
    }

    public ItemStack stack;
    public List<Text> tooltip;
    public EntityData data;

}
