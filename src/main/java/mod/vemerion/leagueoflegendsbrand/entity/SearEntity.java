package mod.vemerion.leagueoflegendsbrand.entity;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class SearEntity extends BrandBallEntity {

	public SearEntity(EntityType<? extends SearEntity> type, World world) {
		super(type, world);
	}

	public SearEntity(EntityType<? extends SearEntity> type, double x, double y, double z, World worldIn) {
		super(type, x, y, z, worldIn);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (!world.isRemote && ticksExisted > 25) {
			remove();
		}
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		if (!world.isRemote) {
			Entity target = result.getEntity();
			Entity shooter = func_234616_v_();
			if (target instanceof LivingEntity && shooter instanceof PlayerEntity) {
				target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity)shooter), 4);
				Ablazed ablazed = target.getCapability(LeagueOfLegendsBrand.ABLAZED_CAP).orElse(new Ablazed());
				if (ablazed.getAblazed() > 0 && target instanceof LivingEntity) {
					((LivingEntity)target).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 30, 10));

				}
				ablazed.incAblazed();
			}
		}
		remove();
	}

}
