package mod.vemerion.leagueoflegendsbrand;

import java.util.Iterator;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.capability.AblazedProvider;
import mod.vemerion.leagueoflegendsbrand.capability.Brand;
import mod.vemerion.leagueoflegendsbrand.capability.BrandProvider;
import mod.vemerion.leagueoflegendsbrand.entity.PyroclasmEntity;
import mod.vemerion.leagueoflegendsbrand.item.BrandSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = LeagueOfLegendsBrand.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {

	public static final ResourceLocation BRAND_CAP = new ResourceLocation(LeagueOfLegendsBrand.MODID, "brand");
	public static final ResourceLocation ABLAZED_CAP = new ResourceLocation(LeagueOfLegendsBrand.MODID, "ablazed");

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity) {
			event.addCapability(BRAND_CAP, new BrandProvider());
		}

		if (event.getObject() instanceof LivingEntity) {
			event.addCapability(ABLAZED_CAP, new AblazedProvider());
		}
	}

	@SubscribeEvent
	public static void synchBrand(PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
		Brand.syncBrand(player, (ServerPlayerEntity) player);
	}

	@SubscribeEvent
	public static void synchBrand(PlayerChangedDimensionEvent event) {
		PlayerEntity player = event.getPlayer();
		Brand.syncBrand(player, (ServerPlayerEntity) player);
	}

	@SubscribeEvent
	public static void synchBrand(PlayerEvent.PlayerRespawnEvent event) {
		PlayerEntity player = event.getPlayer();
		Brand.syncBrand(player, (ServerPlayerEntity) player);
	}
	
	@SubscribeEvent
	public static void synchBrand(PlayerEvent.StartTracking event) {
		Brand.syncBrand(event.getTarget(), (ServerPlayerEntity) event.getPlayer());
	}

	@SubscribeEvent
	public static void burnMap(PlayerTickEvent event) {
		if (event.side == LogicalSide.SERVER && event.phase == Phase.START) {
			PlayerEntity player = event.player;
			ServerWorld world = (ServerWorld) player.world;
			boolean isBrand = player.getCapability(LeagueOfLegendsBrand.BRAND_CAP).orElse(new Brand()).isBrand();
			if (isBrand) {
				Hand hand = null;
				if (player.getHeldItemMainhand().getItem().equals(Items.FILLED_MAP))
					hand = Hand.MAIN_HAND;
				else if (player.getHeldItemOffhand().getItem().equals(Items.FILLED_MAP))
					hand = Hand.OFF_HAND;
				if (hand != null) {
					player.setHeldItem(hand, new ItemStack(Items.AIR));
					for (int i = 0; i < 30; i++) {
						Vector3d position = player.getPositionVec()
								.add(player.getRNG().nextDouble() * 0.4 - 0.2, 1.5,
										player.getRNG().nextDouble() * 0.4 - 0.2)
								.add(Vector3d.fromPitchYaw(player.getPitchYaw()).scale(0.5));
						world.spawnParticle(ParticleTypes.FLAME, position.getX(), position.getY(), position.getZ(), 1,
								0, 0, 0, 0.1);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void updateAblazed(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (!entity.world.isRemote) {
			Ablazed ablazed = entity.getCapability(LeagueOfLegendsBrand.ABLAZED_CAP).orElse(new Ablazed());
			ablazed.tick(entity);
		}
	}

	@SubscribeEvent
	public static void preventPyroclasmFromHittingSameTarget(ProjectileImpactEvent.Arrow event) {
		if (event.getArrow() instanceof PyroclasmEntity) {
			PyroclasmEntity pyro = (PyroclasmEntity) event.getArrow();
			if (pyro.bouncer != null && event.getRayTraceResult() instanceof EntityRayTraceResult) {
				if (((EntityRayTraceResult) event.getRayTraceResult()).getEntity().getEntityId() == pyro.bouncer
						.getEntityId()) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void preventSpellDropDeath(LivingDropsEvent event) {
		if (event.getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntity();
			Iterator<ItemEntity> itr = event.getDrops().iterator();
			while (itr.hasNext()) {
				ItemEntity itemEntity = itr.next();
				if (itemEntity.getItem().getItem() instanceof BrandSpell) {
					itr.remove();
					player.addItemStackToInventory(itemEntity.getItem());
				}
			}
		}
	}

	@SubscribeEvent
	public static void readdSpellsAfterDeath(PlayerEvent.Clone event) {
		event.getPlayer().inventory.copyInventory(event.getOriginal().inventory);
		Brand cloneBrand = event.getPlayer().getCapability(LeagueOfLegendsBrand.BRAND_CAP).orElse(new Brand());
		Brand originalBrand = event.getOriginal().getCapability(LeagueOfLegendsBrand.BRAND_CAP).orElse(new Brand());
		cloneBrand.setBrand(originalBrand.isBrand());
	}

}
