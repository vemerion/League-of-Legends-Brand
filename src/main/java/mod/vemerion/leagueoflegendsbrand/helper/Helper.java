package mod.vemerion.leagueoflegendsbrand.helper;

import net.minecraft.util.math.MathHelper;

public class Helper {
	public static float lerpRepeat(float value, float start, float stop) {
		return MathHelper.lerp(value % 1, start, stop);
	}
}
