package mod.vemerion.leagueoflegendsbrand.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.leagueoflegendsbrand.entity.BrandBallEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Pose;
import net.minecraft.util.math.vector.Vector3d;

public class BrandBallRenderer extends CubeEntityRenderer<BrandBallEntity> {

	public BrandBallRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(BrandBallEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		float ageInTicks = entityIn.ticksExisted + partialTicks;
		Vector3d direction = entityIn.getMotion().normalize().inverse().scale(2);

		float size = entityIn.getSize(Pose.STANDING).width;
		int count = Math.min((int) (size * 700), 550);

		getCube().renderBall(count, size, direction, ageInTicks, matrixStackIn, bufferIn, packedLightIn);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
}
