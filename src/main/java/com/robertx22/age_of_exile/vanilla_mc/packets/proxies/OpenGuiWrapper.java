package com.robertx22.age_of_exile.vanilla_mc.packets.proxies;

import com.robertx22.age_of_exile.gui.screens.character_screen.CharacterScreen;

public class OpenGuiWrapper {

    public static void openMainHub() {

        net.minecraft.client.Minecraft.getInstance()
            .setScreen(new CharacterScreen());

    }

}
