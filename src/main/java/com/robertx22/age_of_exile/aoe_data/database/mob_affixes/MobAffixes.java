package com.robertx22.age_of_exile.aoe_data.database.mob_affixes;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysConvertToEle;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.LifeOnHit;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

import static com.robertx22.age_of_exile.uncommon.enumclasses.Elements.*;

public class MobAffixes implements ISlashRegistryInit {

    public static MobAffix EMPTY = new MobAffix("empty", "empty", Formatting.AQUA);

    public static MobAffix COLD = new MobAffix("cold", "Cold", Water.format).setMods(new StatModifier(75, 75, new PhysConvertToEle(Water)), new StatModifier(1, 1, new AttackDamage(Water)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));
    public static MobAffix FLAMING = new MobAffix("flaming", "Flaming", Fire.format).setMods(new StatModifier(75, 75, new PhysConvertToEle(Fire)), new StatModifier(1, 1, new AttackDamage(Fire)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));
    public static MobAffix LIGHTNING = new MobAffix("lightning", "Thunder", Thunder.format).setMods(new StatModifier(75, 75, new PhysConvertToEle(Thunder)), new StatModifier(1, 1, new AttackDamage(Thunder)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));
    public static MobAffix VENOMOUS = new MobAffix("venom", "Poison", Nature.format).setMods(new StatModifier(75, 75, new PhysConvertToEle(Nature)), new StatModifier(1, 1, new AttackDamage(Nature)), new StatModifier(10, 10, ExtraMobDropsStat.getInstance()));

    public static MobAffix WINTER = new MobAffix("winter", "Lord of Winter", Water.format)
        .setMods(
            new StatModifier(15, 15, Health.getInstance()),
            new StatModifier(5, 5, ChanceToApplyEffect.CHILL),
            new StatModifier(75, 75, new PhysConvertToEle(Water)),
            new StatModifier(1, 1, new AttackDamage(Water)),
            new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
        .icon(Water.format + Water.icon)
        .setWeight(500);

    public static MobAffix INFERNO = new MobAffix("fire_lord", "Lord of Fire", Fire.format)
        .setMods(
            new StatModifier(15, 15, Health.getInstance()),
            new StatModifier(5, 5, ChanceToApplyEffect.BURN),
            new StatModifier(75, 75, new PhysConvertToEle(Fire)),
            new StatModifier(1, 1, new AttackDamage(Fire)),
            new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
        .icon(Fire.format + Fire.icon)
        .setWeight(500);

    public static MobAffix THUNDER = new MobAffix("thunder_lord", "Lord of Thunder", Thunder.format)
        .setMods(
            new StatModifier(15, 15, Health.getInstance()),
            new StatModifier(5, 5, ChanceToApplyEffect.STATIC),
            new StatModifier(75, 75, new PhysConvertToEle(Thunder)),
            new StatModifier(1, 1, new AttackDamage(Thunder)),
            new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
        .icon(Thunder.format + Thunder.icon)
        .setWeight(500);

    public static MobAffix TOXIC = new MobAffix("nature_lord", "Lord of Toxins", Nature.format)
        .setMods(
            new StatModifier(15, 15, Health.getInstance()),
            new StatModifier(5, 5, ChanceToApplyEffect.POISON),
            new StatModifier(75, 75, new PhysConvertToEle(Nature)),
            new StatModifier(1, 1, new AttackDamage(Nature)),
            new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
        .icon(Nature.format + Nature.icon)
        .setWeight(500);

    public static MobAffix RUIN = new MobAffix("phys_lord", "Lord of Ruin", Formatting.GRAY)
        .setMods(
            new StatModifier(15, 15, Health.getInstance()),
            new StatModifier(2, 2, new AttackDamage(Physical)),
            new StatModifier(20, 20, ExtraMobDropsStat.getInstance()))
        .setWeight(500);

    public static MobAffix VAMPIRE = new MobAffix("vampire", "Vampire Lord", Formatting.RED)
        .setMods(new StatModifier(25, 25, Health.getInstance()),
            new StatModifier(3, 3, LifeOnHit.getInstance()),
            new StatModifier(15, 15, ExtraMobDropsStat.getInstance()))
        .setWeight(500);

    @Override
    public void registerAll() {

        List<MobAffix> all = new ArrayList<>();

        all.add(COLD);
        all.add(FLAMING);
        all.add(LIGHTNING);
        all.add(VENOMOUS);

        all.add(WINTER);
        all.add(INFERNO);
        all.add(THUNDER);
        all.add(TOXIC);

        all.add(RUIN);
        all.add(VAMPIRE);

        all.forEach(x -> x.addToSerializables());

    }
}
