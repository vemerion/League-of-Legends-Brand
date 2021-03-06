package mod.vemerion.leagueoflegendsbrand.entity;

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

public class SearEntity extends SkillshotEntity {

	public SearEntity(EntityType<? extends SearEntity> type, World world) {
		super(type, world, 25);
	}

	public SearEntity(EntityType<? extends SearEntity> type, double x, double y, double z, World worldIn) {
		super(type, x, y, z, worldIn, 25);
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		if (!world.isRemote) {
			Entity target = result.getEntity();
			Entity shooter = func_234616_v_();
			if (target instanceof LivingEntity && shooter instanceof PlayerEntity) {
				target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) shooter), 4);
				Ablazed.get(target).ifPresent(ablazed -> {
					if (ablazed.get() > 0 && target instanceof LivingEntity) {
						((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 30, 10));
					}
					ablazed.inc();
				});
			}
		}
		remove();
	}

}
