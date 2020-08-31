package mod.vemerion.leagueoflegendsbrand.capability;

import java.util.UUID;
import java.util.function.Supplier;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class BrandMessage {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(LeagueOfLegendsBrand.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);

	private boolean isBrand;
	private UUID id;

	public BrandMessage(boolean isBrand, UUID id) {
		this.isBrand = isBrand;
		this.id = id;
	}

	public static void encode(final BrandMessage msg, final PacketBuffer buffer) {
		buffer.writeBoolean(msg.isBrand);
		buffer.writeUniqueId(msg.id);
	}

	public static BrandMessage decode(final PacketBuffer buffer) {
		return new BrandMessage(buffer.readBoolean(), buffer.readUniqueId());
	}

	public static void handle(final BrandMessage msg, final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(msg.id);
			if (player != null) {
				Brand brand = player.getCapability(LeagueOfLegendsBrand.BRAND_CAP)
						.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!"));
				brand.setBrand(msg.isBrand);
			} else {
				System.out.println("Warning: player is null when sending Brand capability");
			}
		}));
	}
}
