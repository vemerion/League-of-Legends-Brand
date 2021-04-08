package mod.vemerion.leagueoflegendsbrand.capability;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.AblazedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class Ablazed implements INBTSerializable<CompoundNBT> {

	@CapabilityInject(Ablazed.class)
	public static final Capability<Ablazed> CAPABILITY = null;

	private static int MAX_TIMER = 20 * 4;
	private static int ABLAZE_COUNT = 3;

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

	// Should only be called on server
	public void tick() {
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
	}

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
}
