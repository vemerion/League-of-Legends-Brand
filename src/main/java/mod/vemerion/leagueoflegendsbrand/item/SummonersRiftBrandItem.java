package mod.vemerion.leagueoflegendsbrand.item;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.capability.Brand;
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
			livingEntityIn.playSound(LeagueOfLegendsBrand.BURNING_SOUND, 1.5f,
					0.9f + livingEntityIn.getRNG().nextFloat() * 0.2f);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return ActionResult.resultSuccess(itemstack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		entityLiving.playSound(LeagueOfLegendsBrand.EXPLOSION_SOUND, 0.6f,
				0.9f + entityLiving.getRNG().nextFloat() * 0.2f);
		if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			Brand.getBrand(player).ifPresent(brand -> {
				if (brand.isBrand()) {
					brand.setBrand(false);
				} else {
					player.addItemStackToInventory(new ItemStack(LeagueOfLegendsBrand.SEAR_SPELL));
					player.addItemStackToInventory(new ItemStack(LeagueOfLegendsBrand.PILLAR_OF_FLAME_SPELL));
					player.addItemStackToInventory(new ItemStack(LeagueOfLegendsBrand.CONFLAGRATION_SPELL));
					player.addItemStackToInventory(new ItemStack(LeagueOfLegendsBrand.PYROCLASM_SPELL));
					brand.setBrand(true);
				}
			});
			Brand.syncBrand(player);
		}
		return stack;
	}
}
