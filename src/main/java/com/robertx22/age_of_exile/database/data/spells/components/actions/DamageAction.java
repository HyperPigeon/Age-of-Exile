package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.tooltips.ICTextTooltip;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.ELEMENT;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class DamageAction extends SpellAction implements ICTextTooltip {

    public DamageAction() {
        super(Arrays.asList(ELEMENT, VALUE_CALCULATION));
    }

    @Override
    public MutableText getText(TooltipInfo info, MapHolder data) {
        MutableText text = new LiteralText("");

        ValueCalculationData calc = data.get(VALUE_CALCULATION);
        Elements ele = data.getElement();

        text.append("Deal ")
            .append(calc.getShortTooltip(info.unitdata))
            .append(" ")
            .append(ele.dmgName)
            .append(" Damage");

        return text;

    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClient) {
            Elements ele = data.getElement();
            ValueCalculationData calc = data.get(VALUE_CALCULATION);

            int value = calc.getCalculatedValue(ctx.caster);

            value *= ctx.calculatedSpellData.config.getMulti(SpellModEnum.DAMAGE);

            for (LivingEntity t : targets) {
                SpellDamageEffect dmg = new SpellDamageEffect(ctx.caster, t, value, ctx.calculatedSpellData.getSpell());
                if (data.has(MapField.DMG_EFFECT_TYPE)) {
                    dmg.effectType = data.getDmgEffectType();
                }
                dmg.element = ele;
                dmg.Activate();
            }
        }

    }

    public MapHolder create(ValueCalculationData calc, Elements ele) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(VALUE_CALCULATION, calc);
        dmg.put(ELEMENT, ele.name());
        return dmg;
    }

    @Override
    public String GUID() {
        return "damage";
    }

}
