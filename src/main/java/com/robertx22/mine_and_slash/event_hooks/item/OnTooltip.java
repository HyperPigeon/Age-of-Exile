package com.robertx22.mine_and_slash.event_hooks.item;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class OnTooltip implements ItemTooltipCallback {

    @Override
    public void getTooltip(ItemStack stack, net.minecraft.client.item.TooltipContext tooltipContext, List<Text> tooltip) {

        /*
        if (stack
            .getItem() instanceof ICurrencyItemEffect) {
            ICurrencyItemEffect currency = (ICurrencyItemEffect) stack
                .getItem();
            currency.addToTooltip(tooltip);
            return;
        }

        PlayerEntity player = MinecraftClient.getInstance().player;

        try {
            if (Screen.hasControlDown()) {
                GearItemData gear = Gear.Load(stack);
                if (gear != null) {
                    return;
                }
            }

            if (player == null || player.world == null) {
                return;
            }

            UnitData unitdata = Load.Unit(player);

            if (unitdata == null) {
                return;
            }

            Unit unit = unitdata.getUnit();

            if (unit == null) {
                return;
            }

            TooltipContext ctx = new TooltipContext(stack, tooltip, unitdata);

            if (!stack.hasTag()) {
                return;
            }

            ICommonDataItem data = ICommonDataItem.load(stack);

            if (data != null) {
                data.BuildTooltip(ctx);
            }

            MutableText broken = TooltipUtils.itemBrokenText(stack, data);
            if (broken != null) {
                tooltip.add(broken);
            }

            if (data instanceof GearItemData) {
                List<String> strings = tooltip
                    .stream()
                    .map(x -> CLOC.translate(x))
                    .collect(Collectors.toList());

                TextRenderer font = MinecraftClient.getInstance().textRenderer;

                int max = font.getWidth(strings.stream()
                    .max(Comparator.comparingInt(x -> font.getWidth(x)))
                    .get());

                tooltip.clear();

                strings.forEach(x -> {

                    String str = x;

                    while (font.getWidth(str) <= max) {
                        str = " " + str + " ";
                    }

                    tooltip
                        .add(new SText(str));

                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }

}
