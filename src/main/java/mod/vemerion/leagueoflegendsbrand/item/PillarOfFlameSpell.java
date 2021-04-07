package mod.vemerion.leagueoflegendsbrand.item;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PillarOfFlameSpell extends BrandSpell {

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			player.getCooldownTracker().setCooldown(this, 180);
			PillarOfFlameEntity entity = player.getCapability(LeagueOfLegendsBrand.BRAND_CAP)
					.orElseThrow(() -> new IllegalArgumentException("Player does not have Brand cap"))
					.getPillarOfFlame();
			if (entity != null) {
				entity.setExplode(true);
			}
		}
		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			PillarOfFlameEntity entity = player.getCapability(LeagueOfLegendsBrand.BRAND_CAP)
					.orElseThrow(() -> new IllegalArgumentException("Player does not have Brand cap"))
					.getPillarOfFlame();
			if (entity != null) {
				entity.remove();
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		Vector3d position = aoEPlacement(worldIn, playerIn);
		if (position != null && canCast(playerIn)) {
			PillarOfFlameEntity entity = new PillarOfFlameEntity(LeagueOfLegendsBrand.PILLAR_OF_FLAME_ENTITY, worldIn, playerIn);
			entity.setPositionAndRotation(position.getX(), position.getY(), position.getZ(), 0, 0);
			worldIn.addEntity(entity);
			playerIn.getCapability(LeagueOfLegendsBrand.BRAND_CAP)
					.orElseThrow(() -> new IllegalArgumentException("Player does not have Brand cap"))
					.setPillarOfFlame(entity);
			return super.onItemRightClick(worldIn, playerIn, handIn);
		} else {
			return ActionResult.resultFail(playerIn.getHeldItem(handIn));
		}
	}

	private Vector3d aoEPlacement(World world, LivingEntity entity) {
		double distance = MathHelper.clampedLerp(7, 1, (entity.rotationPitch + 10) / 80f);
		Vector3d position = Vector3d.fromPitchYaw(0, entity.rotationYaw).scale(distance).add(entity.getPositionVec());
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
