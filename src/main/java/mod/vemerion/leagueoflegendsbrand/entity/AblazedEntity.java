package mod.vemerion.leagueoflegendsbrand.entity;

import mod.vemerion.leagueoflegendsbrand.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class AblazedEntity extends BurningEntity {

	public AblazedEntity(EntityType<? extends AblazedEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}
	
	public AblazedEntity(EntityType<? extends AblazedEntity> entityTypeIn, World worldIn, Entity target) {
		super(entityTypeIn, worldIn, target);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (ticksExisted % 5 == 0)
			playSound(ModSounds.BURNING, 1.3f, 0.8f + rand.nextFloat() * 0.4f);
		
		if (!world.isRemote) {
			if (ticksExisted > 60)
				remove();
			
			if (ticksExisted == 40) {
				playSound(ModSounds.EXPLOSION, 1f, 0.65f + rand.nextFloat() * 0.4f);
				for (Entity e : world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(3), (e) -> e instanceof LivingEntity)) {
					e.attackEntityFrom(DamageSource.IN_FIRE, 6);
				}
			}
		}
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {

	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {

	}

}
