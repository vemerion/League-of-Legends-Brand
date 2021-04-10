package mod.vemerion.leagueoflegendsbrand.eventsubscriber;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
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

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {

	@SubscribeEvent
	public static void synchChampion(PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
		Champions.sync(player, (ServerPlayerEntity) player);
	}

	@SubscribeEvent
	public static void synchChampion(PlayerChangedDimensionEvent event) {
		PlayerEntity player = event.getPlayer();
		Champions.sync(player, (ServerPlayerEntity) player);
	}

	@SubscribeEvent
	public static void synchChampion(PlayerEvent.PlayerRespawnEvent event) {
		PlayerEntity player = event.getPlayer();
		Champions.sync(player, (ServerPlayerEntity) player);
	}

	@SubscribeEvent
	public static void synchChampion(PlayerEvent.StartTracking event) {
		Champions.sync(event.getTarget(), (ServerPlayerEntity) event.getPlayer());
	}

	@SubscribeEvent
	public static void championsTick(PlayerTickEvent event) {
		if (event.phase == Phase.START)
			Champions.get(event.player).ifPresent(c -> c.tick());
	}

	@SubscribeEvent
	public static void updateAblazed(LivingUpdateEvent event) {
		Ablazed.get(event.getEntityLiving()).ifPresent(a -> a.tick());
	}
}
