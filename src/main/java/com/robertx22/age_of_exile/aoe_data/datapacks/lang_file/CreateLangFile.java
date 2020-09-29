package com.robertx22.age_of_exile.aoe_data.datapacks.lang_file;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DirUtils;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CreateLangFile {

    public static void create() {

        String json = "{\n" + DirUtils.getManualString();

        // dont print duplicates
        List<String> usedGUIDS = new ArrayList<>();

        for (Map.Entry<String, List<IAutoLocName>> entry : getMap().entrySet()) {

            json += CreateLangFileUtils.comment(entry.getKey());
            for (IAutoLocName iauto : entry.getValue()) {

                if (iauto.shouldRegisterLangName() && iauto.locNameForLangFile() != null && !iauto.locNameForLangFile()
                    .isEmpty()) {

                    if (iauto.locNameForLangFile()
                        .contains("\"")) {
                        try {
                            throw new Exception(iauto.locNameForLangFile() + " contains double \"");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (usedGUIDS.contains(iauto.formattedLocNameLangFileGUID())) {
                        continue;
                    }
                    usedGUIDS.add(iauto.formattedLocNameLangFileGUID());

                    json += "\t" + "\"" + iauto.formattedLocNameLangFileGUID() + "\": \"" + iauto.locNameForLangFile() + "\",\n";
                }
            }
            json += CreateLangFileUtils.comment(entry.getKey());

        }
        usedGUIDS.clear();

        for (Map.Entry<String, List<IAutoLocDesc>> entry : getDescMap().entrySet()) {

            json += CreateLangFileUtils.comment(entry.getKey());
            for (IAutoLocDesc iauto : entry.getValue()) {
                if (iauto.shouldRegisterLangDesc() && iauto.locDescForLangFile()
                    .isEmpty() == false) {

                    if (iauto.locDescForLangFile()
                        .contains("\"")) {
                        try {
                            throw new Exception(iauto.locDescForLangFile() + " contains double \"");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (usedGUIDS.contains(iauto.formattedLocDescLangFileGUID())) {
                        continue;
                    }
                    usedGUIDS.add(iauto.formattedLocDescLangFileGUID());

                    json += "\t" + "\"" + iauto.formattedLocDescLangFileGUID() + "\": \"" + iauto.locDescForLangFile() + "\",\n";
                }
            }
            json += CreateLangFileUtils.comment(entry.getKey());

        }

        usedGUIDS.clear();

        json += "\n}";

        json = CreateLangFileUtils.replaceLast(json, ",", ""); // removes last , or else json wont work

        try {
            System.out.println("Starting to create lang file");

            if (Files.exists(Paths.get(DirUtils.langFilePath())) == false) {
                Files.createFile(Paths.get(DirUtils.langFilePath()));
            }

            File file = new File(DirUtils.langFilePath());

            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.close();
            System.out.println("Saved lang file to " + file.toPath()
                .toString());

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static HashMap<String, List<IAutoLocName>> getMap() {
        List<IAutoLocName> list = CreateLangFileUtils.getFromRegistries(IAutoLocName.class);

        list.addAll(SlashRegistry.MobAffixes()
            .getSerializable());
        list.addAll(SlashRegistry.Spells()
            .getSerializable());
        list.addAll(SlashRegistry.UniqueGears()
            .getSerializable());
        list.addAll(SlashRegistry.Affixes()
            .getSerializable());
        list.addAll(SlashRegistry.SpellModifiers()
            .getSerializable());
        list.addAll(SlashRegistry.Runewords()
            .getSerializable());

        List<Stat> stats = SlashRegistry.Stats()
            .getList()
            .stream()
            .filter(x -> !x.isFromDatapack())
            .collect(Collectors.toList());
        list.addAll(SlashRegistry.Stats()
            .getSerializable());
        list.addAll(stats);

        list.addAll(SlashRegistry.GearTypes()
            .getSerializable());
        list.addAll(SlashRegistry.ExileEffects()
            .getSerializable());
        list.addAll(Arrays.asList(Words.values()));
        list.addAll(SlashRegistry.GearRarities()
            .getSerializable());
        list.addAll(SlashRegistry.MobRarities()
            .getSerializable());
        list.addAll(Arrays.asList(Chats.values()));

        SlashRegistry.UniqueGears()
            .getSerializable()
            .forEach(x -> list.add(new UniqueName(x)));

        HashMap<IAutoLocName.AutoLocGroup, List<IAutoLocName>> map = new HashMap<>();

        for (IAutoLocName.AutoLocGroup autoLocGroup : IAutoLocName.AutoLocGroup.values()) {
            map.put(
                autoLocGroup,
                list.stream()
                    .filter(x -> x.locNameGroup()
                        .equals(autoLocGroup))
                    .collect(Collectors.toList())
            );
        }

        HashMap<String, List<IAutoLocName>> sortedMap = new HashMap<>();
        for (Map.Entry<IAutoLocName.AutoLocGroup, List<IAutoLocName>> entry : map.entrySet()) {
            List<IAutoLocName> sortedlist = new ArrayList<>(entry.getValue());
            CreateLangFileUtils.sortName(sortedlist);
            if (sortedlist.size() > 0) {
                sortedMap.put(entry.getValue()
                    .get(0)
                    .getGroupName(), sortedlist);
            }
        }

        return sortedMap;

    }

    public static HashMap<String, List<IAutoLocDesc>> getDescMap() {
        List<IAutoLocDesc> list = CreateLangFileUtils.getFromRegistries(IAutoLocDesc.class);

        list.addAll(SlashRegistry.UniqueGears()
            .getSerializable());

        List<Stat> stats = SlashRegistry.Stats()
            .getList()
            .stream()
            .filter(x -> !x.isFromDatapack())
            .collect(Collectors.toList());
        list.addAll(SlashRegistry.Stats()
            .getSerializable());
        list.addAll(stats);

        HashMap<IAutoLocName.AutoLocGroup, List<IAutoLocDesc>> map = new HashMap<>();

        for (IAutoLocName.AutoLocGroup autoLocGroup : IAutoLocName.AutoLocGroup.values()) {
            map.put(
                autoLocGroup,
                list.stream()
                    .filter(x -> x.locDescGroup()
                        .equals(autoLocGroup))
                    .collect(Collectors.toList())
            );
        }

        HashMap<String, List<IAutoLocDesc>> sortedMap = new HashMap<>();
        for (Map.Entry<IAutoLocName.AutoLocGroup, List<IAutoLocDesc>> entry : map.entrySet()) {
            List<IAutoLocDesc> sortedlist = new ArrayList<>(entry.getValue());
            CreateLangFileUtils.sortDesc(sortedlist);
            if (sortedlist.size() > 0) {
                sortedMap.put(entry.getValue()
                    .get(0)
                    .getDescGroupName(), sortedlist);
            }
        }

        return sortedMap;

    }

}
