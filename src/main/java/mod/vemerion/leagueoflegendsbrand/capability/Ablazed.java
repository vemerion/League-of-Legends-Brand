package mod.vemerion.leagueoflegendsbrand.capability;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.AblazedEntity;
import net.minecraft.entity.LivingEntity;

public class Ablazed {
	private int count;
	private int timer;
	
	public int getAblazed() {
		return count;
	}
	
	public void setAblazed(int i) {
		if (i < 0)
			throw new IllegalArgumentException("Ablazed count can not be negative");
		count = i;
		if (count != 0)
			timer = 80;
	}
	
	public void incAblazed() {
		count++;
		timer = 80;
	}
	
	
	// Should only be called on server
	public void tick(LivingEntity entity) {
		timer--;
		
		if (count > 0 && entity.getFireTimer() <= 1) {
			entity.setFire(1);
		}
		
		if (timer == 0) {
			count = Math.max(0, count - 1);
		}
		
		if (count == 3) {
			count = 1;
			timer = 80;
			AblazedEntity ablazed = new AblazedEntity(LeagueOfLegendsBrand.ABLAZED_ENTITY, entity.world, entity);
			ablazed.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
			entity.world.addEntity(ablazed);
		}
	}
}
