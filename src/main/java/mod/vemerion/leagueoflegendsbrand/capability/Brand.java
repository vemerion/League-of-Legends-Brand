package mod.vemerion.leagueoflegendsbrand.capability;

import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;

public class Brand {
	private PillarOfFlameEntity pillarOfFlame;
	private boolean isBrand;

	public PillarOfFlameEntity getPillarOfFlame() {
		return pillarOfFlame;
	}

	public void setPillarOfFlame(PillarOfFlameEntity pillarOfFlame) {
		this.pillarOfFlame = pillarOfFlame;
	}
	
	
	public boolean isBrand() {
		return isBrand;
	}
	
	public void setBrand(boolean value) {
		isBrand = value;
	}
	
}
