package mod.vemerion.leagueoflegendsbrand.item;

import mod.vemerion.leagueoflegendsbrand.capability.Brand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public abstract class BrandSpell extends Item {

	public BrandSpell() {
		super(new Item.Properties().maxStackSize(1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (canCast(playerIn)) {
			playerIn.setActiveHand(handIn);
			return ActionResult.resultSuccess(itemstack);
		} else {
			return ActionResult.resultFail(itemstack);
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 8;
	}

	protected boolean canCast(PlayerEntity player) {
		LazyOptional<Brand> brand = Brand.get(player);
		return brand.isPresent() && brand.orElse(null).isBrand();
	}
}
