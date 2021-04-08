package mod.vemerion.leagueoflegendsbrand.capability;

import com.google.common.collect.ImmutableSet;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

	private static final ImmutableSet<Item> SPELLS = ImmutableSet.of(LeagueOfLegendsBrand.CONFLAGRATION_SPELL,
			LeagueOfLegendsBrand.PILLAR_OF_FLAME_SPELL, LeagueOfLegendsBrand.PYROCLASM_SPELL,
			LeagueOfLegendsBrand.SEAR_SPELL);

	private PillarOfFlameEntity pillarOfFlame;
	private boolean isBrand;
	private PlayerEntity player;

	public Brand(PlayerEntity player) {
		this.player = player;
	}

	public void tick() {
		if (!player.world.isRemote)
			if (!isBrand)
				ItemStackHelper.func_233534_a_(player.inventory, s -> getSpells().contains(s.getItem()), -1, false);
			else
				addSpellsToInv();
	}

	private void addSpellsToInv() {
		for (Item spell : getSpells()) {
			ItemStack stack = spell.getDefaultInstance();
			if (!player.inventory.hasItemStack(stack))
				player.addItemStackToInventory(stack);
		}
	}

	private ImmutableSet<Item> getSpells() {
		return SPELLS;
	}

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

	public static LazyOptional<Brand> get(Entity player) {
		return player.getCapability(CAPABILITY);
	}

	public static void sync(Entity entity) {
		get(entity).ifPresent(b -> {
			BrandMessage.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
					new BrandMessage(b.isBrand(), entity.getUniqueID()));
		});
	}

	public static void sync(Entity entity, ServerPlayerEntity reciever) {
		get(entity).ifPresent(b -> {
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
				event.addCapability(LOCATION, new Provider((PlayerEntity) event.getObject()));
		}

		private PlayerEntity player;
		private LazyOptional<Brand> instance = LazyOptional.of(() -> new Brand(player));

		public Provider(PlayerEntity player) {
			this.player = player;
		}

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
