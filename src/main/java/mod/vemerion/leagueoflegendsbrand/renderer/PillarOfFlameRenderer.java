package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import mod.vemerion.leagueoflegendsbrand.model.BrandParticleModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PillarOfFlameRenderer extends EntityRenderer<PillarOfFlameEntity> {
	private final BrandParticleModel model = new BrandParticleModel();
	public static final ResourceLocation TEXTURES = new ResourceLocation(LeagueOfLegendsBrand.MODID,
			"textures/brand_particle.png");

	public PillarOfFlameRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(PillarOfFlameEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		float ageInTicks = entityIn.ticksExisted + partialTicks;
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(getEntityTexture(entityIn)));
		Random random = new Random(0);
		for (int i = 0; i < 250; i++) {
			int interval = random.nextInt(10) + 10;
			float red = random.nextFloat() * 0.2f + 0.8f;
			float green = random.nextFloat() * 0.2f + 0.3f;
			float blue = 0;
			float direction = random.nextFloat() * 360;
			float radius = random.nextFloat() * 2f;
			Vec3d offset = Vec3d.fromPitchYaw(0, direction).scale(radius);
			matrixStackIn.push();

			float scale = Helper.lerpRepeat(ageInTicks / interval, 1, 0);
			double x = offset.getX();
			double y = -1 + offset.getY();
			double z = offset.getZ();

			if (ageInTicks > 14 && entityIn.canExplode()) {
				y += MathHelper.lerp((ageInTicks - 14 + random.nextFloat() * 4 - 2) / interval, 0, 4);
				scale = (float) MathHelper.clampedLerp(1, 0, (ageInTicks - 14 + random.nextFloat() * 4 - 2) / interval);
			}

			matrixStackIn.translate(x, y, z);
			matrixStackIn.translate(0, 1, 0);
			matrixStackIn.scale(scale, scale, scale);
			matrixStackIn.translate(0, -1, 0);

			model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1);
			matrixStackIn.pop();
		}
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(PillarOfFlameEntity entity) {
		return TEXTURES;
	}

}
