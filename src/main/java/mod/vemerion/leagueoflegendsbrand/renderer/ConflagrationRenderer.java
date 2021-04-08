package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.entity.ConflagrationEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Pose;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class ConflagrationRenderer extends CubeEntityRenderer<ConflagrationEntity> {

	public ConflagrationRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(ConflagrationEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		float ageInTicks = entityIn.ticksExisted + partialTicks;
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(getCube().getRenderType(getEntityTexture(entityIn)));
		Random random = new Random(0);
		Entity owner = entityIn.getTarget();
		float width = owner != null ? owner.getSize(Pose.STANDING).width * 0.7f : 0.7f;

		for (int i = 0; i < 200; i++) {
			int interval = random.nextInt(12) + 12;
			float direction = random.nextFloat() * 360;
			float radius = random.nextFloat() * width;
			Vector3d offset = Vector3d.fromPitchYaw(0, direction).scale(radius);

			float scale = (float) MathHelper.clampedLerp(2, 0, (ageInTicks + random.nextFloat() * 3) / interval);
			double x = offset.getX();
			double y = -1 + offset.getY() + MathHelper.lerp((ageInTicks + random.nextFloat() * 3) / interval, 0, 4);
			double z = offset.getZ();

			getCube().render(random, ageInTicks, scale, new Vector3d(x, y, z), matrixStackIn, ivertexbuilder,
					packedLightIn);
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
}
