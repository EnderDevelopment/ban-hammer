package com.enderdevelopment.banhammer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BanHammerItem extends Item {
    public BanHammerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) user;
            ServerCommandSource source = serverPlayer.getCommandSource();

            if (serverPlayer.getTarget() instanceof ServerPlayerEntity) {
                ServerPlayerEntity target = (ServerPlayerEntity) serverPlayer.getTarget();
                String banCommand = "/ban " + target.getGameProfile().getName() + " Banned by BanHammer";
                world.getServer().getCommandManager().executeWithPrefix(source, banCommand);

                // Drop the target's inventory
                for (ItemStack stack : target.getInventory().main) {
                    if (!stack.isEmpty()) {
                        target.dropItem(stack, true);
                    }
                }

                serverPlayer.sendMessage(Text.literal("Player " + target.getGameProfile().getName() + " has been banned."));
            } else {
                serverPlayer.sendMessage(Text.literal("No player targeted."));
            }
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));
    }
}