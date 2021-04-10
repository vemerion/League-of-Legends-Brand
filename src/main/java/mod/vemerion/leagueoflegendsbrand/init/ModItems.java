package mod.vemerion.leagueoflegendsbrand.init;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.champion.Champion;
import mod.vemerion.leagueoflegendsbrand.champion.SpellKey;
import mod.vemerion.leagueoflegendsbrand.item.SpellItem;
import mod.vemerion.leagueoflegendsbrand.item.SummonersRiftBrandItem;
import net.minecraft.item.Item;
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
	public static final Item SUMMONERS_RIFT_BRAND = null;

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		reg.register(Init.setup(new SpellItem(SpellKey.Q, 8, Champion.BRAND), "sear"));
		reg.register(Init.setup(new SpellItem(SpellKey.W, 8, Champion.BRAND), "pillar_of_flame"));
		reg.register(Init.setup(new SpellItem(SpellKey.E, 8, Champion.BRAND), "conflagration"));
		reg.register(Init.setup(new SpellItem(SpellKey.R, 8, Champion.BRAND), "pyroclasm"));
		reg.register(Init.setup(new SummonersRiftBrandItem(), "summoners_rift_brand"));

	}
}
