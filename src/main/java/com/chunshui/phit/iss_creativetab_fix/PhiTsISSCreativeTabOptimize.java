package com.chunshui.phit.iss_creativetab_fix;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import org.slf4j.Logger;

@Mod(PhiTsISSCreativeTabOptimize.MODID)
public class PhiTsISSCreativeTabOptimize {
    public static final String MODID = "iss_creativetab_fix";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String id) {
       return ResourceLocation.fromNamespaceAndPath(PhiTsISSCreativeTabOptimize.MODID, id);
    }


    public PhiTsISSCreativeTabOptimize(IEventBus modEventBus, ModContainer modContainer) {




    }

}
