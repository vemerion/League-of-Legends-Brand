package mod.vemerion.leagueoflegendsbrand.renderer.champion;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.helper.ClientHelper;
import mod.vemerion.leagueoflegendsbrand.model.BrandModel;
import mod.vemerion.leagueoflegendsbrand.model.CubeModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class MundoRenderer extends ChampionRenderer {

	private final Renderer RENDERER;

	public MundoRenderer(EntityRendererManager renderManager) {
		RENDERER = new Renderer(renderManager);
	}

	@Override
	public void renderThirdPerson(AbstractClientPlayerEntity player, float yaw, float partialTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
		Champions.get(player).ifPresent(c -> {
			if (c.getMundo().isBurningAgonyActivated()) {
				renderBurningAgony(0.2, player, partialTicks, partialTicks, matrix, buffer, light);
			}
		});
	}
	
	@Override
	protected void renderFirstPerson(AbstractClientPlayerEntity player, float partialTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
		Champions.get(player).ifPresent(c -> {
			if (c.getMundo().isBurningAgonyActivated()) {
				renderBurningAgony(-1.5, player, partialTicks, partialTicks, matrix, buffer, light);
			}
		});
	}

	private void renderBurningAgony(double height, AbstractClientPlayerEntity player, float yaw, float partialTicks,
			MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
		float ageInTicks = player.ticksExisted + partialTicks;
		Vector3d rotation = Vector3d.fromPitchYaw(0, yaw).rotateYaw(ageInTicks / 10);
		for (int i = 0; i < 3; i++) {
			matrix.push();
			matrix.translate(rotation.x * 1.5, height, rotation.z * 1.5);
			CubeModel.getCube().renderBall(300, 1.5f, rotation.rotateYaw(ClientHelper.toRad(-90)), ageInTicks, matrix,
					buffer, light);
			rotation = rotation.rotateYaw(ClientHelper.toRad(360 / 3));
			matrix.pop();
		}
	}

	@Override
	protected boolean renderR(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		return true;
	}

	@Override
	protected boolean renderE(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		return true;
	}

	@Override
	protected boolean renderW(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		return true;
	}

	@Override
	protected boolean renderQ(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		return true;
	}

	@Override
	public void renderHand(HandSide side, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks, float swingProgress, float equipProgress) {
	}

	public static class Renderer extends PlayerRenderer implements CustomRenderer {
		public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID, "textures/entity/brand.png");

		public Renderer(EntityRendererManager renderManager) {
			super(renderManager);
			entityModel = new BrandModel(0);
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

	}
}
