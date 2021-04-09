package mod.vemerion.leagueoflegendsbrand.network;

import java.util.UUID;
import java.util.function.Supplier;

import mod.vemerion.leagueoflegendsbrand.capability.Brand;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.fml.network.NetworkEvent;

public class BrandMessage {
	private boolean isBrand;
	private UUID id;

	public BrandMessage(boolean isBrand, UUID id) {
		this.isBrand = isBrand;
		this.id = id;
	}

	public void encode(PacketBuffer buffer) {
		buffer.writeBoolean(isBrand);
		buffer.writeUniqueId(id);
	}

	public static BrandMessage decode(PacketBuffer buffer) {
		return new BrandMessage(buffer.readBoolean(), buffer.readUniqueId());
	}

	public void handle(Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Handler.handle(isBrand, id)));
	}

	private static class Handler {
		private static SafeRunnable handle(boolean isBrand, UUID id) {
			return new SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					World world = Minecraft.getInstance().world;
					if (world != null) {
						PlayerEntity player = world.getPlayerByUuid(id);
						if (player != null)
							Brand.get(player).ifPresent(b -> b.setBrand(isBrand));
					}
				}
			};
		}

	}
}