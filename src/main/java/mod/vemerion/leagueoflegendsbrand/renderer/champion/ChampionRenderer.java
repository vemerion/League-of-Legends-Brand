package mod.vemerion.leagueoflegendsbrand.renderer.champion;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.leagueoflegendsbrand.champion.SpellKey;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.HandSide;

public abstract class ChampionRenderer {

	public boolean renderSpell(SpellKey key, HandSide side, float progress, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light, AbstractClientPlayerEntity player, float partialTicks) {
		switch (key) {
		case Q:
			return renderQ(side, progress, matrix, buffer, light, player, partialTicks);
		case W:
			return renderW(side, progress, matrix, buffer, light, player, partialTicks);
		case E:
			return renderE(side, progress, matrix, buffer, light, player, partialTicks);
		case R:
			return renderR(side, progress, matrix, buffer, light, player, partialTicks);
		default:
			return false;
		}
	}

	protected abstract boolean renderR(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer,
			int light, AbstractClientPlayerEntity player, float partialTicks);

	protected abstract boolean renderE(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer,
			int light, AbstractClientPlayerEntity player, float partialTicks);

	protected abstract boolean renderW(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer,
			int light, AbstractClientPlayerEntity player, float partialTicks);

	protected abstract boolean renderQ(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer,
			int light, AbstractClientPlayerEntity player, float partialTicks);

	public abstract void renderThirdPerson(AbstractClientPlayerEntity player, float yaw, float partialTicks,
			MatrixStack matrix, IRenderTypeBuffer buffer, int light);

	public abstract void renderHand(HandSide side, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks, float swingProgress, float equipProgress);

	protected void renderFirstPerson(AbstractClientPlayerEntity player, float partialTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {

	}
}
