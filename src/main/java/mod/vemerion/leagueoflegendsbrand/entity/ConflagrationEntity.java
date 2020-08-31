package mod.vemerion.leagueoflegendsbrand.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class ConflagrationEntity extends BurningEntity {

	public ConflagrationEntity(EntityType<? extends ConflagrationEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public ConflagrationEntity(EntityType<? extends ConflagrationEntity> entityTypeIn, World worldIn, Entity target) {
		super(entityTypeIn, worldIn, target);
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote) {
			if (ticksExisted > 25)
				remove();
		}
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {

	}

}
