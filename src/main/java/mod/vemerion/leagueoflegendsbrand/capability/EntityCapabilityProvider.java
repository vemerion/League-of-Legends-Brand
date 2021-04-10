package mod.vemerion.leagueoflegendsbrand.capability;

import java.util.function.Function;
import java.util.function.Supplier;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class EntityCapabilityProvider<E extends Entity, C> implements ICapabilitySerializable<INBT> {

	public static final ResourceLocation ABLAZED_LOC = new ResourceLocation(Main.MODID, "ablazed");
	public static final ResourceLocation CHAMPION_LOC = new ResourceLocation(Main.MODID, "champion");

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof LivingEntity)
			event.addCapability(ABLAZED_LOC, new EntityCapabilityProvider<LivingEntity, Ablazed>(
					Lazy.of(() -> Ablazed.CAPABILITY), Ablazed::new, (LivingEntity) event.getObject()));
		if (event.getObject() instanceof PlayerEntity)
			event.addCapability(CHAMPION_LOC, new EntityCapabilityProvider<PlayerEntity, Champions>(
					Lazy.of(() -> Champions.CAPABILITY), Champions::new, (PlayerEntity) event.getObject()));
	}

	private Supplier<Capability<C>> capability;
	private Function<E, C> factory;
	private E entity;
	private LazyOptional<C> instance = LazyOptional.of(() -> factory.apply(entity));

	public EntityCapabilityProvider(Supplier<Capability<C>> capability, Function<E, C> factory, E entity) {
		this.capability = capability;
		this.factory = factory;
		this.entity = entity;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return capability.get().orEmpty(cap, instance);
	}

	@Override
	public INBT serializeNBT() {
		return capability.get().writeNBT(
				instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		capability.get().readNBT(
				instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
	}

}
