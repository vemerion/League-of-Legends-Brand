package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import mod.vemerion.leagueoflegendsbrand.init.ModEffects;
import mod.vemerion.leagueoflegendsbrand.item.SpellItem;
import mod.vemerion.leagueoflegendsbrand.network.ChampionMessage;
import mod.vemerion.leagueoflegendsbrand.network.EffectMessage;
import mod.vemerion.leagueoflegendsbrand.network.Network;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;

public class Champions implements INBTSerializable<CompoundNBT> {

	@CapabilityInject(Champions.class)
	public static final Capability<Champions> CAPABILITY = null;

	private Champion champion;
	private PlayerEntity player;
	private EnumMap<Champion, ChampionImplementation> champImpls;
	private Map<Effect, Boolean> effects;

	public Champions(PlayerEntity player) {
		this.player = player;
		this.setChampion(Champion.STEVE);

		this.initChamps();
		this.initEffects();
	}

	private void initEffects() {
		effects = new HashMap<>();
		effects.put(ModEffects.MASOCHISM, false);
		effects.put(ModEffects.SADISM, false);
	}

	private void initChamps() {
		champImpls = new EnumMap<>(Champion.class);
		champImpls.put(Champion.STEVE, new SteveChampion(player));
		champImpls.put(Champion.BRAND, new BrandChampion(player));
		champImpls.put(Champion.MUNDO, new MundoChampion(player));
	}

	public boolean isSteve() {
		return isChampion(Champion.STEVE);
	}

	public boolean isChampion() {
		return !isSteve();
	}

	public boolean isChampion(Champion champ) {
		return champion == champ;
	}

	public void setChampion(Champion champ) {
		champion = champ;
	}

	public Champion getChampion() {
		return champion;
	}

	public Spell getSpell(SpellKey key) {
		return getChampImpl().getSpell(key);
	}

	private ChampionImplementation getChampImpl() {
		return champImpls.get(champion);
	}

	public MundoChampion getMundo() {
		return (MundoChampion) champImpls.get(Champion.MUNDO);
	}

	public void tick() {
		if (!player.world.isRemote) {
			ItemStackHelper.func_233534_a_(player.inventory,
					s -> !isSpell(s.getItem()) && s.getItem() instanceof SpellItem, -1, false);

			syncEffects(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), false);
		}
		if (isChampion())
			addSpellsToInv();
		destroyMap();

		getChampImpl().tick();
	}

	public void setEffect(Effect effect, boolean active) {
		effects.put(effect, active);
	}

	public boolean hasEffect(Effect effect) {
		return effects.getOrDefault(effect, false);
	}

	private void destroyMap() {
		World world = player.world;
		if (isSteve())
			return;

		for (Hand hand : Hand.values()) {
			if (player.getHeldItem(hand).getItem() == Items.FILLED_MAP) {
				if (!world.isRemote)
					player.setHeldItem(hand, ItemStack.EMPTY);
				else {
					Random rand = player.getRNG();
					for (int i = 0; i < 4; i++) {
						Vector3d position = player.getPositionVec()
								.add(MathHelper.nextDouble(rand, -0.4, 0.4), 1.2,
										MathHelper.nextDouble(rand, -0.4, 0.4))
								.add(Vector3d.fromPitchYaw(player.getPitchYaw()).scale(0.5));
						world.addParticle(ParticleTypes.FLAME, position.x, position.y, position.z, 0, 0, 0);
					}
				}
			}
		}
	}

	private void addSpellsToInv() {
		for (Item spell : getSpellItems()) {
			ItemStack stack = spell.getDefaultInstance();
			if (!player.inventory.hasItemStack(stack))
				player.addItemStackToInventory(stack);
		}
	}

	public boolean isSpell(Item item) {
		return getSpellItems().contains(item);
	}

	private Set<Item> getSpellItems() {
		return getChampImpl().getSpellItems();
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("champion", getChampion().getId());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		setChampion(Champion.get(nbt.getInt("champion")));
	}

	private void sync(PacketTarget target) {
		Network.INSTANCE.send(target, new ChampionMessage(getChampion().getId(), player.getUniqueID()));
		syncEffects(target, true);
		getChampImpl().sync(target);
	}

	private void syncEffects(PacketTarget target, boolean all) {
		for (Effect e : effects.keySet()) {
			boolean isActive = player.isPotionActive(e);
			if (all || isActive != effects.get(e)) {
				if (!all)
					effects.put(e, isActive);
				Network.INSTANCE.send(target, new EffectMessage(e, isActive, player.getUniqueID()));
			}
		}
	}

	public static LazyOptional<Champions> get(Entity player) {
		return player.getCapability(CAPABILITY);
	}

	public static void sync(Entity entity) {
		get(entity).ifPresent(c -> {
			c.sync(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity));
		});
	}

	public static void sync(Entity entity, ServerPlayerEntity reciever) {
		get(entity).ifPresent(c -> {
			c.sync(PacketDistributor.PLAYER.with(() -> reciever));
		});
	}
}
