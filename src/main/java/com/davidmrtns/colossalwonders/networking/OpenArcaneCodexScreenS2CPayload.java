package com.davidmrtns.colossalwonders.networking;

import com.davidmrtns.colossalwonders.ColossalWonders;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OpenArcaneCodexScreenS2CPayload(int open) implements CustomPayload {
    public static final Identifier OPEN_ARCANE_CODEX_SCREEN_PAYLOAD_ID = new Identifier(ColossalWonders.MOD_ID, "open_arcane_codex_screen");
    public static final Id<OpenArcaneCodexScreenS2CPayload> ID = new Id<>(OpenArcaneCodexScreenS2CPayload.OPEN_ARCANE_CODEX_SCREEN_PAYLOAD_ID);

    public static final PacketCodec<RegistryByteBuf, OpenArcaneCodexScreenS2CPayload> CODEC =
            PacketCodec.ofStatic(OpenArcaneCodexScreenS2CPayload::write, OpenArcaneCodexScreenS2CPayload::read);

    private static void write(RegistryByteBuf buf, OpenArcaneCodexScreenS2CPayload payload) {
        buf.writeVarInt(payload.open());
    }

    private static OpenArcaneCodexScreenS2CPayload read(RegistryByteBuf buf) {
        return new OpenArcaneCodexScreenS2CPayload(buf.readVarInt());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
