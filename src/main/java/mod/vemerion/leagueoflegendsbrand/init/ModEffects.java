package mod.vemerion.leagueoflegendsbrand.init;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.effect.ModEffect;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEffects {

	public static final ModEffect MASOCHISM = null;
	public static final ModEffect SADISM = null;

	@SubscribeEvent
	public static void registerEffect(RegistryEvent.Register<Effect> event) {
		IForgeRegistry<Effect> reg = event.getRegistry();

		reg.register(Init.setup(
				new ModEffect(EffectType.BENEFICIAL, Helper.color(160, 15, 255, 255)).addAttributesModifier(
						Attributes.ATTACK_DAMAGE, "31049902-a47b-433e-9f8a-212fc5feef61", 1, Operation.ADDITION),
				"masochism"));
		reg.register(Init.setup(new ModEffect(EffectType.BENEFICIAL, Helper.color(20, 175, 75, 255)).addAttributesModifier(
				Attributes.MOVEMENT_SPEED, "7c0587c5-86b7-4b5f-8d6b-6a170a6b1a02", 1.25, Operation.MULTIPLY_TOTAL), "sadism"));
	}
}
