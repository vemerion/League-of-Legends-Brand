package mod.vemerion.leagueoflegendsbrand.lootmodifier.lootcondition;

import mod.vemerion.leagueoflegendsbrand.Main;
import net.minecraft.loot.LootConditionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootConditions {
	
	protected static LootConditionType FROM_LOOT_TABLE;


	public static void register() {
		FROM_LOOT_TABLE = Registry.register(Registry.LOOT_CONDITION_TYPE,
				new ResourceLocation(Main.MODID, "from_loot_table"),
				new LootConditionType(new FromLootTable.Serializer()));

	}
}
