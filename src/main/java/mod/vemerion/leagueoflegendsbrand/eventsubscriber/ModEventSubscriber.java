package mod.vemerion.leagueoflegendsbrand.eventsubscriber;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.capability.Brand;
import mod.vemerion.leagueoflegendsbrand.capability.CompoundStorage;
import mod.vemerion.leagueoflegendsbrand.network.BrandMessage;
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
		CapabilityManager.INSTANCE.register(Brand.class, new CompoundStorage<>(), () -> {
			throw new UnsupportedOperationException("You are not allowed to use default instance for this capability");
		});
		CapabilityManager.INSTANCE.register(Ablazed.class, new CompoundStorage<>(), () -> {
			throw new UnsupportedOperationException("You are not allowed to use default instance for this capability");
		});
		Network.INSTANCE.registerMessage(Network.index(), BrandMessage.class, BrandMessage::encode,
				BrandMessage::decode, BrandMessage::handle);
		Network.INSTANCE.registerMessage(Network.index(), BurningMessage.class, BurningMessage::encode,
				BurningMessage::decode, BurningMessage::handle);
	}

}
