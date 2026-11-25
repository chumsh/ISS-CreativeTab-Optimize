package com.chunshui.phit.iss_creativetab_fix;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class SpellDataGen {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path FILE = FMLPaths.CONFIGDIR.get()
            .resolve(PhiTsISSCreativeTabOptimize.MODID)
            .resolve("all_spells.json");

    static Type type = new TypeToken<Map<String, List<String>>>() {}.getType();

    public static void generate (Map<String, List<String>> result) {
        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, GSON.toJson(result));
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to encode spell data!",
                    e
            );
        }
    }
    public static NonNullList<SchoolType> decodeForSchool() {
        try {
            if (!Files.exists(FILE)) return null;
            String json = Files.readString(FILE);
            Map<String, List<String>> map = GSON.fromJson(json, type);
            if (map == null) return null;
            NonNullList<SchoolType> schools = NonNullList.create();
            map.forEach((id, list) -> schools.add(SchoolRegistry.getSchool(ResourceLocation.parse(id))));
            return schools;
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to decodeForSpell spell data!",
                    e
            );
        }
    }


    public static NonNullList<AbstractSpell> decodeForSpell(String school){
        try {
            if (!Files.exists(FILE)) return null;
            String json = Files.readString(FILE);
            Map<String, List<String>> map = GSON.fromJson(json, type);
            if (map == null) return null;
            NonNullList<AbstractSpell> spells = NonNullList.create();
            List<String> list = map.get(school);
            if (list == null) return null;
            list.forEach(id -> spells.add(SpellRegistry.getSpell(id)));
            return spells;
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to decodeForSpell spell data!",
                    e
            );
        }

    }
}
