package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.auto_comp.ItemAutoPowerLevels;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.*;

public class CompatibleItemUtils {

    public static class Data {

        public int minLevel = 0;
        public int maxLevel = 0;

        public boolean isCompatible;

        List<CompatibleItem> all;

        public static Data empty() {
            Data data = new Data();
            data.isCompatible = false;
            data.all = new ArrayList<>();
            return data;
        }

        private Data() {

        }

        public Data(Item item) {

            //Watch watch = new Watch();

            this.all = new ArrayList<>();

            String reg = Registry.ITEM.getId(item)
                .toString();

            all.addAll(Database.CompatibleItems()
                .getFilterWrapped(x -> x.item_id.equals(reg)).list);

            if (ModConfig.get().autoCompatibleItems.ENABLE_AUTOMATIC_COMPATIBLE_ITEMS) {
                if (ModConfig.get().autoCompatibleItems.isValid(item)) {
                    if (all.isEmpty()) {

                        Set<GearSlot> slots = new HashSet<>();

                        Database.GearTypes()
                            .getList()
                            .forEach(x -> {
                                if (BaseGearType.isGearOfThisType(x, item)) {
                                    slots.add(x.getGearSlot());
                                }
                            });

                        slots.forEach(x -> {
                            all.addAll(ItemAutoPowerLevels.getPowerClassification(item)
                                .getAutoCompatibleItems(ItemAutoPowerLevels.getFloatValueOf(item), item, x));
                        });

                    }
                }
            }

            this.isCompatible = !all.isEmpty();

            if (!all.isEmpty()) {

                CompatibleItem min = all.stream()
                    .min(Comparator.comparingInt(s -> Database.GearTypes()
                        .get(s.item_type)
                        .getLevelRange()
                        .getMinLevel()))
                    .get();
                CompatibleItem max = all.stream()
                    .max(Comparator.comparingInt(s -> Database.GearTypes()
                        .get(s.item_type)
                        .getLevelRange()
                        .getMaxLevel()))
                    .get();

                this.minLevel = Database.GearTypes()
                    .get(min.item_type)
                    .getLevelRange()
                    .getMinLevel();

                this.maxLevel = Database.GearTypes()
                    .get(max.item_type)
                    .getLevelRange()
                    .getMaxLevel();
            }

            //watch.print("Compat for :" + CLOC.translate(item.getName()));
        }

        public List<CompatibleItem> getAll() {
            return new ArrayList<>(all);
        }

    }

    public static boolean isCompatible(World world, Item item) {
        return getData(world, item).isCompatible;
    }

    public static Data getData(World world, Item item) {

        if (!Database.areDatapacksLoaded(world)) {
            return Data.empty();
        }
        if (!Cached.COMPATIBLE_ITEMS.containsKey(item)) {
            getPossibleCompatibleItemsFor(item); // this also sets up the map
        }

        return Cached.COMPATIBLE_ITEMS.get(item);
    }

    public static CompatibleItem getRandomCompatibleItemFor(Item item) {
        List<CompatibleItem> list = getPossibleCompatibleItemsFor(item);
        return RandomUtils.weightedRandom(list);
    }

    public static List<CompatibleItem> getPossibleCompatibleItemsFor(Item item) {

        if (!Cached.COMPATIBLE_ITEMS.containsKey(item)) {
            Cached.COMPATIBLE_ITEMS.put(item, new Data(item));
        }

        return Cached.COMPATIBLE_ITEMS.get(item)
            .getAll();
    }

    public static void tryCreateCompatibleItemStats(ItemStack stack, int level, PlayerEntity player) {
        // fast check for every item
        if (!Gear.has(stack)) {

            if (!CompatibleItemUtils.isCompatible(player.world, stack.getItem())) {
                return;
            }

            CompatibleItem config = CompatibleItemUtils.getRandomCompatibleItemFor(stack.getItem());

            if (config != null) {
                // slow check to make absolutely sure it doesnt have stats
                GearItemData gear = Gear.Load(stack);
                if (gear == null) {
                    ItemStack s = config.createStack(player, level, stack);
                    Gear.Save(stack, Gear.Load(s));

                }
            }
        }
    }

    public static void checkAndGenerate(PlayerEntity player) {

        try {

            if (player.world.isClient) {
                return;
            }

            for (ItemStack stack : player.inventory.main) {

                if (stack.isEmpty()) {
                    continue;
                }
                EntityCap.UnitData data = Load.Unit(player);

                tryCreateCompatibleItemStats(stack, data.getLevel(), player);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
