package mod.vemerion.leagueoflegendsbrand.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class BurningEntity extends Entity {
	protected static final DataParameter<Integer> TARGET = EntityDataManager.createKey(BurningEntity.class,
			DataSerializers.VARINT);
	
	private Entity target;

	public BurningEntity(EntityType<? extends BurningEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public BurningEntity(EntityType<? extends BurningEntity> entityTypeIn, World worldIn, Entity target) {
		super(entityTypeIn, worldIn);
		this.target = target;
		getTargetId(target.getEntityId());
		this.setNoGravity(true);
	}
	
	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote) {
			if (getTarget() != null) {
				move(MoverType.SELF, target.getMotion());
			} else {
				remove();
			}
		} else {
			if (getTarget() != null) {
				move(MoverType.SELF, target.getMotion());
			}
		}
	}
	
	public Entity getTarget() {
		if (target != null)
			return target;
		int id = dataManager.get(TARGET);
		Entity entity = world.getEntityByID(id);
		target = entity;
		return target;
	}
	
	public void getTargetId(int id) {
		dataManager.set(TARGET, id);
	}
	

	@Override
	protected void registerData() {
		this.dataManager.register(TARGET, -1);
	}

	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
