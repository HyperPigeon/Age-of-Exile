package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase.ElementalDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

import java.util.List;

public class ElementalDamageBonus extends ElementalStat implements IStatEffects {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.add(newGeneratedInstance(Elements.Physical));
        return list;

    }

    public ElementalDamageBonus(Elements element) {
        super(element);
        this.scaling = StatScaling.NONE;
        this.statGroup = StatGroup.ELEMENTAL;
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new ElementalDamageBonus(element);
    }

    @Override
    public String GUID() {
        return "all_" + this.getElement().guidName + "_damage";
    }

    @Override
    public String locDescForLangFile() {
        return "Increases All dmg of that element, both spells and attacks";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + "all_ele_dmg";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public IStatEffect getEffect() {
        return ElementalDamageEffect.getInstance();
    }

    @Override
    public String locNameForLangFile() {
        return "All " + getElement()
            .dmgName + " Damage";
    }

}

