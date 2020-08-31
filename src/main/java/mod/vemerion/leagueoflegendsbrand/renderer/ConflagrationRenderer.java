package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.ConflagrationEntity;
import mod.vemerion.leagueoflegendsbrand.model.BrandParticleModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Pose;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ConflagrationRenderer extends EntityRenderer<ConflagrationEntity> {
	private final BrandParticleModel model = new BrandParticleModel();
	public static final ResourceLocation TEXTURES = new ResourceLocation(LeagueOfLegendsBrand.MODID,
			"textures/brand_particle.png");

	public ConflagrationRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(ConflagrationEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		float ageInTicks = entityIn.ticksExisted + partialTicks;
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(getEntityTexture(entityIn)));
		Random random = new Random(0);
		Entity owner = entityIn.getTarget();
		float width = owner != null ? owner.getSize(Pose.STANDING).width * 0.7f : 0.7f;
		
		for (int i = 0; i < 200; i++) {
			int interval = random.nextInt(12) + 12;
			float red = random.nextFloat() * 0.2f + 0.8f;
			float green = random.nextFloat() * 0.2f + 0.3f;
			float blue = 0;
			float direction = random.nextFloat() * 360;
			float radius = random.nextFloat() * width;
			Vec3d offset = Vec3d.fromPitchYaw(0, direction).scale(radius);
			matrixStackIn.push();

			float scale = (float) MathHelper.clampedLerp(2, 0, (ageInTicks + random.nextFloat() * 3) / interval);
			double x = offset.getX();
			double y = -1 + offset.getY() + MathHelper.lerp((ageInTicks + random.nextFloat() * 3) / interval, 0, 4);
			double z = offset.getZ();

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
	public ResourceLocation getEntityTexture(ConflagrationEntity entity) {
		return TEXTURES;
	}

}
