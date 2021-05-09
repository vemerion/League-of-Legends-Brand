package mod.vemerion.leagueoflegendsbrand.lootmodifier;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class SummonersRiftLootModifier extends LootModifier {

	private Item rift;

	public SummonersRiftLootModifier(ILootCondition[] conditionsIn, Item rift) {
		super(conditionsIn);
		this.rift = rift;
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		generatedLoot.add(rift.getDefaultInstance());
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<SummonersRiftLootModifier> {

		@Override
		public SummonersRiftLootModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
			Item item = JSONUtils.getItem(json, "item");
			return new SummonersRiftLootModifier(conditionsIn, item);
		}

		@Override
		public JsonObject write(SummonersRiftLootModifier instance) {
			JsonObject json = makeConditions(instance.conditions);
			json.addProperty("item", instance.rift.getRegistryName().toString());
			return json;
		}
	}

}
