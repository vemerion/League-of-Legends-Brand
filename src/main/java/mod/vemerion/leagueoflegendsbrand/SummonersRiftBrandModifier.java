package mod.vemerion.leagueoflegendsbrand;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class SummonersRiftBrandModifier extends LootModifier {

	protected SummonersRiftBrandModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		BlockPos pos = new BlockPos(context.get(LootParameters.field_237457_g_));
		TileEntity te = pos == null ? null : context.getWorld().getTileEntity(pos);
		if (te instanceof LockableLootTileEntity && context.getWorld().getDimensionKey() == World.THE_NETHER) {
			generatedLoot.add(new ItemStack(LeagueOfLegendsBrand.SUMMONERS_RIFT_BRAND_ITEM));
		}
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<SummonersRiftBrandModifier> {

		@Override
		public SummonersRiftBrandModifier read(ResourceLocation name, JsonObject object,
				ILootCondition[] conditionsIn) {
			return new SummonersRiftBrandModifier(conditionsIn);
		}

		@Override
		public JsonObject write(SummonersRiftBrandModifier instance) {
			return makeConditions(instance.conditions);
		}
	}

}
