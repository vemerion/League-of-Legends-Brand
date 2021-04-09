package mod.vemerion.leagueoflegendsbrand.eventsubscriber;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.init.ModEntities;
import mod.vemerion.leagueoflegendsbrand.renderer.AblazedRenderer;
import mod.vemerion.leagueoflegendsbrand.renderer.BrandBallRenderer;
import mod.vemerion.leagueoflegendsbrand.renderer.PillarOfFlameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterRenderer(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.SEAR,
				(renderManager) -> new BrandBallRenderer(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.PILLAR_OF_FLAME,
				(renderManager) -> new PillarOfFlameRenderer(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.PYROCLASM,
				(renderManager) -> new BrandBallRenderer(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.ABLAZED,
				(renderManager) -> new AblazedRenderer(renderManager));
	}
}
