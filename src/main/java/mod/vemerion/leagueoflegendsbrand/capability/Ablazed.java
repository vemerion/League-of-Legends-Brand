package mod.vemerion.leagueoflegendsbrand.capability;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.AblazedEntity;
import mod.vemerion.leagueoflegendsbrand.network.BurningMessage;
import mod.vemerion.leagueoflegendsbrand.network.Network;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public class Ablazed implements INBTSerializable<CompoundNBT> {

	@CapabilityInject(Ablazed.class)
	public static final Capability<Ablazed> CAPABILITY = null;

	private static int MAX_TIMER = 20 * 4;
	private static int ABLAZE_COUNT = 3;
	private static int MAX_BURNING = 25;

	private int burning; // Only for client render effect

	private int count;
	private int timer;
	private LivingEntity entity;

	public Ablazed(LivingEntity entity) {
		this.entity = entity;
	}

	public int get() {
		return count;
	}

	private void set(int i) {
		count = i;
		timer = MAX_TIMER;
	}

	public void inc() {
		set(get() + 1);
	}

	public void dec() {
		set(Math.max(0, get() - 1));
	}

	public void tick() {
		if (!entity.world.isRemote) {
			timer--;

			if (get() > 0 && entity.getFireTimer() <= 1)
				entity.setFire(1);

			if (timer == 0)
				dec();

			if (count == ABLAZE_COUNT) {
				set(1);
				AblazedEntity ablazed = new AblazedEntity(LeagueOfLegendsBrand.ABLAZED_ENTITY, entity.world, entity);
				ablazed.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
				entity.world.addEntity(ablazed);
			}
		} else {
			if (burning > 0)
				burning--;
		}
	}

	// CLIENT ONLY START
	public void setBurning() {
		burning = MAX_BURNING;
	}

	public int getBurning() {
		return MAX_BURNING - burning;
	}
	
	public boolean isBurning() {
		return burning > 0;
	}
	// CLIENT ONLY STOP

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("count", get());
		compound.putInt("timer", timer);
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		count = nbt.getInt("count");
		timer = nbt.getInt("timer");
	}

	public static LazyOptional<Ablazed> get(Entity entity) {
		return entity.getCapability(CAPABILITY);
	}

	public static void startBurning(Entity entity) {
		Network.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
				new BurningMessage(entity.getEntityId()));
	}
}
