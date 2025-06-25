package com.davidmrtns.colossalwonders.screens.grimoire;

import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.include.com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class GrimoireDataLoader {
    private static final Gson GSON = new Gson();
    public static final Map<Identifier, GrimoireEntryData> ENTRIES = new HashMap<>();

    public static void loadGrimoireEntries(ResourceManager manager) {
        Identifier path = new Identifier("colossal_wonders", "grimoire_entries");

        for (Identifier id : manager.findResources("grimoire_entries", id -> id.getPath().endsWith(".json")).keySet()) {
            try {
                Resource resource = manager.getResource(id).orElseThrow();
                try (Reader reader = new InputStreamReader(resource.getInputStream())) {
                    GrimoireEntryData data = GSON.fromJson(reader, GrimoireEntryData.class);
                    ENTRIES.put(new Identifier(data.id), data);
                }
            } catch (Exception e) {
                System.err.println("Error while trying to load grimoire entry: " + id + " -> " + e);
            }
        }
    }
}
