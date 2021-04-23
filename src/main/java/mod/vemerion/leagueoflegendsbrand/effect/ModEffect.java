package mod.vemerion.leagueoflegendsbrand.effect;

import mod.vemerion.leagueoflegendsbrand.init.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class ModEffect extends Effect {

	public ModEffect(EffectType type, int color) {
		super(type, color);
	}

	@Override
	public void performEffect(LivingEntity entity, int amplifier) {
		if (this == ModEffects.SADISM && entity.getHealth() < entity.getMaxHealth()) {
			entity.heal(1);
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		if (this == ModEffects.SADISM) {
			int interval = 20 >> amplifier;
			return interval <= 0 || duration % interval == 0;
		}
		return false;
	}
}
