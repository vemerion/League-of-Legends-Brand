package mod.vemerion.leagueoflegendsbrand;

import mod.vemerion.leagueoflegendsbrand.renderer.AblazedRenderer;
import mod.vemerion.leagueoflegendsbrand.renderer.BrandBallRenderer;
import mod.vemerion.leagueoflegendsbrand.renderer.PillarOfFlameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = LeagueOfLegendsBrand.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterRenderer(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(LeagueOfLegendsBrand.SEAR_ENTITY,
				(renderManager) -> new BrandBallRenderer(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(LeagueOfLegendsBrand.PILLAR_OF_FLAME_ENTITY,
				(renderManager) -> new PillarOfFlameRenderer(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(LeagueOfLegendsBrand.PYROCLASM_ENTITY,
				(renderManager) -> new BrandBallRenderer(renderManager));
		RenderingRegistry.registerEntityRenderingHandler(LeagueOfLegendsBrand.ABLAZED_ENTITY,
				(renderManager) -> new AblazedRenderer(renderManager));
	}
}
