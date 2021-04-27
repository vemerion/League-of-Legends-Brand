package mod.vemerion.leagueoflegendsbrand.renderer.champion;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Quaternion;

// Convenience class for champions with a human-like shape
public abstract class HumanRenderer extends PlayerRenderer implements CustomRenderer {

	public HumanRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	protected void preRenderArm(HandSide side, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn, AbstractClientPlayerEntity playerIn, float partialTicks) {
		PlayerModel<AbstractClientPlayerEntity> model = this.getEntityModel();
		model.rightArmPose = BipedModel.ArmPose.EMPTY;
		model.leftArmPose = BipedModel.ArmPose.EMPTY;
		model.swingProgress = 0.0F;
		model.isSneak = false;
		model.swimAnimation = 0.0F;
		model.setRotationAngles(playerIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	}

	protected void renderArm(HandSide side, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn, AbstractClientPlayerEntity playerIn, float partialTicks) {
		ModelRenderer arm = side == HandSide.LEFT ? entityModel.bipedLeftArm : entityModel.bipedRightArm;
		preRenderArm(side, matrixStackIn, bufferIn, combinedLightIn, playerIn, partialTicks);
		arm.rotateAngleX = 0.0F;
		arm.showModel = true;
		arm.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(getEntityTexture(playerIn))),
				combinedLightIn, OverlayTexture.NO_OVERLAY);
	}
	
	public void renderHand(HandSide side, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks, float swingProgress, float equipProgress) {
		equipProgress = swingProgress > 0.01 ? 0 : equipProgress;
		float offset = side == HandSide.RIGHT ? 1 : -1;
		matrix.push();

		matrix.translate(0, 0, -1);
		matrix.rotate(
				new Quaternion(-45 - swingProgress * 100, offset * (20 + swingProgress * 100), -20 * offset, true));
		matrix.translate(offset * 1.2, -0.7 - equipProgress + swingProgress * 2.5, -swingProgress * 2);
		renderArm(side, matrix, buffer, light, player, partialTicks);
		matrix.pop();
	}
}
