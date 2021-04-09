package mod.vemerion.leagueoflegendsbrand.init;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.lootmodifier.SummonersRiftBrandLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModLootModifiers {

	public static final GlobalLootModifierSerializer<SummonersRiftBrandLootModifier> SUMMONERS_RIFT_BRAND = null;

	@SubscribeEvent
	public static void onRegisterModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
		IForgeRegistry<GlobalLootModifierSerializer<?>> reg = event.getRegistry();
		reg.register(Init.setup(new SummonersRiftBrandLootModifier.Serializer(), "summoners_rift_brand"));
	}
}
