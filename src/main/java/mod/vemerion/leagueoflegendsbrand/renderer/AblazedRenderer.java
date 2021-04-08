package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.entity.AblazedEntity;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Pose;
import net.minecraft.util.math.vector.Vector3d;

public class AblazedRenderer extends CubeEntityRenderer<AblazedEntity> {

	public AblazedRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(AblazedEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		float ageInTicks = entityIn.ticksExisted + partialTicks;
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(getCube().getRenderType(getEntityTexture(entityIn)));
		Random random = new Random(0);
		Entity owner = entityIn.getTarget();
		float width = owner != null ? owner.getSize(Pose.STANDING).width * 1.5f : 1;
		int count = ageInTicks > 40 ? 700 : 200;
		for (int i = 0; i < count; i++) {
			int interval = random.nextInt(8) + 8;
			float radius = width * Helper.lerpRepeat((ageInTicks) / interval, 0, 1);
			if (ageInTicks > 40) {
				radius = 3.5f;
			}
			Vector3d offset = Vector3d.fromPitchYaw(random.nextFloat() * 360, random.nextFloat() * 360).scale(radius * Helper.lerpRepeat(ageInTicks / interval, 0.6f, 1.2f));

			float scale = Helper.lerpRepeat((ageInTicks) / interval, 2, 0);

			getCube().render(random, ageInTicks, scale, offset, matrixStackIn, ivertexbuilder, packedLightIn);
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}
}
