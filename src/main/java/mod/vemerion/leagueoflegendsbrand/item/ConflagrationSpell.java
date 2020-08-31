package mod.vemerion.leagueoflegendsbrand.item;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.entity.ConflagrationEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ConflagrationSpell extends BrandSpell {

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		entityLiving.playSound(LeagueOfLegendsBrand.FIREBALL_SPELL, 1, 0.8f + entityLiving.getRNG().nextFloat() * 0.4f);
		if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			Entity target = findTarget(entityLiving.getPositionVec().add(0, 1.6, 0),
					Vec3d.fromPitchYaw(entityLiving.getPitchYaw()).scale(0.5), 7, worldIn, entityLiving);
			if (target != null) {
				target.attackEntityFrom(DamageSource.causePlayerDamage(player), 4);
				player.getCooldownTracker().setCooldown(this, 160);
				ConflagrationEntity entity = new ConflagrationEntity(LeagueOfLegendsBrand.CONFLAGRATION_ENTITY, worldIn, target);
				entity.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
				worldIn.addEntity(entity);
				
				Ablazed ablazed = target.getCapability(LeagueOfLegendsBrand.ABLAZED_CAP).orElse(new Ablazed());
				if (ablazed.getAblazed() > 0 && target instanceof LivingEntity) {
					for (Entity nearby : worldIn.getEntitiesInAABBexcluding(target, target.getBoundingBox().grow(2),
							(e) -> e instanceof LivingEntity)) {
						nearby.getCapability(LeagueOfLegendsBrand.ABLAZED_CAP).orElse(new Ablazed()).incAblazed();
						nearby.attackEntityFrom(DamageSource.causePlayerDamage(player), 4);
						entity = new ConflagrationEntity(LeagueOfLegendsBrand.CONFLAGRATION_ENTITY, worldIn, nearby);
						entity.setPosition(nearby.getPosX(), nearby.getPosY(), nearby.getPosZ());
						worldIn.addEntity(entity);
					}
				}
				ablazed.incAblazed();
			}
		}
		return stack;
	}

	private Entity findTarget(Vec3d start, Vec3d direction, float distance, World world, LivingEntity caster) {
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
