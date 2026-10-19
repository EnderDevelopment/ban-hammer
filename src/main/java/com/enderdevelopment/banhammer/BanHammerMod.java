package com.enderdevelopment.banhammer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BanHammerMod implements ModInitializer {
    public static final String MOD_ID = "banhammer";
    public static final Item BAN_HAMMER = new BanHammerItem(new FabricItemSettings().maxCount(1).fireproof());

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "ban_hammer"), BAN_HAMMER);
    }
}