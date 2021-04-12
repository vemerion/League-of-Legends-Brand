package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.EnumMap;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class SteveChampion extends ChampionImplementation {

	public SteveChampion(PlayerEntity player) {
		super(player);
	}

	@Override
	public Set<Item> getSpellItems() {
		return ImmutableSet.of();
	}

	@Override
	protected void addSpells(EnumMap<SpellKey, Spell> spells) {
		
	}

}
