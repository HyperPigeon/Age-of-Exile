package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TickUtils;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SpellConfiguration {

    public boolean swing_arm = false;
    public CastingWeapon castingWeapon = CastingWeapon.ANY_WEAPON;
    public int mana_cost;
    public int times_to_cast = 1;
    private int cast_time_ticks = 0;
    public int cooldown_ticks = 20;
    public AttackPlayStyle style = AttackPlayStyle.MAGIC;
    public PassiveConfig passive_config = new PassiveConfig();
    public List<SkillGemTag> tags = new ArrayList<>();

    public int getCastTimeTicks() {
        return cast_time_ticks;
    }

    public static class PassiveConfig {
        public boolean is_passive = false;
        public float cast_when_hp_bellow = 0.3F;

        public boolean canCastNow(LivingEntity en) {
            return HealthUtils.getCombinedHealthMulti(en) < cast_when_hp_bellow;
        }
    }

    public boolean isProjectile() {
        return tags.contains(SkillGemTag.PROJECTILE);
    }

    public SpellConfiguration setSwingArm() {
        this.swing_arm = true;
        return this;
    }

    public static class Builder {

        public static SpellConfiguration instant(int mana, int cd) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = 0;
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration nonInstant(int mana, int cd, int casttime) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = casttime;
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration passive() {
            SpellConfiguration c = new SpellConfiguration();
            c.cooldown_ticks = TickUtils.MINUTE * 10;
            c.passive_config.is_passive = true;
            return c;
        }

        public static SpellConfiguration multiCast(int mana, int cd, int casttime, int times) {
            SpellConfiguration c = new SpellConfiguration();
            c.times_to_cast = times;
            c.cast_time_ticks = casttime;
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            return c;
        }

    }
}
