package com.robertx22.age_of_exile.database.data.talent_tree;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.talent_tree.parser.TalentGrid;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TalentTree implements JsonExileRegistry<TalentTree>, IAutoGson<TalentTree> {

    public static TalentTree SERIALIZER = new TalentTree();

    public int order = 0;
    public String text_format;
    public String identifier;
    public String school_type;
    public String icon;

    public enum SchoolType {
        TALENTS
    }

    public SchoolType getSchool_type() {
        try {
            return SchoolType.valueOf(school_type);
        } catch (Exception e) {
            e.printStackTrace();
            return SchoolType.TALENTS;
        }
    }

    // 2d grid with whitespace
    public String perks = "";

    public transient CalcData calcData = new CalcData();

    @Override
    public Class<TalentTree> getClassForSerialization() {
        return TalentTree.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.TALENT_TREE;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public void onLoadedFromJson() {

        TalentGrid grid = new TalentGrid(this, perks);

        grid.loadIntoTree();

    }

    @Override
    public boolean isRegistryEntryValid() {

        if (false && MMORPG.RUN_DEV_TOOLS) {
            for (Map.Entry<PointData, String> x : this.calcData.perks.entrySet()) {
                if (!ExileDB.Perks()
                    .isRegistered(x.getValue())) {

                    System.out.print("\n Perk of id: " + x.getValue()
                        .replaceAll("\r", "[NEWLINE]") + " doesn't exist, used in spell school: " + this.identifier + " at point: " + x.getKey()
                        .toString());

                }
            }

            ExileDB.Perks()
                .getFilterWrapped(x -> x.type == Perk.PerkType.SPECIAL).list.forEach(x -> {
                    if (this.calcData.perks.values()
                        .stream()
                        .noneMatch(e -> x.GUID()
                            .equals(e))) {
                        System.out.print("\n" + x.GUID() + " is registered but not used in the tree \n");
                    }
                });
        }
        return true;
    }

    @Override
    public boolean shouldGenerateJson() {
        return false; // i'll do these manually as its easier to use a program for a grid then to do it in code
    }

    public static class CalcData {

        public PointData center;

        public transient HashMap<PointData, Set<PointData>> connections = new HashMap<>();
        public transient HashMap<PointData, String> perks = new HashMap<>();

        public Perk getPerk(PointData point) {
            return ExileDB.Perks()
                .get(perks.get(point));
        }

        public boolean isConnected(PointData one, PointData two) {

            if (!connections.containsKey(one)) {
                return false;
            }
            return connections.get(one)
                .contains(two);
        }

        public void addPerk(PointData point, String perk) {
            perks.put(point, perk);
        }

        public void addConnection(PointData from, PointData to) {

            if (from.x == to.x && from.y == to.y) {
                return;
            }

            if (!connections.containsKey(from)) {
                connections.put(from, new HashSet<>());
            }
            if (!connections.containsKey(to)) {
                connections.put(to, new HashSet<>());
            }
            connections.get(from)
                .add(to);
            connections.get(to)
                .add(from);

        }

    }

}
