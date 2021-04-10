package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import mod.vemerion.leagueoflegendsbrand.init.ModEntities;
import mod.vemerion.leagueoflegendsbrand.init.ModItems;
import mod.vemerion.leagueoflegendsbrand.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;

public class BrandChampion extends ChampionImplementation {

	public BrandChampion(PlayerEntity player) {
		super(player, new ProjectileSpell(Lazy.of(() -> ModEntities.SEAR), 0.5f, 140, Lazy.of(() -> ModSounds.FIREBALL)),
				new PillarOfFlame(), new Conflagration(), new ProjectileSpell(Lazy.of(() -> ModEntities.PYROCLASM),
						0.7f, 1000, Lazy.of(() -> ModSounds.FIREBALL_WOOSH)));
	}

	@Override
	public Set<Item> getSpellItems() {
		return ImmutableSet.of(ModItems.CONFLAGRATION, ModItems.PILLAR_OF_FLAME, ModItems.PYROCLASM, ModItems.SEAR);
	}

	private static class Conflagration extends Spell {
		@Override
		public void finish(ItemStack stack, World world, PlayerEntity player) {
			player.playSound(ModSounds.FIREBALL_SPELL, 1, 0.8f + player.getRNG().nextFloat() * 0.4f);
			if (!world.isRemote) {
				Entity target = findTarget(player.getPositionVec().add(0, 1.6, 0),
						Vector3d.fromPitchYaw(player.getPitchYaw()).scale(0.5), 7, world, player);
				if (target != null) {
					target.attackEntityFrom(DamageSource.causePlayerDamage(player), 4);
					setCooldown(player, stack, 160);
					Ablazed.startBurning(target);

					Ablazed.get(target).ifPresent(ablazed -> {
						if (ablazed.get() > 0) {
							for (Entity nearby : world.getEntitiesInAABBexcluding(target,
									target.getBoundingBox().grow(2), (e) -> e instanceof LivingEntity && e != player)) {
								Ablazed.get(nearby).ifPresent(a -> a.inc());
								nearby.attackEntityFrom(DamageSource.causePlayerDamage(player), 4);
								Ablazed.startBurning(nearby);
							}
						}
						ablazed.inc();
					});
				}
			}
		}

		private Entity findTarget(Vector3d start, Vector3d direction, float distance, World world,
				LivingEntity caster) {
			AxisAlignedBB box = new AxisAlignedBB(start, start).grow(0.25);
			for (int i = 0; i < distance * 2; i++) {
				for (Entity e : world.getEntitiesInAABBexcluding(caster, box, (e) -> e instanceof LivingEntity)) {
					return e;
				}
				box = box.offset(direction);
			}
			return null;
		}
	}

	private static class PillarOfFlame extends Spell {

		private PillarOfFlameEntity pillarOfFlame;

		@Override
		public void finish(ItemStack stack, World world, PlayerEntity player) {
			if (!world.isRemote) {
				setCooldown(player, stack, 180);
				if (pillarOfFlame != null)
					pillarOfFlame.setExplode(true);
			}
		}

		@Override
		public void stop(ItemStack stack, World world, PlayerEntity player, int timeLeft) {
			if (!world.isRemote && pillarOfFlame != null)
				pillarOfFlame.remove();
		}

		@Override
		public void start(World world, PlayerEntity player, Hand hand) {
			Vector3d position = aoEPlacement(world, player);
			if (position != null) {
				PillarOfFlameEntity entity = new PillarOfFlameEntity(ModEntities.PILLAR_OF_FLAME, world, player);
				entity.setPositionAndRotation(position.getX(), position.getY(), position.getZ(), 0, 0);
				world.addEntity(entity);
				pillarOfFlame = entity;
			}
		}

		private Vector3d aoEPlacement(World world, LivingEntity entity) {
			double distance = MathHelper.clampedLerp(7, 1, (entity.rotationPitch + 10) / 80f);
			Vector3d position = Vector3d.fromPitchYaw(0, entity.rotationYaw).scale(distance)
					.add(entity.getPositionVec());
			BlockPos pos = new BlockPos(position);
			if (world.isAirBlock(pos) && !world.isAirBlock(pos.add(0, -1, 0))) {
				return position;
			} else if (world.isAirBlock(pos.add(0, 1, 0)) && !world.isAirBlock(pos)) {
				return position.add(0, 1, 0);
			} else if (world.isAirBlock(pos.add(0, -1, 0)) && !world.isAirBlock(pos.add(0, -2, 0))) {
				return position.add(0, -1, 0);
			} else {
				return null;
			}
		}
	}

}
