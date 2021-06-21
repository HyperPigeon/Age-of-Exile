package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques;

import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry.SeasonRings;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry.UniqueNecklaces;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry.UniqueRings;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class UniqueJewelry implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new UniqueNecklaces().registerAll();
        new UniqueRings().registerAll();

        new SeasonRings().registerAll();
    }

}

