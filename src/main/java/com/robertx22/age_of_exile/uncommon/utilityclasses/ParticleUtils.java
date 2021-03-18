package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleUtils {
    public static void spawn(ParticleEffect type, World world, Vec3d vec, Vec3d mot) {
        world.addParticle(type, vec.x, vec.y, vec.z, mot.x, mot.y, mot.z);
    }

    public static void spawn(ParticleEffect type, World world, Vec3d vec) {
        world.addParticle(type, vec.x, vec.y, vec.z, 0, 0, 0);
    }

    public static void spawn(ParticleEffect particleData, World world, double x, double y, double z, double xSpeed,
                             double ySpeed, double zSpeed) {
        world.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);

    }

    public static void spawnParticles(ParticleType particle, LivingEntity en, int amount) {

        ParticleEnum.sendToClients(en, new ParticlePacketData(en.getPos(), ParticleEnum.AOE).radius(1)
            .type(particle)
            .amount(amount));

    }

}
