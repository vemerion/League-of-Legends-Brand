package mod.vemerion.leagueoflegendsbrand;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.capability.AblazedProvider;
import mod.vemerion.leagueoflegendsbrand.capability.Brand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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

	public static final ResourceLocation ABLAZED_CAP = new ResourceLocation(LeagueOfLegendsBrand.MODID, "ablazed");

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof LivingEntity) {
			event.addCapability(ABLAZED_CAP, new AblazedProvider());
		}
	}

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
		LivingEntity entity = event.getEntityLiving();
		if (!entity.world.isRemote) {
			Ablazed ablazed = entity.getCapability(LeagueOfLegendsBrand.ABLAZED_CAP).orElse(new Ablazed());
			ablazed.tick(entity);
		}
	}
}
