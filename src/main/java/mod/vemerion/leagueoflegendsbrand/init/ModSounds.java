package mod.vemerion.leagueoflegendsbrand.init;

import mod.vemerion.leagueoflegendsbrand.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModSounds {

	public static final SoundEvent BURNING = null;
	public static final SoundEvent EXPLOSION = null;
	public static final SoundEvent FIREBALL = null;
	public static final SoundEvent FIREBALL_SPELL = null;
	public static final SoundEvent FIREBALL_WOOSH = null;

	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		SoundEvent burning_sound = new SoundEvent(new ResourceLocation(Main.MODID, "burning"));
		event.getRegistry().register(Init.setup(burning_sound, "burning"));
		SoundEvent explosion_sound = new SoundEvent(new ResourceLocation(Main.MODID, "explosion"));
		event.getRegistry().register(Init.setup(explosion_sound, "explosion"));
		SoundEvent fireball_sound = new SoundEvent(new ResourceLocation(Main.MODID, "fireball"));
		event.getRegistry().register(Init.setup(fireball_sound, "fireball"));
		SoundEvent fireball_spell = new SoundEvent(new ResourceLocation(Main.MODID, "fireball_spell"));
		event.getRegistry().register(Init.setup(fireball_spell, "fireball_spell"));
		SoundEvent fireball_woosh_sound = new SoundEvent(
				new ResourceLocation(Main.MODID, "fireball_woosh"));
		event.getRegistry().register(Init.setup(fireball_woosh_sound, "fireball_woosh"));
	}
}
