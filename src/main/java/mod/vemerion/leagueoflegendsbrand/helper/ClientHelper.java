package mod.vemerion.leagueoflegendsbrand.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;

public class ClientHelper {
	public static float toRad(double deg) {
		return (float) Math.toRadians(deg);
	}

	public static Hand leftHand(PlayerEntity player) {
		return player.getPrimaryHand() == HandSide.LEFT ? Hand.MAIN_HAND : Hand.OFF_HAND;
	}
}
