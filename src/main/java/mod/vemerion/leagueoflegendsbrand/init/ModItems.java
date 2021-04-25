package mod.vemerion.leagueoflegendsbrand.init;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.champion.Champion;
import mod.vemerion.leagueoflegendsbrand.champion.SpellKey;
import mod.vemerion.leagueoflegendsbrand.item.SpellItem;
import mod.vemerion.leagueoflegendsbrand.item.SummonersRiftItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModItems {

	public static final Item SEAR = null;
	public static final Item PILLAR_OF_FLAME = null;
	public static final Item CONFLAGRATION = null;
	public static final Item PYROCLASM = null;

	public static final Item INFECTED_CLEAVER = null;
	public static final Item BURNING_AGONY = null;
	public static final Item MASOCHISM = null;
	public static final Item SADISM = null;

	public static final Item SUMMONERS_RIFT_BRAND = null;
	public static final Item SUMMONERS_RIFT_MUNDO = null;

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		reg.register(Init.setup(new SpellItem(SpellKey.Q, 8, Champion.BRAND), "sear"));
		reg.register(Init.setup(new SpellItem(SpellKey.W, 8, Champion.BRAND), "pillar_of_flame"));
		reg.register(Init.setup(new SpellItem(SpellKey.E, 8, Champion.BRAND), "conflagration"));
		reg.register(Init.setup(new SpellItem(SpellKey.R, 8, Champion.BRAND), "pyroclasm"));

		reg.register(Init.setup(new SpellItem(SpellKey.Q, 5, Champion.MUNDO), "infected_cleaver"));
		reg.register(Init.setup(new SpellItem(SpellKey.W, 8, Champion.MUNDO), "burning_agony"));
		reg.register(Init.setup(new SpellItem(SpellKey.E, 0, Champion.MUNDO), "masochism"));
		reg.register(Init.setup(new SpellItem(SpellKey.R, 0, Champion.MUNDO), "sadism"));

		reg.register(Init.setup(new SummonersRiftItem(Champion.BRAND, Lazy.of(() -> ModSounds.BURNING),
				Lazy.of(() -> ModSounds.EXPLOSION)), "summoners_rift_brand"));
		reg.register(Init.setup(new SummonersRiftItem(Champion.MUNDO, null, Lazy.of(() -> ModSounds.SPRAY)),
				"summoners_rift_mundo"));

	}
}
