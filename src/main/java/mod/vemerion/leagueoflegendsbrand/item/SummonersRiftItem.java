package mod.vemerion.leagueoflegendsbrand.item;

import java.util.function.Supplier;

import mod.vemerion.leagueoflegendsbrand.champion.Champion;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class SummonersRiftItem extends Item {

	private Champion champ;
	private Supplier<SoundEvent> finish;
	private Supplier<SoundEvent> continuous;

	public SummonersRiftItem(Champion champ, Supplier<SoundEvent> continuous, Supplier<SoundEvent> finish) {
		super(new Item.Properties().maxStackSize(1).group(ItemGroup.MISC));
		this.champ = champ;
		this.continuous = continuous;
		this.finish = finish;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
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
		if (continuous != null && count % 5 == 0)
			livingEntityIn.playSound(continuous.get(), 1, 0.9f + livingEntityIn.getRNG().nextFloat() * 0.2f);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return ActionResult.resultSuccess(itemstack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		if (finish != null)
			entityLiving.playSound(finish.get(), 1, 0.9f + entityLiving.getRNG().nextFloat() * 0.2f);
		if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			Champions.get(player).ifPresent(c -> {
				if (c.isChampion(champ))
					c.setChampion(Champion.STEVE);
				else
					c.setChampion(champ);
			});
			Champions.sync(player);
		}
		return stack;
	}
}
