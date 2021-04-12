package mod.vemerion.leagueoflegendsbrand.renderer.champion;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.model.BrandModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;

public class MundoRenderer extends ChampionRenderer {

	private final Renderer RENDERER;

	public MundoRenderer(EntityRendererManager renderManager) {
		RENDERER = new Renderer(renderManager);
	}

	@Override
	public void render(AbstractClientPlayerEntity player, float yaw, float partialTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
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
