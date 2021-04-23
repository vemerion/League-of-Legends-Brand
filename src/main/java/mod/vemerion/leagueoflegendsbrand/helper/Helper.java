package mod.vemerion.leagueoflegendsbrand.helper;

import net.minecraft.util.math.MathHelper;

public class Helper {
	public static float lerpRepeat(float value, float start, float stop) {
		return MathHelper.lerp(value % 1, start, stop);
	}

	public static int color(int r, int g, int b, int a) {
		a = (a << 24) & 0xFF000000;
		r = (r << 16) & 0x00FF0000;
		g = (g << 8) & 0x0000FF00;
		b &= 0x000000FF;

		return a | r | g | b;
	}

	public static int red(int color) {
		return (color >> 16) & 255;
	}

	public static int green(int color) {
		return (color >> 8) & 255;
	}

	public static int blue(int color) {
		return color & 255;
	}

	public static int alfa(int color) {
		return color >>> 24;
	}
}
