package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.MasterLootGen;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class AddSpawnerExtraLootMethod {

    public static void hookLoot(LootContext context, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {
            if (!context.hasParam(LootParameters.BLOCK_STATE)) {
                return;
            }
            if (!context.hasParam(LootParameters.TOOL)) {
                return;
            }
            if (!context.hasParam(LootParameters.ORIGIN)) {
                return;
            }
            if (!context.hasParam(LootParameters.THIS_ENTITY)) {
                return;
            }
            if (context.getParamOrNull(LootParameters.BLOCK_STATE)
                .getBlock() != Blocks.SPAWNER) {
                return;
            }

            Entity en = context.getParamOrNull(LootParameters.THIS_ENTITY);

            PlayerEntity player = null;
            if (en instanceof PlayerEntity) {
                player = (PlayerEntity) en;
            }
            if (player == null) {
                return;
            }

            ItemStack stack = context.getParamOrNull(LootParameters.TOOL);
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) != 0) {
                return;
            }

            Load.playerRPGData(player).favor.addFavor(ServerContainer.get().FAVOR_GAIN_PER_SPAWNER_DESTROYED.get()
                .floatValue());

            Vector3d p = context.getParamOrNull(LootParameters.ORIGIN);
            BlockPos pos = new BlockPos(p.x, p.y, p.z);

            LootInfo info = LootInfo.ofSpawner(player, context.getLevel(), pos);
            info.multi += 2;
            List<ItemStack> list = MasterLootGen.generateLoot(info);

            ci.getReturnValue()
                .addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
