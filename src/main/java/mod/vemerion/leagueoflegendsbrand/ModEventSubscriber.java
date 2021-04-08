package mod.vemerion.leagueoflegendsbrand;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.capability.Brand;
import mod.vemerion.leagueoflegendsbrand.capability.BrandMessage;
import mod.vemerion.leagueoflegendsbrand.capability.CompoundStorage;
import mod.vemerion.leagueoflegendsbrand.entity.AblazedEntity;
import mod.vemerion.leagueoflegendsbrand.entity.ConflagrationEntity;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import mod.vemerion.leagueoflegendsbrand.entity.PyroclasmEntity;
import mod.vemerion.leagueoflegendsbrand.entity.SearEntity;
import mod.vemerion.leagueoflegendsbrand.item.ConflagrationSpell;
import mod.vemerion.leagueoflegendsbrand.item.PillarOfFlameSpell;
import mod.vemerion.leagueoflegendsbrand.item.PyroclasmSpell;
import mod.vemerion.leagueoflegendsbrand.item.SearSpell;
import mod.vemerion.leagueoflegendsbrand.item.SummonersRiftBrandItem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = LeagueOfLegendsBrand.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(setup(new SearSpell(), "sear_spell"));
		event.getRegistry().register(setup(new PillarOfFlameSpell(), "pillar_of_flame_spell"));
		event.getRegistry().register(setup(new ConflagrationSpell(), "conflagration_spell"));
		event.getRegistry().register(setup(new PyroclasmSpell(), "pyroclasm_spell"));
		event.getRegistry().register(setup(new SummonersRiftBrandItem(), "summoners_rift_brand_item"));

	}

	@SubscribeEvent
	public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		EntityType<SearEntity> searEntity = EntityType.Builder
				.<SearEntity>create(SearEntity::new, EntityClassification.MISC).size(0.6f, 0.6f).build("sear_entity");
		EntityType<PillarOfFlameEntity> pillarOfFlameEntity = EntityType.Builder
				.<PillarOfFlameEntity>create(PillarOfFlameEntity::new, EntityClassification.MISC).size(1, 1)
				.build("pillar_of_flame_entity");
		EntityType<ConflagrationEntity> conflagrationEntity = EntityType.Builder
				.<ConflagrationEntity>create(ConflagrationEntity::new, EntityClassification.MISC).size(1, 1)
				.build("conflagration_entity");
		EntityType<PyroclasmEntity> pyroclasmEntity = EntityType.Builder
				.<PyroclasmEntity>create(PyroclasmEntity::new, EntityClassification.MISC).size(0.85f, 0.85f)
				.build("pyroclasm_entity");
		EntityType<AblazedEntity> ablazedEntity = EntityType.Builder
				.<AblazedEntity>create(AblazedEntity::new, EntityClassification.MISC).size(1, 1)
				.build("ablazed_entity");

		event.getRegistry().register(setup(searEntity, "sear_entity"));
		event.getRegistry().register(setup(pillarOfFlameEntity, "pillar_of_flame_entity"));
		event.getRegistry().register(setup(conflagrationEntity, "conflagration_entity"));
		event.getRegistry().register(setup(pyroclasmEntity, "pyroclasm_entity"));
		event.getRegistry().register(setup(ablazedEntity, "ablazed_entity"));
	}

	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		SoundEvent burning_sound = new SoundEvent(new ResourceLocation(LeagueOfLegendsBrand.MODID, "burning_sound"));
		event.getRegistry().register(setup(burning_sound, "burning_sound"));
		SoundEvent explosion_sound = new SoundEvent(
				new ResourceLocation(LeagueOfLegendsBrand.MODID, "explosion_sound"));
		event.getRegistry().register(setup(explosion_sound, "explosion_sound"));
		SoundEvent fireball_sound = new SoundEvent(new ResourceLocation(LeagueOfLegendsBrand.MODID, "fireball_sound"));
		event.getRegistry().register(setup(fireball_sound, "fireball_sound"));
		SoundEvent fireball_spell = new SoundEvent(new ResourceLocation(LeagueOfLegendsBrand.MODID, "fireball_spell"));
		event.getRegistry().register(setup(fireball_spell, "fireball_spell"));
		SoundEvent fireball_woosh_sound = new SoundEvent(
				new ResourceLocation(LeagueOfLegendsBrand.MODID, "fireball_woosh_sound"));
		event.getRegistry().register(setup(fireball_woosh_sound, "fireball_woosh_sound"));
	}

	@SubscribeEvent
	public static void onRegisterModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
		event.getRegistry()
				.register(setup(new SummonersRiftBrandModifier.Serializer(), "summoners_rift_brand_dungeon_loot"));
	}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(Brand.class, new CompoundStorage<>(), () -> {
			throw new UnsupportedOperationException("You are not allowed to use default instance for this capability");
		});
		CapabilityManager.INSTANCE.register(Ablazed.class, new CompoundStorage<>(), () -> {
			throw new UnsupportedOperationException("You are not allowed to use default instance for this capability");
		});
		BrandMessage.INSTANCE.registerMessage(0, BrandMessage.class, BrandMessage::encode, BrandMessage::decode,
				BrandMessage::handle);

	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(LeagueOfLegendsBrand.MODID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}

}
