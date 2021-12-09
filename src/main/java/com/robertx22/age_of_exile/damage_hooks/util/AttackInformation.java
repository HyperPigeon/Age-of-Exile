package com.robertx22.age_of_exile.damage_hooks.util;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class AttackInformation {

    public ExileEvents.OnDamageEntity event;
    Mitigation mitigation;
    boolean canceled = false;
    LivingEntity targetEntity;
    LivingEntity attackerEntity;
    DamageSource damageSource;
    float amount;

    public ItemStack weapon;
    public GearItemData weaponData;

    public AttackInformation(ExileEvents.OnDamageEntity event, Mitigation miti, LivingEntity target, DamageSource source, float amount) {
        this.targetEntity = target;
        this.damageSource = source;
        this.amount = amount;
        this.mitigation = miti;
        this.event = event;
        this.weapon = WeaponFinderUtil.getWeapon(source);
        this.weaponData = Gear.Load(weapon);

        Preconditions.checkArgument(source.getEntity() instanceof LivingEntity);
        this.attackerEntity = (LivingEntity) source.getEntity();

    }

    public DamageSource getSource() {
        return damageSource;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float f) {
        amount = f;
    }

    public LivingEntity getTargetEntity() {
        return targetEntity;
    }

    public LivingEntity getAttackerEntity() {
        return attackerEntity;
    }

    public EntityData getAttackerEntityData() {
        return Load.Unit(attackerEntity);
    }

    public EntityData getTargetEntityData() {
        return Load.Unit(targetEntity);
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public enum Mitigation {
        PRE, POST;
    }
}
