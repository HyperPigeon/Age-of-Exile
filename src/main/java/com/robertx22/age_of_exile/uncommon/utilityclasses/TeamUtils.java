package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TeamUtils {

    public static void forEachMember(World world, BlockPos pos, Consumer<PlayerEntity> action) {

        PlayerEntity player = PlayerUtils.nearestPlayer((ServerWorld) world, pos);

        if (player != null) {
            TeamUtils.getOnlineMembers(player)
                .forEach(x -> action.accept(x));
        }

    }

    public static List<PlayerEntity> getOnlineTeamMembersInRange(PlayerEntity player, double range) {
        return getOnlineMembers(player).stream()
            .filter(x -> player.distanceTo(x) < range)
            .collect(Collectors.toList());

    }

    public static List<PlayerEntity> getOnlineTeamMembersInRange(PlayerEntity player) {

        return getOnlineTeamMembersInRange(player, ServerContainer.get().PARTY_RADIUS.get());

    }

    public static List<PlayerEntity> getOnlineMembers(PlayerEntity player) {
        List<PlayerEntity> players = new ArrayList<>();

        try {
            player.getServer()
                .getPlayerList()
                .getPlayers()
                .forEach(x -> {
                    if (areOnSameTeam(player, x)) {
                        players.add(x);
                    }

                });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (players.isEmpty()) {
            players.add(player);
        }

        return players;
    }

    public static boolean areOnSameTeam(PlayerEntity p1, PlayerEntity p2) {
        if (ServerContainer.get().ALL_PLAYERS_ARE_TEAMED_PVE_MODE.get()) {
            return true;
        }

        if (Load.playerRPGData(p1).team
            .isOnSameTeam(p2)) {
            return true;
        }

        return false;

    }

}
