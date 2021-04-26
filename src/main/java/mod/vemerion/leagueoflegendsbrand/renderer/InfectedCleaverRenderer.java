package mod.vemerion.leagueoflegendsbrand.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.leagueoflegendsbrand.entity.InfectedCleaverEntity;
import mod.vemerion.leagueoflegendsbrand.model.InfectedCleaverModel;
import mod.vemerion.leagueoflegendsbrand.model.MundoModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class InfectedCleaverRenderer extends EntityRenderer<InfectedCleaverEntity> {

	private final InfectedCleaverModel<InfectedCleaverEntity> MODEL;

	public InfectedCleaverRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		MODEL = new InfectedCleaverModel<>();
	}

	@Override
	public void render(InfectedCleaverEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		MODEL.setRotationAngles(entityIn, 0, 0, entityIn.ticksExisted + partialTicks, 0, 0);
		MODEL.setLivingAnimations(entityIn, 0, 0, partialTicks);
		MODEL.render(matrixStackIn, bufferIn.getBuffer(MODEL.getRenderType(MundoModel.TEXTURES)), packedLightIn,
				OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
	}

	@Override
	public ResourceLocation getEntityTexture(InfectedCleaverEntity entity) {
		return MundoModel.TEXTURES;
	}

}
