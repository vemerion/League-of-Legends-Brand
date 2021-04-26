package mod.vemerion.leagueoflegendsbrand.init;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.entity.AblazedEntity;
import mod.vemerion.leagueoflegendsbrand.entity.InfectedCleaverEntity;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import mod.vemerion.leagueoflegendsbrand.entity.PyroclasmEntity;
import mod.vemerion.leagueoflegendsbrand.entity.SearEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {

	public static final EntityType<SearEntity> SEAR = null;
	public static final EntityType<PillarOfFlameEntity> PILLAR_OF_FLAME = null;
	public static final EntityType<PyroclasmEntity> PYROCLASM = null;
	public static final EntityType<AblazedEntity> ABLAZED = null;
	public static final EntityType<InfectedCleaverEntity> INFECTED_CLEAVER = null;

	@SubscribeEvent
	public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		IForgeRegistry<EntityType<?>> reg = event.getRegistry();

		EntityType<SearEntity> searEntity = EntityType.Builder
				.<SearEntity>create(SearEntity::new, EntityClassification.MISC).size(0.6f, 0.6f).build("");
		EntityType<PillarOfFlameEntity> pillarOfFlameEntity = EntityType.Builder
				.<PillarOfFlameEntity>create(PillarOfFlameEntity::new, EntityClassification.MISC).size(1, 1).build("");
		EntityType<PyroclasmEntity> pyroclasmEntity = EntityType.Builder
				.<PyroclasmEntity>create(PyroclasmEntity::new, EntityClassification.MISC).size(0.85f, 0.85f).build("");
		EntityType<AblazedEntity> ablazedEntity = EntityType.Builder
				.<AblazedEntity>create(AblazedEntity::new, EntityClassification.MISC).size(1, 1).build("");
		EntityType<InfectedCleaverEntity> infectedCleaver = EntityType.Builder
				.<InfectedCleaverEntity>create(InfectedCleaverEntity::new, EntityClassification.MISC).size(1, 1).build("");


		reg.register(Init.setup(searEntity, "sear"));
		reg.register(Init.setup(pillarOfFlameEntity, "pillar_of_flame"));
		reg.register(Init.setup(pyroclasmEntity, "pyroclasm"));
		reg.register(Init.setup(ablazedEntity, "ablazed"));
		reg.register(Init.setup(infectedCleaver, "infected_cleaver"));
	}

}
