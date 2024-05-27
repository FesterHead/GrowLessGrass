package com.festerhead.growlessgrass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GrowLessGrass.MODID)
public class GrowLessGrass {
    public static final String MODID = "growlessgrass";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Random random = new Random();

    public GrowLessGrass() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Bonemeal short grass chance = {}", Config.bonemealShortGrassChance);
        LOGGER.info("Bonemeal tall grass chance = {}", Config.bonemealTallGrassChance);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }

    @EventBusSubscriber(modid = MODID)
    public class EventHandler {
        /**
         * onBonemealEvent. Uses a configuration chance to grow short and tall grass.
         * <p>
         * Mostly copied from
         * https://github.com/Serilum/Random-Bone-Meal-Flowers/blob/1.20.1/Common/src/main/java/com/natamus/randombonemealflowers/events/FlowerEvent.java
         */
        @SubscribeEvent
        public static void onBonemealEvent(BonemealEvent event) {
            BlockPos blockPosition = event.getPos();
            Level world = event.getLevel();

            if (world.isClientSide) {
                return;
            }

            MinecraftServer server = world.getServer();
            if (server == null) {
                return;
            }

            int x = blockPosition.getX();
            int y = blockPosition.getY();
            int z = blockPosition.getZ();

            List<Block> oldblocks = new ArrayList<Block>();
            Iterator<BlockPos> it = BlockPos.betweenClosedStream(x - 6, y, z - 6, x + 6, y + 1, z + 6).iterator();
            while (it.hasNext()) {
                BlockPos bp = it.next();
                Block block = world.getBlockState(bp).getBlock();
                oldblocks.add(block);
            }

            server.tell(new TickTask(server.getTickCount(), () -> {
                Iterator<BlockPos> newit = BlockPos.betweenClosedStream(x - 6, y, z - 6, x + 6, y + 1, z + 6)
                        .iterator();
                while (newit.hasNext()) {
                    BlockPos bp = newit.next();
                    Block block = world.getBlockState(bp).getBlock();
                    if ((block == Blocks.GRASS) && (random.nextDouble() > Config.bonemealShortGrassChance)) {
                        world.setBlockAndUpdate(bp, Blocks.AIR.defaultBlockState());
                    }
                    if ((block == Blocks.TALL_GRASS) && (random.nextDouble() > Config.bonemealTallGrassChance)) {
                        world.setBlockAndUpdate(bp, Blocks.AIR.defaultBlockState());
                    }
                    oldblocks.remove(0);
                }
            }));
        }
    }
}
