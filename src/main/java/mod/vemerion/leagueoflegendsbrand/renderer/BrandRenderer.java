package mod.vemerion.leagueoflegendsbrand.renderer;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import mod.vemerion.leagueoflegendsbrand.model.BrandModel;
import mod.vemerion.leagueoflegendsbrand.model.CubeModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;

public class BrandRenderer extends PlayerRenderer {
	public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID,
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

	public void renderArm(HandSide side, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, float partialTicks) {
		ModelRenderer arm = side == HandSide.LEFT ? entityModel.bipedLeftArm : entityModel.bipedRightArm;
		preRenderArm(playerIn);
		arm.rotateAngleX = 0.0F;
		matrixStackIn.push();
		matrixStackIn.translate(0.35 * (side == HandSide.LEFT ? 1 : -1), -0.35, 0.2);
		renderBurning(35, playerIn, matrixStackIn, bufferIn, partialTicks, combinedLightIn);
		matrixStackIn.pop();
		arm.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(getEntityTexture(playerIn))),
				combinedLightIn, OverlayTexture.NO_OVERLAY);
	}

	public void renderSear(float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		matrix.push();
		matrix.rotate(new Quaternion(-70, -5, progress * 20, true));
		matrix.translate(1 - progress * 0.25, 0.2, -0.7);
		renderArm(HandSide.RIGHT, matrix, buffer, light, player, partialTicks);
		matrix.pop();
		matrix.push();
		matrix.rotate(new Quaternion(-70, 5, -progress * 20, true));
		matrix.translate(-1 + progress * 0.25, 0.2, -0.7);
		renderArm(HandSide.LEFT, matrix, buffer, light, player, partialTicks);
		matrix.pop();
	}

	public void renderPillarOfFlame(float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		matrix.push();
		matrix.rotate(new Quaternion(-90 * progress, -5, 20 - progress * 20, true));
		matrix.translate(0.5 + progress * 1, 0, 0.3 - 2 * progress);
		renderArm(HandSide.RIGHT, matrix, buffer, light, player, partialTicks);
		matrix.pop();
		matrix.push();
		matrix.rotate(new Quaternion(-90 * progress, 5, -20 + progress * 20, true));
		matrix.translate(-0.5 - progress * 1, 0, 0.3 - 2 * progress);

		renderArm(HandSide.LEFT, matrix, buffer, light, player, partialTicks);
		matrix.pop();
	}

	public void renderConflagration(float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		matrix.push();
		matrix.rotate(new Quaternion(-70, -5, 20 - progress * 90, true));
		matrix.translate(0.75 + progress * 0.25, 0.2, -0.7);
		renderArm(HandSide.RIGHT, matrix, buffer, light, player, partialTicks);
		matrix.pop();
		matrix.push();
		matrix.rotate(new Quaternion(-70, 5, -20 + progress * 90, true));
		matrix.translate(-0.75 - progress * 0.25, 0.2, -0.7);

		renderArm(HandSide.LEFT, matrix, buffer, light, player, partialTicks);
		matrix.pop();
	}

	public void renderPyroclasm(float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		matrix.push();
		matrix.rotate(new Quaternion(-30 - 60 * progress, -5, 20 - progress * 90, true));
		matrix.translate(0.75 + progress * 0.25, 0, -0.7);
		renderArm(HandSide.RIGHT, matrix, buffer, light, player, partialTicks);
		matrix.pop();
		matrix.push();
		matrix.rotate(new Quaternion(-30 - 60 * progress, 5, -20 + progress * 90, true));
		matrix.translate(-0.75 - progress * 0.25, 0, -0.7);

		renderArm(HandSide.LEFT, matrix, buffer, light, player, partialTicks);
		matrix.pop();
	}

	public static void renderBurning(int count, PlayerEntity player, MatrixStack matrix, IRenderTypeBuffer buffers,
			float partialTicks, int light) {
		float ageInTicks = player.ticksExisted + partialTicks;
		CubeModel model = new CubeModel();
		IVertexBuilder ivertexbuilder = buffers.getBuffer(model.getRenderType(model.getTexture()));
		Random random = new Random(0);
		for (int i = 0; i < count; i++) {
			int interval = random.nextInt(12) + 12;
			Vector3d offset = new Vector3d(random.nextDouble() * 0.4 - 0.20, random.nextDouble() * 0.4 - 0.20,
					random.nextDouble() * 0.4 - 0.20).add(0, 0, Helper.lerpRepeat(ageInTicks / interval, 0, 0.8f));

			float scale = Helper.lerpRepeat(ageInTicks / interval, 1, 0);

			model.render(random, ageInTicks, scale, offset, matrix, ivertexbuilder, light);
		}
	}
}
