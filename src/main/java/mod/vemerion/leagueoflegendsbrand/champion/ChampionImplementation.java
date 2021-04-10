package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.Set;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public abstract class ChampionImplementation {

	protected PlayerEntity player;
	private Spell q, w, e, r;

	public ChampionImplementation(PlayerEntity player, Spell q, Spell w, Spell e, Spell r) {
		this.player = player;
		this.q = q;
		this.w = w;
		this.e = e;
		this.r = r;
	}

	public void tick() {

	}

	public Spell getSpell(SpellKey key) {
		switch (key) {
		case Q:
			return q;
		case W:
			return w;
		case E:
			return e;
		case R:
			return r;
		default:
			return null;
		}
	}

	public abstract Set<Item> getSpellItems();

}
