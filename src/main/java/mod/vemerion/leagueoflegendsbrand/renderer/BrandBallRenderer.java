package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.BrandBallEntity;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import mod.vemerion.leagueoflegendsbrand.model.BrandParticleModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Pose;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class BrandBallRenderer extends EntityRenderer<BrandBallEntity> {
	private final BrandParticleModel model = new BrandParticleModel();
	public static final ResourceLocation TEXTURES = new ResourceLocation(LeagueOfLegendsBrand.MODID,
			"textures/brand_particle.png");

	public BrandBallRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(BrandBallEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		float ageInTicks = entityIn.ticksExisted + partialTicks;
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(getEntityTexture(entityIn)));
		Random random = new Random(0);
		Vec3d direction = entityIn.getMotion().normalize().inverse().scale(2);
		
		float size = entityIn.getSize(Pose.STANDING).width;
		float count = Math.min(size * 700, 550);

		for (int i = 0; i < count; i++) {
			int interval = random.nextInt(6) + 6;
			float red = random.nextFloat() * 0.2f + 0.8f;
			float green = random.nextFloat() * 0.2f + 0.3f;
			float blue = 0;
			float offsetYaw = random.nextFloat() * 360;
			float offsetPitch = random.nextFloat() * 360;
			float radius = random.nextFloat() * size / 2;
			Vec3d offset = Vec3d.fromPitchYaw(offsetPitch, offsetYaw).scale(radius);
			matrixStackIn.push();

			float scale = Helper.lerpRepeat(ageInTicks / interval, size, 0);
			double x = offset.getX() + Helper.lerpRepeat(ageInTicks / interval, 0, (float) direction.getX());
			double y = -0.5 + offset.getY() + Helper.lerpRepeat(ageInTicks / interval, 0, (float) direction.getY());
			double z = offset.getZ() + Helper.lerpRepeat(ageInTicks / interval, 0, (float) direction.getZ());

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
	public ResourceLocation getEntityTexture(BrandBallEntity entity) {
		return TEXTURES;
	}

}
