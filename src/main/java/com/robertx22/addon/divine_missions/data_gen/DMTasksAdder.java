package com.robertx22.addon.divine_missions.data_gen;

import com.robertx22.addon.divine_missions.types.CompleteDungeonTask;
import com.robertx22.divine_missions.database.WorthTypeIds;
import com.robertx22.divine_missions.database.db_types.TaskEntry;

public class DMTasksAdder {

    public static TaskEntry COMPLETE_DUNGEON = completeDungeon();

    public static void init() {

    }

    private static TaskEntry completeDungeon() {

        TaskEntry en = new TaskEntry();
        en.weight = 500;
        en.id = "complete_dungeon";
        en.worths.put(WorthTypeIds.DEFAULT, 1000);
        en.worths.put(WorthTypeIds.AGE_OF_EXILE, 2000);
        en.seconds = 60 * 20;
        en.min = 1;
        en.max = 1;
        en.task_type = CompleteDungeonTask.ID;
        en.addToSerializables();
        return en;
    }
}
