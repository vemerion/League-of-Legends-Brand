package mod.vemerion.leagueoflegendsbrand.init;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.item.ConflagrationSpell;
import mod.vemerion.leagueoflegendsbrand.item.PillarOfFlameSpell;
import mod.vemerion.leagueoflegendsbrand.item.ProjectileSpell;
import mod.vemerion.leagueoflegendsbrand.item.SummonersRiftBrandItem;
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
	public static final Item SUMMONERS_RIFT_BRAND = null;

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		reg.register(Init.setup(new ProjectileSpell(Lazy.of(() -> ModEntities.SEAR), 0.5f, 140,
				Lazy.of(() -> ModSounds.FIREBALL)), "sear"));
		reg.register(Init.setup(new PillarOfFlameSpell(), "pillar_of_flame"));
		reg.register(Init.setup(new ConflagrationSpell(), "conflagration"));
		reg.register(Init.setup(new ProjectileSpell(Lazy.of(() -> ModEntities.PYROCLASM), 0.7f, 1000,
				Lazy.of(() -> ModSounds.FIREBALL_WOOSH)), "pyroclasm"));
		reg.register(Init.setup(new SummonersRiftBrandItem(), "summoners_rift_brand"));

	}
}
