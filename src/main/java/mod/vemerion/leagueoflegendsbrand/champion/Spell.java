package mod.vemerion.leagueoflegendsbrand.champion;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class Spell {
	public void finish(ItemStack stack, World world, PlayerEntity player) {
	}

	public void stop(ItemStack stack, World world, PlayerEntity player, int timeLeft) {
	}

	public void start(World world, PlayerEntity player, Hand hand) {
	}

	protected void setCooldown(PlayerEntity player, ItemStack stack, int duration) {
		player.getCooldownTracker().setCooldown(stack.getItem(), duration);
	}
}
