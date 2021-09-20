package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.age_of_exile.vanilla_mc.commands.TeamCommand;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

@Storable
public class QuestProgression {

    @Store
    public String uuid = "";

    @Store
    public int num = 0;

    @Store
    public int target = 0;

    @Store
    public boolean fini = false;

    public QuestProgression(String uuid, int target) {
        this.uuid = uuid;
        this.target = target;
    }

    public QuestProgression() {
    }

    public void increaseProgressBy(PlayerEntity player, int num, SingleDungeonData single) {

        if (!fini) {

            List<PlayerEntity> members = TeamUtils.getOnlineTeamMembersInRange(player, 1000);

            if (members
                .size() > 1) {
                for (PlayerEntity p : members) {
                    if (p.getStringUUID()
                        .equals(single.ownerUUID)) {
                        player = p;
                    }
                }
            }

            if (MMORPG.RUN_DEV_TOOLS) {
                num += 55;
            }

            this.num += num;

            if (!single.pop.donePop) {
                return; // don't check for completition if dungeon didn't finish generating
            }

            player.displayClientMessage(new StringTextComponent("Dungeon Progress: " + this.num + "/" + target), false);

            if (this.num >= target) {
                fini = true;

                for (PlayerEntity x : members) {
                    x.displayClientMessage(
                        new StringTextComponent("Quest completed!, You can now progress to the next dungeon.")
                        , false);
                    PlayerUtils.giveItem(new ItemStack(SlashItems.TELEPORT_BACK.get()), x);
                    TeamCommand.sendDpsCharts(x);
                }

                Load.playerRPGData(player).maps
                    .onDungeonCompletedAdvanceProgress(player);

            }
        }
    }

}
