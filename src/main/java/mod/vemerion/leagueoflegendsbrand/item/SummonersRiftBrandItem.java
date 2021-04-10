package mod.vemerion.leagueoflegendsbrand.item;

import mod.vemerion.leagueoflegendsbrand.champion.Champion;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.init.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SummonersRiftBrandItem extends Item {

	public SummonersRiftBrandItem() {
		super(new Item.Properties().maxStackSize(1).group(ItemGroup.MISC));
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.RARE;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 40;
	}

	@Override
	public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		if (count % 5 == 0)
			livingEntityIn.playSound(ModSounds.BURNING, 1.5f, 0.9f + livingEntityIn.getRNG().nextFloat() * 0.2f);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return ActionResult.resultSuccess(itemstack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		entityLiving.playSound(ModSounds.EXPLOSION, 0.6f, 0.9f + entityLiving.getRNG().nextFloat() * 0.2f);
		if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			Champions.get(player).ifPresent(c -> {
				if (c.isChampion(Champion.BRAND))
					c.setChampion(Champion.STEVE);
				else
					c.setChampion(Champion.BRAND);
			});
			Champions.sync(player);
		}
		return stack;
	}
}
