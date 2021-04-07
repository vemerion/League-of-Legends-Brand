package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.AblazedEntity;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import mod.vemerion.leagueoflegendsbrand.model.BrandParticleModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Pose;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class AblazedRenderer extends EntityRenderer<AblazedEntity> {
	private final BrandParticleModel model = new BrandParticleModel();
	public static final ResourceLocation TEXTURES = new ResourceLocation(LeagueOfLegendsBrand.MODID,
			"textures/brand_particle.png");

	public AblazedRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(AblazedEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		float ageInTicks = entityIn.ticksExisted + partialTicks;
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(getEntityTexture(entityIn)));
		Random random = new Random(0);
		Entity owner = entityIn.getTarget();
		float width = owner != null ? owner.getSize(Pose.STANDING).width * 1.5f : 1;
		int count = ageInTicks > 40 ? 700 : 200;
		for (int i = 0; i < count; i++) {
			int interval = random.nextInt(8) + 8;
			float red = random.nextFloat() * 0.2f + 0.8f;
			float green = random.nextFloat() * 0.2f + 0.3f;
			float blue = 0;
			float radius = width * Helper.lerpRepeat((ageInTicks) / interval, 0, 1);
			if (ageInTicks > 40) {
				radius = 3.5f;
			}
			Vector3d offset = Vector3d.fromPitchYaw(random.nextFloat() * 360, random.nextFloat() * 360).scale(radius * Helper.lerpRepeat(ageInTicks / interval, 0.6f, 1.2f));
			matrixStackIn.push();

			float scale = Helper.lerpRepeat((ageInTicks) / interval, 2, 0);
			double x = offset.getX();
			double y = offset.getY();
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
	public ResourceLocation getEntityTexture(AblazedEntity entity) {
		return TEXTURES;
	}

}
