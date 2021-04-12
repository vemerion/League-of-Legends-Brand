package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.EnumMap;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import mod.vemerion.leagueoflegendsbrand.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MundoChampion extends ChampionImplementation {

	private boolean burningAgonyActivated;

	public MundoChampion(PlayerEntity player) {
		super(player);
	}
	
	@Override
	protected void addSpells(EnumMap<SpellKey, Spell> spells) {
		spells.put(SpellKey.W, new BurningAgony(this));
	}


	@Override
	public Set<Item> getSpellItems() {
		return ImmutableSet.of(ModItems.BURNING_AGONY);
	}

	private static class BurningAgony extends Spell {

		private MundoChampion mundo;

		public BurningAgony(MundoChampion mundo) {
			this.mundo = mundo;
		}

		@Override
		public void start(ItemStack stack, World world, PlayerEntity player, Hand hand) {
			if (!world.isRemote)
				setCooldown(player, stack, 20 * 4);
			mundo.burningAgonyActivated = !mundo.burningAgonyActivated;
		}
	}
}
