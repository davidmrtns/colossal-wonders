package com.davidmrtns.colossalwonders.networking;

import com.davidmrtns.colossalwonders.ColossalWonders;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SwapWandCoreC2SPayload(int slot) implements CustomPayload {
    public static final Identifier SWAP_WAND_CORE_PAYLOAD_ID = new Identifier(ColossalWonders.MOD_ID, "swap_wand_core");
    public static final CustomPayload.Id<SwapWandCoreC2SPayload> ID = new CustomPayload.Id<>(SwapWandCoreC2SPayload.SWAP_WAND_CORE_PAYLOAD_ID);

    public static final PacketCodec<RegistryByteBuf, SwapWandCoreC2SPayload> CODEC =
            PacketCodec.ofStatic(SwapWandCoreC2SPayload::write, SwapWandCoreC2SPayload::read);

    private static void write(RegistryByteBuf buf, SwapWandCoreC2SPayload payload) {
        buf.writeVarInt(payload.slot());
    }

    private static SwapWandCoreC2SPayload read(RegistryByteBuf buf) {
        return new SwapWandCoreC2SPayload(buf.readVarInt());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
