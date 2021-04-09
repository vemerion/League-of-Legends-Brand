package mod.vemerion.leagueoflegendsbrand;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.capability.Brand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = LeagueOfLegendsBrand.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {

	@SubscribeEvent
	public static void synchBrand(PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
		Brand.sync(player, (ServerPlayerEntity) player);
	}

	@SubscribeEvent
	public static void synchBrand(PlayerChangedDimensionEvent event) {
		PlayerEntity player = event.getPlayer();
		Brand.sync(player, (ServerPlayerEntity) player);
	}

	@SubscribeEvent
	public static void synchBrand(PlayerEvent.PlayerRespawnEvent event) {
		PlayerEntity player = event.getPlayer();
		Brand.sync(player, (ServerPlayerEntity) player);
	}

	@SubscribeEvent
	public static void synchBrand(PlayerEvent.StartTracking event) {
		Brand.sync(event.getTarget(), (ServerPlayerEntity) event.getPlayer());
	}

	@SubscribeEvent
	public static void brandTick(PlayerTickEvent event) {
		if (event.phase == Phase.START)
			Brand.get(event.player).ifPresent(b -> b.tick());
	}

	@SubscribeEvent
	public static void updateAblazed(LivingUpdateEvent event) {
		Ablazed.get(event.getEntityLiving()).ifPresent(a -> a.tick());
	}
}
