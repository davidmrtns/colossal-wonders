package com.davidmrtns.colossalwonders.networking;

import com.davidmrtns.colossalwonders.ColossalWonders;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record OpenGrimoireScreenS2CPayload(int open) implements CustomPayload {
    public static final Identifier OPEN_GRIMOIRE_SCREEN_PAYLOAD_ID = new Identifier(ColossalWonders.MOD_ID, "open_grimoire_screen");
    public static final Id<OpenGrimoireScreenS2CPayload> ID = new Id<>(OpenGrimoireScreenS2CPayload.OPEN_GRIMOIRE_SCREEN_PAYLOAD_ID);

    public static final PacketCodec<RegistryByteBuf, OpenGrimoireScreenS2CPayload> CODEC =
            PacketCodec.ofStatic(OpenGrimoireScreenS2CPayload::write, OpenGrimoireScreenS2CPayload::read);

    private static void write(RegistryByteBuf buf, OpenGrimoireScreenS2CPayload payload) {
        buf.writeVarInt(payload.open());
    }

    private static OpenGrimoireScreenS2CPayload read(RegistryByteBuf buf) {
        return new OpenGrimoireScreenS2CPayload(buf.readVarInt());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
