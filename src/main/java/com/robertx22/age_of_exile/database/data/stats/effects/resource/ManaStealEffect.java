package com.robertx22.age_of_exile.database.data.stats.effects.resource;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectUtils;

public class ManaStealEffect extends BaseDamageEffect {

    private ManaStealEffect() {
    }

    public static ManaStealEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.Last.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        float healed = ((float) data.getAverageValue() * effect.number / 100);

        effect.manaRestored += healed;

        return effect;

    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return EffectUtils.isConsideredAWeaponAttack(effect);
    }

    private static class SingletonHolder {
        private static final ManaStealEffect INSTANCE = new ManaStealEffect();
    }
}
