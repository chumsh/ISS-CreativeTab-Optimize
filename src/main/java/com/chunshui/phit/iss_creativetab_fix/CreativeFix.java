package com.chunshui.phit.iss_creativetab_fix;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.event.lifecycle.InterModProcessEvent;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = PhiTsISSCreativeTabOptimize.MODID)
public class CreativeFix {
    @SubscribeEvent
    public static void onServerStarting(InterModProcessEvent event) {
        event.enqueueWork(() -> {
            List<AbstractSpell> spells = SpellRegistry.getEnabledSpells();
            Map<String, List<String>> map = new HashMap<>();
            spells.forEach(spell -> {
                String path = spell.getDefaultConfig().schoolResource.toString();
                map.computeIfAbsent(path, key -> new ArrayList<>()).add(spell.getSpellId());
            });
            SpellDataGen.generate(map);
        });
    }

    @SubscribeEvent
    public static void onCreativeTabConstruct(FMLLoadCompleteEvent event) {
        NonNullList<SchoolType> list = SpellDataGen.decodeForSchool();
        if (list == null) return;
        list.forEach(schoolType -> {
            Component school = schoolType.getDisplayName();
            Vector3f color = schoolType.getTargetingColor();
            FancyTabSections.addSection(IronsSpellbooks.id("spellbook_scrolls"),
                    new SectionAnimatedWithBackground(PhiTsISSCreativeTabOptimize.id(schoolType.getId().getPath()), Resources.BACKGROUND, Resources.FOREGROUND, color)
                            .setFrames(50)
                            .setFrameTimeInMS(80)
                            .setTitle(school)
                            .setTextColor(Utils.packRGB(schoolType.getTargetingColor()))
                            .setRenderTitle(true)
                            .setCentered(true)
            );
            NonNullList<AbstractSpell> spells = SpellDataGen.decodeForSpell(schoolType.getId().toString());

            if (spells != null) {
                spells.forEach(spell -> {
                    DefaultConfig config = spell.getDefaultConfig();
                    for (int i = 1; i <= config.maxLevel; i++) {
                        var itemstack = new ItemStack(ItemRegistry.SCROLL.get());
                        ISpellContainer.createScrollContainer(spell, i, itemstack);
                        FancyTabSections.getSection(PhiTsISSCreativeTabOptimize.id(schoolType.getId().getPath())).add(itemstack);
                    }
                });
            }
        });
    }
}
