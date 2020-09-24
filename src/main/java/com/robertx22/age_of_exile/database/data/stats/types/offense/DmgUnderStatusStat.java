package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.DmgUnderStatusEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

public class DmgUnderStatusStat extends Stat implements IStatEffects {

    public static DmgUnderStatusStat HUNGER = new DmgUnderStatusStat(StatusEffects.HUNGER, "hunger");
    public static DmgUnderStatusStat POISON = new DmgUnderStatusStat(StatusEffects.POISON, "poison");
    public static DmgUnderStatusStat WITHER = new DmgUnderStatusStat(StatusEffects.WITHER, "wither");

    StatusEffect status;
    String id;
    DmgUnderStatusEffect effect;

    private DmgUnderStatusStat(StatusEffect status, String id) {
        this.status = status;
        this.id = id;
        this.effect = new DmgUnderStatusEffect(status);
        this.is_percent = true;
        this.add$To$toTooltip = false;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your damage if you are under this status effect.";
    }

    @Override
    public String locNameForLangFile() {
        return "Damage under " + CLOC.translate(status.getName());
    }

    @Override
    public String GUID() {
        return "dmg_under_" + id;
    }

    @Override
    public IStatEffect getEffect() {
        return effect;
    }
}
