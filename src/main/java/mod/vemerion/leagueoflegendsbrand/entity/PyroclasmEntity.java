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
			Entity shooter = func_234616_v_();
			if (hitEntity instanceof LivingEntity && shooter instanceof PlayerEntity) {
				hitEntity.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) shooter), 10);
				ConflagrationEntity fireEffect = new ConflagrationEntity(LeagueOfLegendsBrand.CONFLAGRATION_ENTITY,
						world, hitEntity);
				fireEffect.setPosition(hitEntity.getPosX(), hitEntity.getPosY(), hitEntity.getPosZ());
				world.addEntity(fireEffect);
				
				Ablazed ablazed = hitEntity.getCapability(LeagueOfLegendsBrand.ABLAZED_CAP).orElse(new Ablazed());
				if (ablazed.getAblazed() > 0) {
					((LivingEntity)hitEntity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 5, 1));
				}
				ablazed.incAblazed();

				if (bounces > 0) {
					bounce(hitEntity);
				}
			}
		}
		remove();
	}

	private void bounce(Entity bouncer) {
		Entity shooter = func_234616_v_();
		List<Entity> nearby = world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(5),
				(e) -> e instanceof LivingEntity && (target == null || e.getEntityId() != target.getEntityId())
						&& (shooter == null || e.getEntityId() != shooter.getEntityId())
						&& e.getEntityId() != bouncer.getEntityId() && e.isAlive());
		if (!nearby.isEmpty()) {
			Entity next = nearby.get(rand.nextInt(nearby.size()));
			Vector3d position = getPositionVec();
			Vector3d direction = next.getEyePosition(0.5f).subtract(position);
			PyroclasmEntity pyro = new PyroclasmEntity(LeagueOfLegendsBrand.PYROCLASM_ENTITY, position.getX(),
					position.getY(), position.getZ(), world, next, bounces - 1);
			pyro.bouncer = bouncer;
			pyro.setShooter(shooter);
			pyro.shoot(direction.getX(), direction.getY(), direction.getZ(), 0.3f, 0);
			world.addEntity(pyro);
		}
	}

}
