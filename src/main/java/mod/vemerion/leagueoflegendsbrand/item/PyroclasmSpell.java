package mod.vemerion.leagueoflegendsbrand.item;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.PyroclasmEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PyroclasmSpell extends BrandSpell {
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		entityLiving.playSound(LeagueOfLegendsBrand.FIREBALL_WOOSH_SOUND, 2f, 0.8f + entityLiving.getRNG().nextFloat() * 0.4f);
		if (!worldIn.isRemote) {
			Vector3d direction = Vector3d.fromPitchYaw(entityLiving.getPitchYaw());
			Vector3d position = entityLiving.getPositionVec().add(direction.getX() * 0.8, 0.8, direction.getZ() * 0.8);
			PyroclasmEntity pyro = new PyroclasmEntity(LeagueOfLegendsBrand.PYROCLASM_ENTITY, position.getX(), position.getY(),
					position.getZ(), worldIn, null, 4);
			pyro.setShooter(entityLiving);
			pyro.func_234612_a_(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0, 0.7f, 0);
			worldIn.addEntity(pyro);
		}
		if (entityLiving instanceof PlayerEntity) {
			((PlayerEntity) entityLiving).getCooldownTracker().setCooldown(this, 1000);
		}
		return stack;
	}
}
