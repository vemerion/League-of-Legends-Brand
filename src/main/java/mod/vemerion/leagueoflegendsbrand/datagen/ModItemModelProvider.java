package mod.vemerion.leagueoflegendsbrand.datagen;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Main.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		// TODO: Hide spell items in third person
		simpleItem(ModItems.CONFLAGRATION);
		simpleItem(ModItems.PILLAR_OF_FLAME);
		simpleItem(ModItems.PYROCLASM);
		simpleItem(ModItems.SEAR);
		simpleItem(ModItems.SUMMONERS_RIFT_BRAND);
	}

	private void simpleItem(Item item) {
		String name = item.getRegistryName().getPath();
		singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(ITEM_FOLDER + "/" + name));
	}
}