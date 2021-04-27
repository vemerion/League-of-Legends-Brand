package mod.vemerion.leagueoflegendsbrand.renderer.champion;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import mod.vemerion.leagueoflegendsbrand.model.BrandModel;
import mod.vemerion.leagueoflegendsbrand.model.CubeModel;
import mod.vemerion.leagueoflegendsbrand.renderer.GlowingBrandLayer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;

public class BrandRenderer extends ChampionRenderer {

	private final Renderer RENDERER;

	public BrandRenderer(EntityRendererManager renderManager) {
		RENDERER = new Renderer(renderManager);
	}

	@Override
	public void renderThirdPerson(AbstractClientPlayerEntity player, float yaw, float partialTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
		RENDERER.render(player, yaw, partialTicks, matrix, buffer, light);
	}

	@Override
	protected boolean renderR(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		RENDERER.renderPyroclasm(side, progress, matrix, buffer, light, player, partialTicks);
		return true;
	}

	@Override
	protected boolean renderE(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		RENDERER.renderConflagration(side, progress, matrix, buffer, light, player, partialTicks);
		return true;
	}

	@Override
	protected boolean renderW(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		RENDERER.renderPillarOfFlame(side, progress, matrix, buffer, light, player, partialTicks);
		return true;
	}

	@Override
	protected boolean renderQ(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		RENDERER.renderSear(side, progress, matrix, buffer, light, player, partialTicks);
		return true;
	}

	@Override
	public void renderHand(HandSide side, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks, float swingProgress, float equipProgress) {
		RENDERER.renderHand(side, matrix, buffer, light, player, partialTicks, swingProgress, equipProgress);
	}

	public static class Renderer extends HumanRenderer {
		public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID, "textures/entity/brand.png");

		public Renderer(EntityRendererManager renderManager) {
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
			renderBurningHead(entityIn, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		}

		@Override
		protected void preRenderArm(HandSide side, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
				int combinedLightIn, AbstractClientPlayerEntity playerIn, float partialTicks) {
			super.preRenderArm(side, matrixStackIn, bufferIn, combinedLightIn, playerIn, partialTicks);
			matrixStackIn.push();
			matrixStackIn.translate(0.35 * (side == HandSide.LEFT ? 1 : -1), -0.35, 0.2);
			renderBurning(35, playerIn, matrixStackIn, bufferIn, partialTicks, combinedLightIn);
			matrixStackIn.pop();
		}

		private void renderBurningHead(AbstractClientPlayerEntity player, float partialTicks, MatrixStack matrix,
				IRenderTypeBuffer bufferIn, int light) {
			Pose pose = player.getPose();
			float eyeHeight = player.getStandingEyeHeight(pose, player.getSize(pose));
			if (pose == Pose.SWIMMING || pose == Pose.FALL_FLYING) {
				return;
			}
			matrix.push();
			matrix.rotate(new Quaternion(-90, 0, 0, true));
			matrix.translate(0, -1, 0.1 + eyeHeight);
			renderBurning(35, player, matrix, bufferIn, partialTicks, light);
			matrix.pop();
		}

		private void renderSear(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
				AbstractClientPlayerEntity player, float partialTicks) {
			int offset = side == HandSide.RIGHT ? 1 : -1;
			matrix.push();
			matrix.rotate(new Quaternion(-70, -offset * 5, offset * progress * 20, true));
			matrix.translate(offset * (1 - progress * 0.25), 0.2, -0.7);
			renderArm(side, matrix, buffer, light, player, partialTicks);
			matrix.pop();
		}

		private void renderPillarOfFlame(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer,
				int light, AbstractClientPlayerEntity player, float partialTicks) {
			int offset = side == HandSide.RIGHT ? 1 : -1;
			matrix.push();
			matrix.rotate(new Quaternion(-90 * progress, -offset * 5, offset * (20 - progress * 20), true));
			matrix.translate(offset * (0.5 + progress), 0, 0.3 - 2 * progress);
			renderArm(side, matrix, buffer, light, player, partialTicks);
			matrix.pop();
		}

		private void renderConflagration(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer,
				int light, AbstractClientPlayerEntity player, float partialTicks) {
			int offset = side == HandSide.RIGHT ? 1 : -1;
			matrix.push();
			matrix.rotate(new Quaternion(-70, -offset * 5, offset * (20 - progress * 90), true));
			matrix.translate(offset * (0.75 + progress * 0.25), 0.2, -0.7);
			renderArm(side, matrix, buffer, light, player, partialTicks);
			matrix.pop();
		}

		private void renderPyroclasm(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer,
				int light, AbstractClientPlayerEntity player, float partialTicks) {
			int offset = side == HandSide.RIGHT ? 1 : -1;
			matrix.push();
			matrix.rotate(new Quaternion(-30 - 60 * progress, -offset * 5, offset * (20 - progress * 90), true));
			matrix.translate(offset * (0.75 + progress * 0.25), 0, -0.7);
			renderArm(side, matrix, buffer, light, player, partialTicks);
			matrix.pop();
		}

		private static void renderBurning(int count, PlayerEntity player, MatrixStack matrix, IRenderTypeBuffer buffers,
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
}
