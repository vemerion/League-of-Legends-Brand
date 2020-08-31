package mod.vemerion.leagueoflegendsbrand.capability;

import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class BrandStorage implements IStorage<Brand> {

	@Override
	public INBT writeNBT(Capability<Brand> capability, Brand instance, Direction side) {
		return ByteNBT.valueOf(instance.isBrand());
		
	}

	@Override
	public void readNBT(Capability<Brand> capability, Brand instance, Direction side, INBT nbt) {
		instance.setBrand(((ByteNBT)nbt).getByte() == 1);
	}

}
