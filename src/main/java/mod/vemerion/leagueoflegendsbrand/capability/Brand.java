package mod.vemerion.leagueoflegendsbrand.capability;

import java.util.Random;

import com.google.common.collect.ImmutableSet;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public class Brand implements INBTSerializable<CompoundNBT> {

	@CapabilityInject(Brand.class)
	public static final Capability<Brand> CAPABILITY = null;

	private static final ImmutableSet<Item> SPELLS = ImmutableSet.of(LeagueOfLegendsBrand.CONFLAGRATION_SPELL,
			LeagueOfLegendsBrand.PILLAR_OF_FLAME_SPELL, LeagueOfLegendsBrand.PYROCLASM_SPELL,
			LeagueOfLegendsBrand.SEAR_SPELL);

	private PillarOfFlameEntity pillarOfFlame;
	private boolean isBrand;
	private PlayerEntity player;

	public Brand(PlayerEntity player) {
		this.player = player;
	}

	public void tick() {
		if (!player.world.isRemote)
			if (!isBrand)
				ItemStackHelper.func_233534_a_(player.inventory, s -> getSpells().contains(s.getItem()), -1, false);
			else
				addSpellsToInv();
		destroyMap();
	}

	private void destroyMap() {
		World world = player.world;
		if (!isBrand())
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
		for (Item spell : getSpells()) {
			ItemStack stack = spell.getDefaultInstance();
			if (!player.inventory.hasItemStack(stack))
				player.addItemStackToInventory(stack);
		}
	}

	private ImmutableSet<Item> getSpells() {
		return SPELLS;
	}

	public PillarOfFlameEntity getPillarOfFlame() {
		return pillarOfFlame;
	}

	public void setPillarOfFlame(PillarOfFlameEntity pillarOfFlame) {
		this.pillarOfFlame = pillarOfFlame;
	}

	public boolean isBrand() {
		return isBrand;
	}

	public void setBrand(boolean value) {
		isBrand = value;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		compound.putBoolean("isBrand", isBrand());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		setBrand(nbt.getBoolean("isBrand"));
	}

	public static LazyOptional<Brand> get(Entity player) {
		return player.getCapability(CAPABILITY);
	}

	public static void sync(Entity entity) {
		get(entity).ifPresent(b -> {
			BrandMessage.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
					new BrandMessage(b.isBrand(), entity.getUniqueID()));
		});
	}

	public static void sync(Entity entity, ServerPlayerEntity reciever) {
		get(entity).ifPresent(b -> {
			BrandMessage.INSTANCE.send(PacketDistributor.PLAYER.with(() -> reciever),
					new BrandMessage(b.isBrand(), entity.getUniqueID()));
		});
	}
}
