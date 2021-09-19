package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public class OnTickCancelTargettingOtherMobs {
    public static void cancelTarget(MobEntity mob) {
        if (mob.tickCount % 50 == 0) {
            if (WorldUtils.isMapWorldClass(mob.level)) {
                if (mob.getTarget() instanceof PlayerEntity == false) {
                    mob.setTarget(null);
                }
            }
        }
    }
}
