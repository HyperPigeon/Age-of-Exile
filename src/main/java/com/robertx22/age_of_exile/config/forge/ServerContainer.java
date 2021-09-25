package com.robertx22.age_of_exile.config.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class ServerContainer {

    public static final ForgeConfigSpec commonSpec;
    public static final ServerContainer COMMON;

    public static ServerContainer get() {
        return COMMON;
    }

    static {
        final Pair<ServerContainer, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerContainer::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    ServerContainer(ForgeConfigSpec.Builder b) {
        b.comment("General Configs")
            .push("general");

        ENABLE_FAVOR_SYSTEM = b.define("enable_favor", true);
        ALL_PLAYERS_ARE_TEAMED_PVE_MODE = b.define("all_players_are_allied", false);
        GET_STARTER_ITEMS = b.define("start_items", true);
        ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER = b.define("scale_mob_to_nearby_player_lvl", false);
        ENABLE_LOOT_ANNOUNCEMENTS = b.define("loot_announcements", true);
        REQUIRE_TEAM_FOR_TEAM_DUNGEONS = b.define("require_team_for_dungeons", true);
        DONT_SYNC_DATA_OF_AMBIENT_MOBS = b.define("dont_sync_ambient_mob_data", true);

        STARTING_FAVOR = b.defineInRange("starting_favor", 100, 0, 100000);
        MAX_UNIQUE_GEARS_WORN = b.defineInRange("max_uniques_worn", 100, 0, 20);

        FAVOR_GAIN_PER_CHEST_LOOTED = b.defineInRange("favor_per_chest", 10D, 0, 1000);
        REGEN_HUNGER_COST = b.defineInRange("regen_hunger_cost", 10D, 0, 1000);
        EXP_LOSS_ON_DEATH = b.defineInRange("death_exp_penalty", 0.1D, 0, 1);
        EXP_GAIN_MULTI = b.defineInRange("exp_gain_multi", 1D, 0, 1000);
        PARTY_RADIUS = b.defineInRange("party_radius", 200D, 0, 1000);
        MAX_INSTABILITY = b.defineInRange("max_instability", 1000D, 0, 10000000);
        LEVEL_DISTANCE_PENALTY_PER_TIER = b.defineInRange("lvl_distance_penalty_per_tier", 0.5D, 0, 1D);
        LEVEL_DISTANCE_PENALTY_MIN_MULTI = b.defineInRange("min_loot_chance", 0.01D, 0, 1);
        EXTRA_MOB_STATS_PER_LEVEL = b.defineInRange("extra_mob_stats_per_lvl", 0.02D, 0, 1000);
        VANILLA_MOB_DMG_AS_EXILE_DMG = b.defineInRange("vanilla_mob_dmg_as_exile_dmg", 1D, 0, 1000);
        VANILLA_MOB_DMG_AS_EXILE_DMG_AT_MAX_LVL = b.defineInRange("vanilla_mob_dmg_as_exile_dmg_at_max_lvl", 1D, 0, 1000);
        PVP_DMG_MULTI = b.defineInRange("pvp_dmg_multi", 1D, 0, 1000);

        GEAR_DROPRATE = b.defineInRange("pvp_dmg_multi", 7D, 0, 1000);
        GEM_DROPRATE = b.defineInRange("pvp_dmg_multi", 0.5D, 0, 1000);
        INGREDIENT_DROPRATE = b.defineInRange("pvp_dmg_multi", 5D, 0, 1000);
        RUNE_DROPRATE = b.defineInRange("pvp_dmg_multi", 0.05D, 0, 1000);
        CURRENCY_DROPRATE = b.defineInRange("pvp_dmg_multi", 0.2D, 0, 1000);

        b.pop();
    }

    public ForgeConfigSpec.BooleanValue ENABLE_FAVOR_SYSTEM;
    public ForgeConfigSpec.BooleanValue ALL_PLAYERS_ARE_TEAMED_PVE_MODE;
    public ForgeConfigSpec.BooleanValue GET_STARTER_ITEMS;
    public ForgeConfigSpec.BooleanValue ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER;
    public ForgeConfigSpec.BooleanValue ENABLE_LOOT_ANNOUNCEMENTS;
    public ForgeConfigSpec.BooleanValue REQUIRE_TEAM_FOR_TEAM_DUNGEONS;
    public ForgeConfigSpec.BooleanValue DONT_SYNC_DATA_OF_AMBIENT_MOBS;

    public ForgeConfigSpec.IntValue STARTING_FAVOR;
    public ForgeConfigSpec.IntValue MAX_UNIQUE_GEARS_WORN;

    public ForgeConfigSpec.DoubleValue FAVOR_GAIN_PER_CHEST_LOOTED;
    public ForgeConfigSpec.DoubleValue REGEN_HUNGER_COST;
    public ForgeConfigSpec.DoubleValue EXP_LOSS_ON_DEATH;
    public ForgeConfigSpec.DoubleValue EXP_GAIN_MULTI;
    public ForgeConfigSpec.DoubleValue PARTY_RADIUS;
    public ForgeConfigSpec.DoubleValue MAX_INSTABILITY;
    public ForgeConfigSpec.DoubleValue LEVEL_DISTANCE_PENALTY_PER_TIER;
    public ForgeConfigSpec.DoubleValue LEVEL_DISTANCE_PENALTY_MIN_MULTI;
    public ForgeConfigSpec.DoubleValue EXTRA_MOB_STATS_PER_LEVEL;
    public ForgeConfigSpec.DoubleValue VANILLA_MOB_DMG_AS_EXILE_DMG;
    public ForgeConfigSpec.DoubleValue VANILLA_MOB_DMG_AS_EXILE_DMG_AT_MAX_LVL;
    public ForgeConfigSpec.DoubleValue PVP_DMG_MULTI;

    public ForgeConfigSpec.DoubleValue GEAR_DROPRATE;
    public ForgeConfigSpec.DoubleValue GEM_DROPRATE;
    public ForgeConfigSpec.DoubleValue INGREDIENT_DROPRATE;
    public ForgeConfigSpec.DoubleValue RUNE_DROPRATE;
    public ForgeConfigSpec.DoubleValue CURRENCY_DROPRATE;

    public List<String> BLACKLIST_SPELLS_IN_DIMENSIONS = Arrays.asList("modid:testdim");

}
