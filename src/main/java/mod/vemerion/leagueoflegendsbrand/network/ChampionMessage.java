package mod.vemerion.leagueoflegendsbrand.network;

import java.util.UUID;
import java.util.function.Supplier;

import mod.vemerion.leagueoflegendsbrand.champion.Champion;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.fml.network.NetworkEvent;

public class ChampionMessage {
	private int champId;
	private UUID id;

	public ChampionMessage(int champId, UUID id) {
		this.champId = champId;
		this.id = id;
	}

	public void encode(PacketBuffer buffer) {
		buffer.writeInt(champId);
		buffer.writeUniqueId(id);
	}

	public static ChampionMessage decode(PacketBuffer buffer) {
		return new ChampionMessage(buffer.readInt(), buffer.readUniqueId());
	}

	public void handle(Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Handler.handle(champId, id)));
	}

	private static class Handler {
		private static SafeRunnable handle(int champId, UUID id) {
			return new SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					World world = Minecraft.getInstance().world;
					if (world != null) {
						PlayerEntity player = world.getPlayerByUuid(id);
						if (player != null)
							Champions.get(player).ifPresent(b -> b.setChampion(Champion.get(champId)));
					}
				}
			};
		}

	}
}
