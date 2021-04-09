package mod.vemerion.leagueoflegendsbrand.network;

import java.util.function.Supplier;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.fml.network.NetworkEvent;

public class BurningMessage {
	private int id;

	public BurningMessage(int id) {
		this.id = id;
	}

	public void encode(PacketBuffer buffer) {
		buffer.writeInt(id);
	}

	public static BurningMessage decode(PacketBuffer buffer) {
		return new BurningMessage(buffer.readInt());
	}

	public void handle(Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Handler.handle(id)));
	}

	private static class Handler {
		private static SafeRunnable handle(int id) {
			return new SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					World world = Minecraft.getInstance().world;
					if (world != null) {
						Entity entity = world.getEntityByID(id);
						if (entity != null)
							Ablazed.get(entity).ifPresent(a -> a.setBurning());
					}
				}
			};
		}

	}
}
