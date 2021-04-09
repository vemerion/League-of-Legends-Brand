package mod.vemerion.leagueoflegendsbrand.entity;

import java.util.List;

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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PyroclasmEntity extends BrandBallEntity {

	private Entity target;
	private int bounces;
	public Entity bouncer;

	public PyroclasmEntity(EntityType<? extends PyroclasmEntity> type, World world) {
		super(type, world);
	}

	public PyroclasmEntity(EntityType<? extends PyroclasmEntity> type, double x, double y, double z, World worldIn) {
		super(type, x, y, z, worldIn);
	}

	public PyroclasmEntity(EntityType<? extends PyroclasmEntity> type, double x, double y, double z, World worldIn,
			Entity target, int bounces) {
		super(type, x, y, z, worldIn);
		this.target = target;
		this.bounces = bounces;
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote) {
			if (ticksExisted > 100)
				remove();
			if (target != null && target.isAlive()) {
				shoot(target.getPosX() - getPosX(), target.getEyePosition(0).getY() - getPosY(),
						target.getPosZ() - getPosZ(), 0.3f, 0);
				playSound(LeagueOfLegendsBrand.FIREBALL_WOOSH_SOUND, 2f, 0.8f + rand.nextFloat() * 0.4f);

			}
		}
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		if (!world.isRemote) {
			Entity hitEntity = result.getEntity();
			if (hitEntity == bouncer)
				return;

			Entity shooter = func_234616_v_();
			if (hitEntity instanceof LivingEntity && shooter instanceof PlayerEntity) {
				hitEntity.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) shooter), 10);
				Ablazed.startBurning(hitEntity);

				Ablazed.get(hitEntity).ifPresent(ablazed -> {
					if (ablazed.get() > 0) {
						((LivingEntity) hitEntity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 5, 1));
					}
					ablazed.inc();
				});

				if (bounces > 0) {
					bounce(hitEntity);
				}
			}
			remove();
		}
	}

	private void bounce(Entity hitEntity) {
		Entity shooter = func_234616_v_();
		List<LivingEntity> nearby = world.getEntitiesWithinAABB(LivingEntity.class, getBoundingBox().grow(5),
				e -> isValidTarget(e, hitEntity));
		if (!nearby.isEmpty()) {
			Entity next = nearby.get(rand.nextInt(nearby.size()));
			Vector3d position = getPositionVec();
			Vector3d direction = next.getEyePosition(0.5f).subtract(position);
			PyroclasmEntity pyro = new PyroclasmEntity(LeagueOfLegendsBrand.PYROCLASM_ENTITY, position.getX(),
					position.getY(), position.getZ(), world, next, bounces - 1);
			pyro.bouncer = hitEntity;
			pyro.setShooter(shooter);
			pyro.shoot(direction.getX(), direction.getY(), direction.getZ(), 0.3f, 0);
			world.addEntity(pyro);
		}
	}

	private boolean isValidTarget(Entity e, Entity hitEntity) {
		return e != func_234616_v_() && e != hitEntity && e.isAlive();
	}

}
