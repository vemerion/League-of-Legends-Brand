package mod.vemerion.leagueoflegendsbrand;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class SummonersRiftBrandModifier extends LootModifier {

	protected SummonersRiftBrandModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		BlockPos pos = context.get(LootParameters.POSITION);
		TileEntity te = pos == null ? null : context.getWorld().getTileEntity(pos);
		if (te instanceof LockableLootTileEntity
				&& context.getWorld().dimension.getType() == DimensionType.THE_NETHER) {
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
	}

}
