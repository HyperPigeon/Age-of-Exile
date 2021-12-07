package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.library_of_exile.utils.AllItemStackSavers;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface ISalvagable {

    enum SalvageContext {
        SALVAGE_STATION

    }

    List<ItemStack> getSalvageResult(ItemStack stack);

    boolean isSalvagable(SalvageContext context);

    static ISalvagable load(ItemStack stack) {

        for (ItemstackDataSaver<? extends ISalvagable> saver : AllItemStackSavers.getAllOfClass(ISalvagable.class)) {
            ISalvagable data = saver.loadFrom(stack);
            if (data != null) {
                return data;
            }
        }
        return null;
    }
}
