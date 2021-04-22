package mod.vemerion.leagueoflegendsbrand.effect;

import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class MasochismEffect extends Effect {

	public MasochismEffect() {
		super(EffectType.BENEFICIAL, Helper.color(160, 15, 255, 255));
		this.addAttributesModifier(Attributes.ATTACK_DAMAGE, "31049902-a47b-433e-9f8a-212fc5feef61", 1,
				Operation.ADDITION);
	}
}
