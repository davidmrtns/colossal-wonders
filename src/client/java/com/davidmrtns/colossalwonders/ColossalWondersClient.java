package com.davidmrtns.colossalwonders;

import com.davidmrtns.colossalwonders.entities.ModEntities;
import com.davidmrtns.colossalwonders.keybindings.ModKeyBindings;
import com.davidmrtns.colossalwonders.networking.OpenArcaneCodexScreenS2CPayload;
import com.davidmrtns.colossalwonders.renderer.ShieldDomeRenderer;
import com.davidmrtns.colossalwonders.screens.RadialMenuOverlay;
import com.davidmrtns.colossalwonders.screens.WandHudOverlay;
import com.davidmrtns.colossalwonders.screens.arcane_codex.ArcaneCodexResourceReloader;
import com.davidmrtns.colossalwonders.screens.arcane_codex.ArcaneCodexScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class ColossalWondersClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModKeyBindings.registerModKeyBindings();

		HudRenderCallback.EVENT.register(WandHudOverlay::render);
		RadialMenuOverlay.init();

		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new ArcaneCodexResourceReloader());
		EntityRendererRegistry.register(ModEntities.SHIELD_DOME, ShieldDomeRenderer::new);

		ClientPlayNetworking.registerGlobalReceiver(OpenArcaneCodexScreenS2CPayload.ID, (payload, context) -> {
			context.client().setScreen(new ArcaneCodexScreen());
		});
	}
}
