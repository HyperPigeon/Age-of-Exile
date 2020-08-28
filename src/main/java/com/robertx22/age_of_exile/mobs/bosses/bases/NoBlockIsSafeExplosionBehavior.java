package com.robertx22.age_of_exile.mobs.bosses.bases;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;

public class NoBlockIsSafeExplosionBehavior extends ExplosionBehavior {

    @Override
    public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
        return blockState.getBlock() == Blocks.BEDROCK ? Optional.of(Math.max(blockState.getBlock()
            .getBlastResistance(), fluidState.getBlastResistance())) : Optional.of(0F);
    }
}
