package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.EnumMap;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import mod.vemerion.leagueoflegendsbrand.init.ModItems;
import mod.vemerion.leagueoflegendsbrand.network.BurningAgonyMessage;
import mod.vemerion.leagueoflegendsbrand.network.Network;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;

public class MundoChampion extends ChampionImplementation {

	private static final int AGONY_DAMAGE_INTERVAL = 5;

	private boolean burningAgonyActivated;
	private int agonyDamageTimer;

	public MundoChampion(PlayerEntity player) {
		super(player);
	}

	@Override
	protected void addSpells(EnumMap<SpellKey, Spell> spells) {
		spells.put(SpellKey.Q, new InfectedCleaver());
		spells.put(SpellKey.W, new BurningAgony(this));
		spells.put(SpellKey.E, new Masochism());
		spells.put(SpellKey.R, new Sadism());
	}

	@Override
	public Set<Item> getSpellItems() {
		return ImmutableSet.of(ModItems.INFECTED_CLEAVER, ModItems.BURNING_AGONY, ModItems.MASOCHISM, ModItems.SADISM);
	}

	@Override
	public void tick() {
		if (burningAgonyActivated)
			if (agonyDamageTimer++ > AGONY_DAMAGE_INTERVAL) {
				agonyDamageTimer = 0;
				for (LivingEntity e : player.world.getEntitiesWithinAABB(LivingEntity.class,
						player.getBoundingBox().grow(2), e -> e != player)) {
					e.attackEntityFrom(DamageSource.causePlayerDamage(player), 2);
				}
				player.attackEntityFrom(DamageSource.MAGIC, 1);
			}
	}

	@Override
	public void sync(PacketTarget target) {
		Network.INSTANCE.send(target, new BurningAgonyMessage(burningAgonyActivated, player.getUniqueID()));
	}

	public boolean isBurningAgonyActivated() {
		return burningAgonyActivated;
	}

	public void setBurningAgonyActivated(boolean burningAgonyActivated) {
		this.burningAgonyActivated = burningAgonyActivated;
	}

	private static class BurningAgony extends Spell {

		private static final int COOLDOWN = 20 * 4;
		private static final int TOGGLE_COOLDOWN = 10;

		private MundoChampion mundo;

		public BurningAgony(MundoChampion mundo) {
			this.mundo = mundo;
		}

		@Override
		public void start(ItemStack stack, World world, PlayerEntity player, Hand hand) {
			if (!world.isRemote) {
				setCooldown(player, stack, mundo.burningAgonyActivated ? COOLDOWN : TOGGLE_COOLDOWN);
				mundo.agonyDamageTimer = 0;
			}
			mundo.burningAgonyActivated = !mundo.burningAgonyActivated;

		}
	}
	
	private static class InfectedCleaver extends Spell {
	}
	
	private static class Masochism extends Spell {
	}
	
	private static class Sadism extends Spell {
	}
}
