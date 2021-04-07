package mod.vemerion.leagueoflegendsbrand.capability;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.PacketDistributor;

public class Brand implements INBTSerializable<CompoundNBT> {

	@CapabilityInject(Brand.class)
	public static final Capability<Brand> CAPABILITY = null;

	private PillarOfFlameEntity pillarOfFlame;
	private boolean isBrand;

	public PillarOfFlameEntity getPillarOfFlame() {
		return pillarOfFlame;
	}

	public void setPillarOfFlame(PillarOfFlameEntity pillarOfFlame) {
		this.pillarOfFlame = pillarOfFlame;
	}

	public boolean isBrand() {
		return isBrand;
	}

	public void setBrand(boolean value) {
		isBrand = value;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putBoolean("isBrand", isBrand());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		setBrand(nbt.getBoolean("isBrand"));
	}

	public static LazyOptional<Brand> getBrand(Entity player) {
		return player.getCapability(CAPABILITY);
	}

	public static void syncBrand(Entity entity) {
		getBrand(entity).ifPresent(b -> {
			BrandMessage.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
					new BrandMessage(b.isBrand(), entity.getUniqueID()));
		});
	}

	public static void syncBrand(Entity entity, ServerPlayerEntity reciever) {
		getBrand(entity).ifPresent(b -> {
			BrandMessage.INSTANCE.send(PacketDistributor.PLAYER.with(() -> reciever),
					new BrandMessage(b.isBrand(), entity.getUniqueID()));
		});
	}

	@EventBusSubscriber(modid = LeagueOfLegendsBrand.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class Provider implements ICapabilitySerializable<INBT> {

		public static final ResourceLocation LOCATION = new ResourceLocation(LeagueOfLegendsBrand.MODID, "brand");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof PlayerEntity)
				event.addCapability(LOCATION, new Provider());
		}

		private LazyOptional<Brand> instance = LazyOptional.of(Brand::new);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public INBT serializeNBT() {
			return CAPABILITY.writeNBT(
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			CAPABILITY.readNBT(
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null,
					nbt);
		}

	}

	public static class BrandStorage implements IStorage<Brand> {

		@Override
		public INBT writeNBT(Capability<Brand> capability, Brand instance, Direction side) {
			return instance.serializeNBT();

		}

		@Override
		public void readNBT(Capability<Brand> capability, Brand instance, Direction side, INBT nbt) {
			instance.deserializeNBT((CompoundNBT) nbt);
		}

	}
}
