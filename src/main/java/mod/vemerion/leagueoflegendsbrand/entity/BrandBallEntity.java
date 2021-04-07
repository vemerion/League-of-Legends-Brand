package mod.vemerion.leagueoflegendsbrand.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BrandBallEntity extends AbstractArrowEntity {

	public BrandBallEntity(EntityType<? extends BrandBallEntity> type, World world) {
		super(type, world);
		this.setNoGravity(true);
	}

	public BrandBallEntity(EntityType<? extends BrandBallEntity> type, double x, double y, double z, World worldIn) {
		super(type, x, y, z, worldIn);
		this.setNoGravity(true);
	}

	@Override
	protected ItemStack getArrowStack() {
		return null;
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
