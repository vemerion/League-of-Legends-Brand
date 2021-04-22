package mod.vemerion.leagueoflegendsbrand.init;

import mod.vemerion.leagueoflegendsbrand.Main;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModParticles {
	
	public static final BasicParticleType BLEED = null;

	@SubscribeEvent
	public static void registerParticle(RegistryEvent.Register<ParticleType<?>> event) {
		IForgeRegistry<ParticleType<?>> reg = event.getRegistry();
		reg.register(Init.setup(new BasicParticleType(true), "bleed"));
	}
}
