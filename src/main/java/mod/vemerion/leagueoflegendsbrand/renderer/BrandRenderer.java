package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import mod.vemerion.leagueoflegendsbrand.model.BrandModel;
import mod.vemerion.leagueoflegendsbrand.model.BrandParticleModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class BrandRenderer extends PlayerRenderer {
	public static final ResourceLocation TEXTURES = new ResourceLocation(LeagueOfLegendsBrand.MODID,
			"textures/entity/brand.png");

	public BrandRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		entityModel = new BrandModel(0);
		addLayer(new GlowingBrandLayer(this));
	}

	@Override
	public ResourceLocation getEntityTexture(AbstractClientPlayerEntity entity) {
		return TEXTURES;
	}

	@Override
	public void render(AbstractClientPlayerEntity entityIn, float entityYaw, float partialTicks,
			MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	private void preRenderArm(AbstractClientPlayerEntity playerIn) {
		PlayerModel<AbstractClientPlayerEntity> model = this.getEntityModel();
		model.rightArmPose = BipedModel.ArmPose.EMPTY;
		model.swingProgress = 0.0F;
		model.isSneak = false;
		model.swimAnimation = 0.0F;
		model.setRotationAngles(playerIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	}

	// Only for rendering arm without holding item
	public void renderRightArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, float partialTicks) {
		preRenderArm(playerIn);
		entityModel.bipedRightArm.rotateAngleX = 0.0F;
		matrixStackIn.push();
		matrixStackIn.translate(-0.35, -0.35, 0.2);
		renderBurning(35, playerIn, matrixStackIn, bufferIn, partialTicks, combinedLightIn);
		matrixStackIn.pop();
		entityModel.bipedRightArm.render(matrixStackIn,
				bufferIn.getBuffer(RenderType.getEntitySolid(getEntityTexture(playerIn))), combinedLightIn,
				OverlayTexture.NO_OVERLAY);

	}

	public void renderLeftArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, float partialTicks) {
		preRenderArm(playerIn);
		entityModel.bipedLeftArm.rotateAngleX = 0.0F;
		matrixStackIn.push();
		matrixStackIn.translate(0.35, -0.35, 0.2);
		renderBurning(35, playerIn, matrixStackIn, bufferIn, partialTicks, combinedLightIn);
		matrixStackIn.pop();
		entityModel.bipedLeftArm.render(matrixStackIn,
				bufferIn.getBuffer(RenderType.getEntitySolid(getEntityTexture(playerIn))), combinedLightIn,
				OverlayTexture.NO_OVERLAY);

	}

	public void renderSear(float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		matrix.push();
		matrix.rotate(new Quaternion(-70, -5, progress * 20, true));
		matrix.translate(1 - progress * 0.25, 0.2, -0.7);
		renderRightArm(matrix, buffer, light, player, partialTicks);
		matrix.pop();
		matrix.push();
		matrix.rotate(new Quaternion(-70, 5, -progress * 20, true));
		matrix.translate(-1 + progress * 0.25, 0.2, -0.7);
		renderLeftArm(matrix, buffer, light, player, partialTicks);
		matrix.pop();
	}

	public void renderPillarOfFlame(float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		matrix.push();
		matrix.rotate(new Quaternion(-90 * progress, -5, 20 - progress * 20, true));
		matrix.translate(0.5 + progress * 1, 0, 0.3 - 2 * progress);
		renderRightArm(matrix, buffer, light, player, partialTicks);
		matrix.pop();
		matrix.push();
		matrix.rotate(new Quaternion(-90 * progress, 5, -20 + progress * 20, true));
		matrix.translate(-0.5 - progress * 1, 0, 0.3 - 2 * progress);

		renderLeftArm(matrix, buffer, light, player, partialTicks);
		matrix.pop();
	}

	public void renderConflagration(float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		matrix.push();
		matrix.rotate(new Quaternion(-70, -5, 20 - progress * 90, true));
		matrix.translate(0.75 + progress * 0.25, 0.2, -0.7);
		renderRightArm(matrix, buffer, light, player, partialTicks);
		matrix.pop();
		matrix.push();
		matrix.rotate(new Quaternion(-70, 5, -20 + progress * 90, true));
		matrix.translate(-0.75 - progress * 0.25, 0.2, -0.7);

		renderLeftArm(matrix, buffer, light, player, partialTicks);
		matrix.pop();
	}

	public void renderPyroclasm(float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		matrix.push();
		matrix.rotate(new Quaternion(-30 - 60 * progress, -5, 20 - progress * 90, true));
		matrix.translate(0.75 + progress * 0.25, 0, -0.7);
		renderRightArm(matrix, buffer, light, player, partialTicks);
		matrix.pop();
		matrix.push();
		matrix.rotate(new Quaternion(-30 - 60 * progress, 5, -20 + progress * 90, true));
		matrix.translate(-0.75 - progress * 0.25, 0, -0.7);

		renderLeftArm(matrix, buffer, light, player, partialTicks);
		matrix.pop();
	}

	public static void renderBurning(int count, PlayerEntity player, MatrixStack matrix, IRenderTypeBuffer buffers,
			float partialTicks, int light) {
		float ageInTicks = player.ticksExisted + partialTicks;
		BrandParticleModel model = new BrandParticleModel();
		IVertexBuilder ivertexbuilder = buffers.getBuffer(model.getRenderType(BrandParticleModel.TEXTURE_LOCATION));
		Random random = new Random(0);
		for (int i = 0; i < count; i++) {
			int interval = random.nextInt(12) + 12;
			float red = random.nextFloat() * 0.2f + 0.8f;
			float green = random.nextFloat() * 0.2f + 0.3f;
			float blue = 0;
			Vec3d offset = new Vec3d(random.nextDouble() * 0.4 - 0.20, random.nextDouble() * 0.4 - 0.20,
					random.nextDouble() * 0.4 - 0.20);
			matrix.push();

			float scale = Helper.lerpRepeat(ageInTicks / interval, 1, 0);
			matrix.translate(offset.getX(), offset.getY(), offset.getZ());
			matrix.translate(0, 1, 0);
			matrix.rotate(new Quaternion(-90, 0, 0, true));
			matrix.translate(0, -1, 0);
			matrix.scale(scale, scale, scale);

			model.render(matrix, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1);
			matrix.pop();
		}
	}
}
