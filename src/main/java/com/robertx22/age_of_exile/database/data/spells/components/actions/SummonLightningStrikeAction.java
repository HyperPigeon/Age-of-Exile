package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.SpellUtils;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

public class SummonLightningStrikeAction extends SpellAction {

    public SummonLightningStrikeAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClient) {
            targets.forEach(t -> {
                SpellUtils.summonLightningStrike(t);
            });
        }
    }

    public MapHolder create() {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        return dmg;
    }

    @Override
    public String GUID() {
        return "summon_lightning_strike";
    }
}

