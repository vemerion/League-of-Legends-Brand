package mod.vemerion.leagueoflegendsbrand.capability;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class BrandProvider implements ICapabilitySerializable<INBT> {

	private LazyOptional<Brand> instance = LazyOptional.of(LeagueOfLegendsBrand.BRAND_CAP::getDefaultInstance);

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return LeagueOfLegendsBrand.BRAND_CAP.orEmpty(cap, instance);
	}

	@Override
	public INBT serializeNBT() {
		return LeagueOfLegendsBrand.BRAND_CAP.getStorage().writeNBT(LeagueOfLegendsBrand.BRAND_CAP, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		LeagueOfLegendsBrand.BRAND_CAP.getStorage().readNBT(LeagueOfLegendsBrand.BRAND_CAP, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
	}

}
