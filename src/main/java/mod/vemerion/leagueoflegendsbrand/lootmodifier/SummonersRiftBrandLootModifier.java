package mod.vemerion.leagueoflegendsbrand.lootmodifier;

import java.util.List;

import com.google.gson.JsonObject;

import mod.vemerion.leagueoflegendsbrand.init.ModItems;
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

public class SummonersRiftBrandLootModifier extends LootModifier {

	protected SummonersRiftBrandLootModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		BlockPos pos = new BlockPos(context.get(LootParameters.field_237457_g_));
		TileEntity te = pos == null ? null : context.getWorld().getTileEntity(pos);
		if (te instanceof LockableLootTileEntity && context.getWorld().getDimensionKey() == World.THE_NETHER) {
			generatedLoot.add(new ItemStack(ModItems.SUMMONERS_RIFT_BRAND));
		}
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<SummonersRiftBrandLootModifier> {

		@Override
		public SummonersRiftBrandLootModifier read(ResourceLocation name, JsonObject object,
				ILootCondition[] conditionsIn) {
			return new SummonersRiftBrandLootModifier(conditionsIn);
		}

		@Override
		public JsonObject write(SummonersRiftBrandLootModifier instance) {
			return makeConditions(instance.conditions);
		}
	}

}
