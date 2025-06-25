package com.davidmrtns.colossalwonders.networking;

import com.davidmrtns.colossalwonders.ColossalWonders;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DropWandCoreC2SPayload(int drop) implements CustomPayload {
    public static final Identifier DROP_WAND_CORE_PAYLOAD_ID = new Identifier(ColossalWonders.MOD_ID, "drop_wand_core");
    public static final Id<DropWandCoreC2SPayload> ID = new Id<>(DropWandCoreC2SPayload.DROP_WAND_CORE_PAYLOAD_ID);

    public static final PacketCodec<RegistryByteBuf, DropWandCoreC2SPayload> CODEC =
            PacketCodec.ofStatic(DropWandCoreC2SPayload::write, DropWandCoreC2SPayload::read);

    private static void write(RegistryByteBuf buf, DropWandCoreC2SPayload payload) {
        buf.writeVarInt(payload.drop());
    }

    private static DropWandCoreC2SPayload read(RegistryByteBuf buf) {
        return new DropWandCoreC2SPayload(buf.readVarInt());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
