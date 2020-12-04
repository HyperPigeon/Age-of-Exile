package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.library_of_exile.utils.GeometryUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class ParticleInRadiusAction extends SpellAction {

    public enum Shape {
        CIRCLE, HORIZONTAL_CIRCLE, HORIZONTAL_CIRCLE_EDGE
    }

    public ParticleInRadiusAction() {
        super(Arrays.asList(PARTICLE_TYPE, RADIUS, PARTICLE_COUNT));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (ctx.world.isClient) {

            Shape shape = data.getParticleShape();

            DefaultParticleType particle = data.getParticle();

            float radius = data.get(RADIUS)
                .floatValue();

            radius *= ctx.calculatedSpellData.config.getMulti(SpellModEnum.AREA);

            float height = data.getOrDefault(HEIGHT, 0D)
                .floatValue();
            int amount = data.get(PARTICLE_COUNT)
                .intValue();

            amount *= ctx.calculatedSpellData.config.getMulti(SpellModEnum.AREA);

            float yrand = data.getOrDefault(Y_RANDOM, 0D)
                .floatValue();

            if (shape == Shape.CIRCLE) {
                if (ctx.sourceEntity.age > 2) {
                    for (int i = 0; i < amount; i++) {
                        Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(ctx.vecPos, radius);
                        ParticleUtils.spawn(particle, ctx.world, p);
                    }
                }
            } else if (shape == Shape.HORIZONTAL_CIRCLE) {
                for (int i = 0; i < amount; i++) {
                    float yRandom = (int) RandomUtils.RandomRange(0, yrand);
                    Vec3d p = GeometryUtils.getRandomHorizontalPosInRadiusCircle(ctx.vecPos.getX(), ctx.vecPos.getY() + height + yRandom, ctx.vecPos.getZ(), radius);
                    ParticleUtils.spawn(particle, ctx.world, p);
                }
            } else if (shape == Shape.HORIZONTAL_CIRCLE_EDGE) {
                for (int i = 0; i < amount; i++) {
                    float yRandom = (int) RandomUtils.RandomRange(0, yrand);
                    Vec3d p = randomEdgeCirclePos(ctx.vecPos.getX(), ctx.vecPos.getY() + height + yRandom, ctx.vecPos.getZ(), radius);
                    ParticleUtils.spawn(particle, ctx.world, p);
                }
            }
        }
    }

    public static Vec3d randomEdgeCirclePos(double x, double y, double z, float radius) {
        double angle = Math.random() * Math.PI * 2;
        double xpos = x + Math.cos(angle) * radius;
        double zpos = z + Math.sin(angle) * radius;
        return new Vec3d(xpos, y, zpos);
    }

    public MapHolder create(DefaultParticleType particle, Double count, Double radius) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(RADIUS, radius);
        dmg.put(PARTICLE_COUNT, count);
        dmg.put(PARTICLE_TYPE, Registry.PARTICLE_TYPE.getId(particle)
            .toString());
        return dmg;
    }

    @Override
    public String GUID() {
        return "particles_in_radius";
    }
}
