package com.robertx22.mine_and_slash.database.data.spells.entities.proj;

import com.robertx22.mine_and_slash.database.data.spells.entities.bases.EntityBaseProjectile;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ParticleRegister;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.GeometryUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class LightningTotemEntity extends EntityBaseProjectile {

    public LightningTotemEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public LightningTotemEntity(World worldIn) {
        super(ModRegistry.ENTITIES.LIGHTNING_TOTEM, worldIn);

    }

    @Override
    public double radius() {
        return 2F;
    }

    @Override
    protected void onImpact(HitResult result) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    @Override
    public void onTick() {

        int tickRate = getSpellData().configs.get(SC.TICK_RATE)
            .intValue();

        if (this.age % tickRate == 0) {
            if (!world.isClient) {

                List<LivingEntity> entities = EntityFinder.start(getCaster(), LivingEntity.class, getPosVector())
                    .radius(radius())
                    .build();

                entities.forEach(x -> {

                    SpellDamageEffect dmg = dealSpellDamageTo(x, new Options().activatesEffect(false)
                        .knockbacks(false));

                    dmg.Activate();

                    SoundUtils.playSound(this, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1, 1);

                });
            } else {

                SoundUtils.playSound(this, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1, 1);
                // TODO why isnt this being cast?

            }
        }

        if (this.inGround && world.isClient) {

            for (int i = 0; i < 80; i++) {
                Vec3d p = GeometryUtils.getRandomPosInRadiusCircle(getPosVector(), (float) radius());
                ParticleUtils.spawn(ParticleRegister.THUNDER, world, p);

            }

        }

    }

}
