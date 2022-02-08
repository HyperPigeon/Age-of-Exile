package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.spells.entities.SimpleArrowEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleProjectileEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleTridentEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class SlashEntities {

    public static void init() {

    }

    public static RegObj<EntityType<SimpleProjectileEntity>> SIMPLE_PROJECTILE = projectile(SimpleProjectileEntity::new, "spell_projectile");
    public static RegObj<EntityType<SimpleArrowEntity>> SIMPLE_ARROW = projectile(SimpleArrowEntity::new, "spell_arrow");
    public static RegObj<EntityType<StationaryFallingBlockEntity>> SIMPLE_BLOCK_ENTITY = projectile(StationaryFallingBlockEntity::new, "spell_block_entity", false);
    public static RegObj<EntityType<SimpleTridentEntity>> SIMPLE_TRIDENT = projectile(SimpleTridentEntity::new, "spell_trident", false);

    private static <T extends Entity> RegObj<EntityType<T>> projectile(EntityType.IFactory<T> factory,
                                                                       String id) {
        return projectile(factory, id, true);

    }

    private static <T extends Entity> RegObj<EntityType<T>> projectile(EntityType.IFactory<T> factory,
                                                                       String id, boolean itemRender) {

        EntityType<T> type = EntityType.Builder.of(factory, EntityClassification.MISC)
            .sized(0.7F, 0.7F)
            .setUpdateInterval(20)
            .setTrackingRange(10)
            .build(id);

        RegObj<EntityType<T>> def = Def.entity(id, () -> type);

        return def;
    }

}


