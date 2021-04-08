package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.entity.BrandBallEntity;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
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
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(getCube().getRenderType(getEntityTexture(entityIn)));
		Random random = new Random(0);
		Vector3d direction = entityIn.getMotion().normalize().inverse().scale(2);
		
		float size = entityIn.getSize(Pose.STANDING).width;
		float count = Math.min(size * 700, 550);

		for (int i = 0; i < count; i++) {
			int interval = random.nextInt(6) + 6;
			float offsetYaw = random.nextFloat() * 360;
			float offsetPitch = random.nextFloat() * 360;
			float radius = random.nextFloat() * size / 2;
			Vector3d offset = Vector3d.fromPitchYaw(offsetPitch, offsetYaw).scale(radius);

			float scale = Helper.lerpRepeat(ageInTicks / interval, size, 0);
			double x = offset.getX() + Helper.lerpRepeat(ageInTicks / interval, 0, (float) direction.getX());
			double y = -0.5 + offset.getY() + Helper.lerpRepeat(ageInTicks / interval, 0, (float) direction.getY());
			double z = offset.getZ() + Helper.lerpRepeat(ageInTicks / interval, 0, (float) direction.getZ());

			getCube().render(random, ageInTicks, scale, new Vector3d(x, y, z), matrixStackIn, ivertexbuilder, packedLightIn);
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
}
