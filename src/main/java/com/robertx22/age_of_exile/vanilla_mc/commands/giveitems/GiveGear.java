package com.robertx22.age_of_exile.vanilla_mc.commands.giveitems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.DatabaseSuggestions;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.GearRaritySuggestions;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GiveGear {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {

        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("give").requires(e -> e.hasPermissionLevel(2))
                    .then(literal("gear")
                        .then(argument("target", EntityArgumentType.player())
                            .then(argument("type", StringArgumentType.word())
                                .suggests(new DatabaseSuggestions(ExileRegistryTypes.GEAR_TYPE))
                                .then(argument("level",
                                    IntegerArgumentType.integer()
                                )
                                    .then(argument(
                                        "rarity",
                                        StringArgumentType.string()
                                    ).suggests(new GearRaritySuggestions())
                                        .then(argument(
                                            "amount",
                                            IntegerArgumentType
                                                .integer(
                                                    1,
                                                    5000
                                                )
                                        )
                                            .executes(
                                                e -> execute(
                                                    e.getSource(),
                                                    EntityArgumentType
                                                        .getPlayer(
                                                            e,
                                                            "target"
                                                        ),
                                                    StringArgumentType
                                                        .getString(
                                                            e,
                                                            "type"
                                                        ),
                                                    IntegerArgumentType
                                                        .getInteger(
                                                            e,
                                                            "level"
                                                        ),
                                                    StringArgumentType
                                                        .getString(
                                                            e,
                                                            "rarity"
                                                        ),
                                                    IntegerArgumentType
                                                        .getInteger(
                                                            e,
                                                            "amount"
                                                        )

                                                ))))))))));
    }

    private static int execute(ServerCommandSource commandSource, PlayerEntity player, String type, int lvl,
                               String rarity, int amount) {

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayer();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }
        for (int i = 0; i < amount; i++) {

            GearBlueprint blueprint = new GearBlueprint(lvl);
            blueprint.unidentifiedPart.set(false);
            blueprint.level.set(lvl);

            if (Database.GearRarities()
                .isRegistered(rarity)) {
                blueprint.rarity.set(Database.GearRarities()
                    .get(rarity));
            }
            if (!type.equals("random")) {
                blueprint.gearItemSlot.set(type);
            }

            player.giveItemStack(blueprint.createStack());

        }

        return 0;
    }
}
