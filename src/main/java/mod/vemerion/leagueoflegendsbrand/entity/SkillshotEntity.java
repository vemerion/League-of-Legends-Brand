package mod.vemerion.leagueoflegendsbrand.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SkillshotEntity extends AbstractArrowEntity {
	
	private int duration;

	public SkillshotEntity(EntityType<? extends SkillshotEntity> type, World world, int duration) {
		super(type, world);
		this.setNoGravity(true);
		this.duration = duration;
	}

	public SkillshotEntity(EntityType<? extends SkillshotEntity> type, double x, double y, double z, World worldIn, int duration) {
		super(type, x, y, z, worldIn);
		this.setNoGravity(true);
		this.duration = duration;
	}
	
	@Override
	public void tick() {
		super.tick();
		if (!world.isRemote && duration-- < 0)
			remove();
	}
	
	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("duration"))
			duration = compound.getInt("duration");
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		
		compound.putInt("duration", duration);
	}

	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void onImpact(RayTraceResult raytraceResultIn) {
		RayTraceResult.Type resultType = raytraceResultIn.getType();
		if (resultType == RayTraceResult.Type.ENTITY) {
			this.onEntityHit((EntityRayTraceResult) raytraceResultIn);
		} else if (resultType == RayTraceResult.Type.BLOCK) {
			remove();
		}
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void setShooter(@Nullable Entity entityIn) {
		super.setShooter(entityIn);
		pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
	}
}
