package mod.vemerion.leagueoflegendsbrand;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.entity.AblazedEntity;
import mod.vemerion.leagueoflegendsbrand.entity.ConflagrationEntity;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import mod.vemerion.leagueoflegendsbrand.entity.PyroclasmEntity;
import mod.vemerion.leagueoflegendsbrand.entity.SearEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod(LeagueOfLegendsBrand.MODID)
public class LeagueOfLegendsBrand {
	public static final String MODID = "league-of-legends-brand";
	
	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":sear_entity")
	public static EntityType<SearEntity> SEAR_ENTITY = null;
	
	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":pillar_of_flame_entity")
	public static EntityType<PillarOfFlameEntity> PILLAR_OF_FLAME_ENTITY = null;
	
	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":conflagration_entity")
	public static EntityType<ConflagrationEntity> CONFLAGRATION_ENTITY = null;
	
	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":pyroclasm_entity")
	public static EntityType<PyroclasmEntity> PYROCLASM_ENTITY = null;

	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":ablazed_entity")
	public static EntityType<AblazedEntity> ABLAZED_ENTITY = null;

	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":sear_spell")
	public static Item SEAR_SPELL = null;
	
	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":pillar_of_flame_spell")
	public static Item PILLAR_OF_FLAME_SPELL = null;

	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":conflagration_spell")
	public static Item CONFLAGRATION_SPELL = null;
	
	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":pyroclasm_spell")
	public static Item PYROCLASM_SPELL = null;
	
	@ObjectHolder(LeagueOfLegendsBrand.MODID + ":summoners_rift_brand_item")
	public static Item SUMMONERS_RIFT_BRAND_ITEM = null;
	
	@ObjectHolder("league-of-legends-brand:burning_sound")
	public static final SoundEvent BURNING_SOUND = null;

	@ObjectHolder("league-of-legends-brand:explosion_sound")
	public static final SoundEvent EXPLOSION_SOUND = null;

	@ObjectHolder("league-of-legends-brand:fireball_sound")
	public static final SoundEvent FIREBALL_SOUND = null;

	@ObjectHolder("league-of-legends-brand:fireball_spell")
	public static final SoundEvent FIREBALL_SPELL = null;

	@ObjectHolder("league-of-legends-brand:fireball_woosh_sound")
	public static final SoundEvent FIREBALL_WOOSH_SOUND = null;    

	@CapabilityInject(Ablazed.class)
	public static final Capability<Ablazed> ABLAZED_CAP = null;

}
