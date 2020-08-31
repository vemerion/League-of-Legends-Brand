package mod.vemerion.leagueoflegendsbrand.capability;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class AblazedProvider implements ICapabilityProvider {

	private LazyOptional<Ablazed> instance = LazyOptional.of(LeagueOfLegendsBrand.ABLAZED_CAP::getDefaultInstance);

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return LeagueOfLegendsBrand.ABLAZED_CAP.orEmpty(cap, instance);
	}
}
