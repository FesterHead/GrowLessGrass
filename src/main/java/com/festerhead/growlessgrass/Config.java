package com.festerhead.growlessgrass;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = GrowLessGrass.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
        private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        private static final ForgeConfigSpec.DoubleValue BONEMEAL_SHORT_GRASS_CHANCE = BUILDER
                        .comment("Chance for short grass generation (0.0 = none, 1.0 = always)")
                        .defineInRange("bonemeal_short_grass_chance", 0.25, 0.0, 1.0);

        private static final ForgeConfigSpec.DoubleValue BONEMEAL_TALL_GRASS_CHANCE = BUILDER
                        .comment("Chance for tall grass generation (0.0 = none, 1.0 = always)")
                        .defineInRange("bonemeal_tall_grass_chance", 0.0, 0.0, 1.0);

        static final ForgeConfigSpec SPEC = BUILDER.build();

        public static double bonemealShortGrassChance;
        public static double bonemealTallGrassChance;

        @SubscribeEvent
        static void onLoad(final ModConfigEvent event) {
                bonemealShortGrassChance = BONEMEAL_SHORT_GRASS_CHANCE.get().doubleValue();
                bonemealTallGrassChance = BONEMEAL_TALL_GRASS_CHANCE.get().doubleValue();
        }
}
