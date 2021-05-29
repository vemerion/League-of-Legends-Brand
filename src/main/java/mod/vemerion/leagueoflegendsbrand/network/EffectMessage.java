package mod.vemerion.leagueoflegendsbrand.network;

import java.util.UUID;
import java.util.function.Supplier;

import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.fml.network.NetworkEvent;

public class EffectMessage {
	private Effect effect;
	private boolean active;
	private UUID id;

	public EffectMessage(Effect effect, boolean active, UUID id) {
		this.effect = effect;
		this.active = active;
		this.id = id;
	}

	public void encode(PacketBuffer buffer) {
		buffer.writeRegistryId(effect);
		buffer.writeBoolean(active);
		buffer.writeUniqueId(id);
	}

	public static EffectMessage decode(PacketBuffer buffer) {
		return new EffectMessage(buffer.readRegistryId(), buffer.readBoolean(), buffer.readUniqueId());
	}

	public void handle(Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Handler.handle(effect, active, id)));
	}

	private static class Handler {
		private static SafeRunnable handle(Effect effect, boolean active, UUID id) {
			return new SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					World world = Minecraft.getInstance().world;
					if (world != null) {
						PlayerEntity player = world.getPlayerByUuid(id);
						if (player != null)
							Champions.get(player).ifPresent(c -> c.setEffect(effect, active));
					}
				}
			};
		}

	}
}
