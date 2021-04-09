package mod.vemerion.leagueoflegendsbrand.item;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ConflagrationSpell extends BrandSpell {

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		entityLiving.playSound(ModSounds.FIREBALL_SPELL, 1, 0.8f + entityLiving.getRNG().nextFloat() * 0.4f);
		if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			Entity target = findTarget(entityLiving.getPositionVec().add(0, 1.6, 0),
					Vector3d.fromPitchYaw(entityLiving.getPitchYaw()).scale(0.5), 7, worldIn, entityLiving);
			if (target != null) {
				target.attackEntityFrom(DamageSource.causePlayerDamage(player), 4);
				player.getCooldownTracker().setCooldown(this, 160);
				Ablazed.startBurning(target);

				Ablazed.get(target).ifPresent(ablazed -> {
					if (ablazed.get() > 0) {
						for (Entity nearby : worldIn.getEntitiesInAABBexcluding(target, target.getBoundingBox().grow(2),
								(e) -> e instanceof LivingEntity && e != player)) {
							Ablazed.get(nearby).ifPresent(a -> a.inc());
							nearby.attackEntityFrom(DamageSource.causePlayerDamage(player), 4);
							Ablazed.startBurning(nearby);
						}
					}
					ablazed.inc();
				});
			}
		}
		return stack;
	}

	private Entity findTarget(Vector3d start, Vector3d direction, float distance, World world, LivingEntity caster) {
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
