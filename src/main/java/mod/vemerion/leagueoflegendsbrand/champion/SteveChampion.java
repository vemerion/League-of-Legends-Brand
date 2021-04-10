package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class SteveChampion extends ChampionImplementation {

	public SteveChampion(PlayerEntity player) {
		super(player, STEVE_SPELL, STEVE_SPELL, STEVE_SPELL, STEVE_SPELL);
	}

	@Override
	public Set<Item> getSpellItems() {
		return ImmutableSet.of();
	}
	
	public static final Spell STEVE_SPELL = new Spell() {
		
	};


}
