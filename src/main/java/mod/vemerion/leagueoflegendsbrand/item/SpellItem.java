package mod.vemerion.leagueoflegendsbrand.item;

import mod.vemerion.leagueoflegendsbrand.champion.Champion;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.champion.SpellKey;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class SpellItem extends Item {

	private SpellKey key;
	private int duration;
	private Champion champion;

	public SpellItem(SpellKey key, int duration, Champion champion) {
		super(new Item.Properties().maxStackSize(1));
		this.key = key;
		this.duration = duration;
		this.champion = champion;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (canCast(playerIn)) {
			Champions.get(playerIn).ifPresent(c -> c.getSpell(key).start(worldIn, playerIn, handIn));
			playerIn.setActiveHand(handIn);
			return ActionResult.resultSuccess(itemstack);
		} else {
			return ActionResult.resultFail(itemstack);
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		Champions.get(entityLiving).ifPresent(c -> c.getSpell(key).finish(stack, worldIn, (PlayerEntity) entityLiving));
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		Champions.get(entityLiving)
				.ifPresent(c -> c.getSpell(key).stop(stack, worldIn, (PlayerEntity) entityLiving, timeLeft));
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return duration;
	}

	private boolean canCast(PlayerEntity player) {
		LazyOptional<Champions> champions = Champions.get(player);
		return champions.isPresent() && champions.orElse(null).isChampion(champion);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return new ItemEntity(world, location.getPosX(), location.getPosY(), location.getPosZ(), ItemStack.EMPTY);
	}
}
