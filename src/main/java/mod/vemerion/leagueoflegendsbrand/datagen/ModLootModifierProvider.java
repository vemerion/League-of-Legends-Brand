package mod.vemerion.leagueoflegendsbrand.datagen;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.init.ModLootModifiers;
import mod.vemerion.leagueoflegendsbrand.lootmodifier.SummonersRiftBrandLootModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

	public ModLootModifierProvider(DataGenerator gen) {
		super(gen, Main.MODID);
	}

	@Override
	protected void start() {
		add("summoners_rift_brand", ModLootModifiers.SUMMONERS_RIFT_BRAND,
				new SummonersRiftBrandLootModifier(new ILootCondition[] { RandomChance.builder(0.1f).build() }));
	}

}
