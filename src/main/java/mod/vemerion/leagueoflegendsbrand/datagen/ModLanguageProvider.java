package mod.vemerion.leagueoflegendsbrand.datagen;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.init.ModEntities;
import mod.vemerion.leagueoflegendsbrand.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen) {
		super(gen, Main.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add(ModItems.CONFLAGRATION, "Conflagration");
		add(ModItems.PILLAR_OF_FLAME, "Pillar of Flame");
		add(ModItems.PYROCLASM, "Pyroclasm");
		add(ModItems.SEAR, "Sear");
		
		add(ModItems.INFECTED_CLEAVER, "Infected Cleaver");
		add(ModItems.BURNING_AGONY, "Burning Agony");
		add(ModItems.MASOCHISM, "Masochism");
		add(ModItems.SADISM, "Sadism");
		
		add(ModItems.SUMMONERS_RIFT_BRAND, "Summoner's Rift: Brand");
		add(ModItems.SUMMONERS_RIFT_MUNDO, "Summoner's Rift: Mundo");
		
		add(ModEntities.ABLAZED, "Ablazed");
		add(ModEntities.PILLAR_OF_FLAME, "Pillar of Flame");
		add(ModEntities.PYROCLASM, "Pyroclasm");
		add(ModEntities.SEAR, "Sear");
	}
}