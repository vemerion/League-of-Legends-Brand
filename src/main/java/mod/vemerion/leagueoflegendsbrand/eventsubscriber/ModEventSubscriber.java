package mod.vemerion.leagueoflegendsbrand.eventsubscriber;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.capability.CompoundStorage;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.network.ChampionMessage;
import mod.vemerion.leagueoflegendsbrand.network.BurningAgonyMessage;
import mod.vemerion.leagueoflegendsbrand.network.BurningMessage;
import mod.vemerion.leagueoflegendsbrand.network.Network;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(Champions.class, new CompoundStorage<>(), () -> {
			throw new UnsupportedOperationException("You are not allowed to use default instance for this capability");
		});
		CapabilityManager.INSTANCE.register(Ablazed.class, new CompoundStorage<>(), () -> {
			throw new UnsupportedOperationException("You are not allowed to use default instance for this capability");
		});
		Network.INSTANCE.registerMessage(Network.index(), ChampionMessage.class, ChampionMessage::encode,
				ChampionMessage::decode, ChampionMessage::handle);
		Network.INSTANCE.registerMessage(Network.index(), BurningMessage.class, BurningMessage::encode,
				BurningMessage::decode, BurningMessage::handle);
		Network.INSTANCE.registerMessage(Network.index(), BurningAgonyMessage.class, BurningAgonyMessage::encode,
				BurningAgonyMessage::decode, BurningAgonyMessage::handle);

	}

}
