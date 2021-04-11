package mod.vemerion.leagueoflegendsbrand.datagen;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Main.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		spellItem(ModItems.CONFLAGRATION);
		spellItem(ModItems.PILLAR_OF_FLAME);
		spellItem(ModItems.PYROCLASM);
		spellItem(ModItems.SEAR);
		simpleItem(ModItems.SUMMONERS_RIFT_BRAND);
	}

	private ItemModelBuilder simpleItem(Item item) {
		String name = item.getRegistryName().getPath();
		return singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(ITEM_FOLDER + "/" + name));
	}

	private ItemModelBuilder spellItem(Item item) {
		return simpleItem(item).transforms().transform(Perspective.THIRDPERSON_LEFT).scale(0).end()
				.transform(Perspective.THIRDPERSON_RIGHT).scale(0).end().end();
	}
}