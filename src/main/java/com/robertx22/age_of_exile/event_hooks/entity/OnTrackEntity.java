package com.robertx22.age_of_exile.event_hooks.entity;

import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class OnTrackEntity {

    public static void onPlayerStartTracking(ServerPlayerEntity serverPlayerEntity, Entity entity) {

        try {

            if (entity.level.isClientSide) {
                return;
            }

            if (entity instanceof LivingEntity) {
                if (!Unit.shouldSendUpdatePackets((LivingEntity) entity)) {
                    return;
                }
                if (entity.is(serverPlayerEntity) == false) {
                    Packets.sendToClient(serverPlayerEntity,
                        Unit.getUpdatePacketFor((LivingEntity) entity, Load.Unit(entity))
                    );

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
