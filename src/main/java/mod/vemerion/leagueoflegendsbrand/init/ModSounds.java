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
	public static final SoundEvent SPRAY = null;
	public static final SoundEvent INFECTED_CLEAVER_IMPACT = null;
	public static final SoundEvent INFECTED_CLEAVER_THROW = null;
	public static final SoundEvent MASOCHISM = null;
	public static final SoundEvent SADISM = null;    

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
		SoundEvent spray = new SoundEvent(new ResourceLocation(Main.MODID, "spray"));
		event.getRegistry().register(Init.setup(spray, "spray"));  
		SoundEvent infected_cleaver_impact = new SoundEvent(new ResourceLocation(Main.MODID, "infected_cleaver_impact"));
		event.getRegistry().register(Init.setup(infected_cleaver_impact, "infected_cleaver_impact"));
		SoundEvent infected_cleaver_throw = new SoundEvent(new ResourceLocation(Main.MODID, "infected_cleaver_throw"));
		event.getRegistry().register(Init.setup(infected_cleaver_throw, "infected_cleaver_throw"));
		SoundEvent masochism = new SoundEvent(new ResourceLocation(Main.MODID, "masochism"));
		event.getRegistry().register(Init.setup(masochism, "masochism"));
		SoundEvent sadism = new SoundEvent(new ResourceLocation(Main.MODID, "sadism"));
		event.getRegistry().register(Init.setup(sadism, "sadism"));   
	}
}
