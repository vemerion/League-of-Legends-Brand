package mod.vemerion.leagueoflegendsbrand.datagen;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.init.ModItems;
import mod.vemerion.leagueoflegendsbrand.init.ModLootModifiers;
import mod.vemerion.leagueoflegendsbrand.lootmodifier.SummonersRiftLootModifier;
import mod.vemerion.leagueoflegendsbrand.lootmodifier.lootcondition.FromLootTable;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

	public ModLootModifierProvider(DataGenerator gen) {
		super(gen, Main.MODID);
	}

	@Override
	protected void start() {
		add("summoners_rift_brand", ModLootModifiers.SUMMONERS_RIFT,
				new SummonersRiftLootModifier(new ILootCondition[] { RandomChance.builder(0.1f).build(),
						new FromLootTable(LootTables.CHESTS_NETHER_BRIDGE) }, ModItems.SUMMONERS_RIFT_BRAND));
		add("summoners_rift_mundo", ModLootModifiers.SUMMONERS_RIFT,
				new SummonersRiftLootModifier(
						new ILootCondition[] { RandomChance.builder(0.1f).build(),
								new FromLootTable(LootTables.CHESTS_WOODLAND_MANSION) },
						ModItems.SUMMONERS_RIFT_MUNDO));
	}

}
