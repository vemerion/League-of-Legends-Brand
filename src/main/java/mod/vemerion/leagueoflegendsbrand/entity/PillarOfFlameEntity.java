package mod.vemerion.leagueoflegendsbrand.entity;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PillarOfFlameEntity extends Entity {

	private static final DataParameter<Boolean> EXPLODE = EntityDataManager.createKey(PillarOfFlameEntity.class,
			DataSerializers.BOOLEAN);

	private PlayerEntity owner;

	public PillarOfFlameEntity(EntityType<? extends PillarOfFlameEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public PillarOfFlameEntity(EntityType<? extends PillarOfFlameEntity> entityTypeIn, World worldIn,
			PlayerEntity owner) {
		super(entityTypeIn, worldIn);
		this.owner = owner;
	}

	@Override
	public void tick() {
		super.tick();

		if (ticksExisted % 5 == 0 && ticksExisted < 20)
			playSound(ModSounds.BURNING, 1.3f, 0.8f + rand.nextFloat() * 0.4f);

		if (!world.isRemote) {
			if (ticksExisted == 14 && canExplode()) {

				for (LivingEntity e : world.getEntitiesWithinAABB(LivingEntity.class, getBoundingBox().grow(1))) {
					Ablazed.get(e).ifPresent(ablazed -> {
						float damage = 5;
						if (ablazed.get() > 0) {
							damage += 2;
						}
						ablazed.inc();
						if (owner != null)
							e.attackEntityFrom(DamageSource.causePlayerDamage(owner), damage);
						else
							e.attackEntityFrom(DamageSource.IN_FIRE, damage);

					});
				}
			}

			if (ticksExisted > 25)
				remove();
		}
	}

	@Override
	protected void registerData() {
		dataManager.register(EXPLODE, false);
	}

	public boolean canExplode() {
		return dataManager.get(EXPLODE);
	}

	public void setExplode(boolean value) {
		this.dataManager.set(EXPLODE, value);
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {

	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
