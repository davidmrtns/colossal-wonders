package com.davidmrtns.colossalwonders.screens.arcane_codex;

import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.include.com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class ArcaneCodexDataLoader {
    private static final Gson GSON = new Gson();
    public static final Map<Identifier, ArcaneCodexEntryData> ENTRIES = new HashMap<>();

    public static void loadArcaneCodexEntries(ResourceManager manager) {
        Identifier path = new Identifier("colossal_wonders", "arcane_codex_entries");

        for (Identifier id : manager.findResources("arcane_codex_entries", id -> id.getPath().endsWith(".json")).keySet()) {
            try {
                Resource resource = manager.getResource(id).orElseThrow();
                try (Reader reader = new InputStreamReader(resource.getInputStream())) {
                    ArcaneCodexEntryData data = GSON.fromJson(reader, ArcaneCodexEntryData.class);
                    ENTRIES.put(new Identifier(data.id), data);
                }
            } catch (Exception e) {
                System.err.println("Error while trying to load arcane codex entry: " + id + " -> " + e);
            }
        }
    }
}
