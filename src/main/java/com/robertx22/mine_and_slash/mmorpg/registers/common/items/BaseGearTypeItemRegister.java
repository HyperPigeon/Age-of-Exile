package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import net.minecraft.item.Item;

import java.util.HashMap;

public class BaseGearTypeItemRegister extends BaseItemRegistrator {

    public HashMap<String, Item> itemMap = new HashMap<>();

    public BaseGearTypeItemRegister() {
        SlashRegistry.GearTypes()
            .getList()
            .forEach(x -> this.itemMap.put(x.GUID(), x.getItemForRegistration()));
    }

}
