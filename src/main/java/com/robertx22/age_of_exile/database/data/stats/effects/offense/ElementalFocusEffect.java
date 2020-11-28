package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ElementalFocusEffect extends BaseDamageEffect {

    @Override
    public int GetPriority() {
        return Priority.Second.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {

        if (effect.element.equals(stat.getElement())) {
            effect.percentIncrease += data.getAverageValue();

        } else {
            effect.percentIncrease -= data.getAverageValue();
        }

        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.element != null && effect.element != Elements.Physical;
    }

}
