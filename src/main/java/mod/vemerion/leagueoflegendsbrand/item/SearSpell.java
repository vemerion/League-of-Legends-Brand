package mod.vemerion.leagueoflegendsbrand.item;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.SearEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SearSpell extends BrandSpell {

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		entityLiving.playSound(LeagueOfLegendsBrand.FIREBALL_SOUND, 1, 0.8f + entityLiving.getRNG().nextFloat() * 0.4f);
		if (!worldIn.isRemote) {
			Vec3d direction = Vec3d.fromPitchYaw(entityLiving.getPitchYaw());
			Vec3d position = entityLiving.getPositionVec().add(direction.getX() * 0.8, 0.8, direction.getZ() * 0.8);
			SearEntity sear = new SearEntity(LeagueOfLegendsBrand.SEAR_ENTITY, position.getX(), position.getY(),
					position.getZ(), worldIn);
			sear.setShooter(entityLiving);
			sear.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0, 0.5f, 0);
			worldIn.addEntity(sear);
		}
		if (entityLiving instanceof PlayerEntity) {
			((PlayerEntity) entityLiving).getCooldownTracker().setCooldown(this, 140);
		}
		return stack;
	}
}
