package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class SummonAtSightAction extends SpellAction {

    public SummonAtSightAction() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.PROJECTILE_ENTITY, MapField.LIFESPAN_TICKS, MapField.HEIGHT));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        Optional<EntityType<?>> projectile = EntityType.byString(data.get(MapField.PROJECTILE_ENTITY));

        Double distance = data.getOrDefault(MapField.DISTANCE, 10D);
        Double height = data.getOrDefault(MapField.HEIGHT, 10D);

        RayTraceResult ray = ctx.caster.pick(distance, 0.0F, false);
        Vector3d pos = ray.getLocation();

        Entity en = projectile.get()
            .create(ctx.world);
        SpellUtils.initSpellEntity(en, ctx.caster, ctx.calculatedSpellData, data);
        en.setPos(pos.x, pos.y + height, pos.z);

        ctx.caster.level.addFreshEntity(en);

    }

    public MapHolder create(EntityType type, Double lifespan, Double height) {
        MapHolder c = new MapHolder();
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.GRAVITY, false);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.HEIGHT, height);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getKey(type)
            .toString());
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "summon_at_sight";
    }

}
