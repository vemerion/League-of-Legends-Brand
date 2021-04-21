package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.EnumMap;
import java.util.Set;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;

public abstract class ChampionImplementation {
	
	public static final Spell NULL_SPELL = new Spell() {
		
	};

	protected PlayerEntity player;
	private EnumMap<SpellKey, Spell> spells;

	public ChampionImplementation(PlayerEntity player) {
		this.player = player;
		this.spells = new EnumMap<>(SpellKey.class);
		this.addSpells(spells);
	}

	protected abstract void addSpells(EnumMap<SpellKey, Spell> spells);

	public void tick() {

	}

	public Spell getSpell(SpellKey key) {
		return spells.getOrDefault(key, NULL_SPELL);
	}

	public abstract Set<Item> getSpellItems();

	public void sync(PacketTarget target) {
		
	}

}
