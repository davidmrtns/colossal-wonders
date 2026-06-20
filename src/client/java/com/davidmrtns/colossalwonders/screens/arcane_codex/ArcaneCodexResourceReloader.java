package com.davidmrtns.colossalwonders.screens.arcane_codex;

import com.davidmrtns.colossalwonders.ColossalWonders;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ArcaneCodexResourceReloader implements IdentifiableResourceReloadListener {
    public static final Identifier ID = new Identifier(ColossalWonders.MOD_ID, "arcane_codex_reloader");

    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return CompletableFuture
                .supplyAsync(() -> {
                    ArcaneCodexDataLoader.loadArcaneCodexEntries(manager);
                    return (Void) null;
                }, prepareExecutor)
                .thenCompose(synchronizer::whenPrepared);
    }

    @Override
    public Identifier getFabricId() {
        return ID;
    }
}
