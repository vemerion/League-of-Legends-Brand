package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class PillarOfFlameRenderer extends CubeEntityRenderer<PillarOfFlameEntity> {
	public PillarOfFlameRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(PillarOfFlameEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		float ageInTicks = entityIn.ticksExisted + partialTicks;
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(getCube().getRenderType(getEntityTexture(entityIn)));
		Random random = new Random(0);
		for (int i = 0; i < 250; i++) {
			int interval = random.nextInt(10) + 10;
			float direction = random.nextFloat() * 360;
			float radius = random.nextFloat() * 2f;
			Vector3d offset = Vector3d.fromPitchYaw(0, direction).scale(radius).add(0, -1, 0);

			float scale = Helper.lerpRepeat(ageInTicks / interval, 1, 0);

			if (ageInTicks > 14 && entityIn.canExplode()) {
				offset = offset.add(0, MathHelper.lerp((ageInTicks - 14 + random.nextFloat() * 4 - 2) / interval, 0, 4),
						0);
				scale = (float) MathHelper.clampedLerp(1, 0, (ageInTicks - 14 + random.nextFloat() * 4 - 2) / interval);
			}

			getCube().render(random, ageInTicks, scale, offset, matrixStackIn, ivertexbuilder, packedLightIn);
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
}
