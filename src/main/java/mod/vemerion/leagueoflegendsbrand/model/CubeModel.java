package mod.vemerion.leagueoflegendsbrand.model;

import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

/**
 * Created using Tabula 8.0.0
 */
public class CubeModel extends Model {
	private static final ResourceLocation TEXTURE = new ResourceLocation(LeagueOfLegendsBrand.MODID,
			"textures/brand_particle.png");

	public ModelRenderer particle;

	public CubeModel() {
		super(RenderType::getEntitySolid);
		this.textureWidth = 4;
		this.textureHeight = 4;
		this.particle = new ModelRenderer(this, 0, 0);
		this.particle.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.particle.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		ImmutableList.of(this.particle).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	public void render(Random rand, float age, float scale, Vector3d offset, MatrixStack matrixStackIn,
			IVertexBuilder bufferIn, int packedLightIn) {
		float red = rand.nextFloat() * 0.2f + 0.8f;
		float green = rand.nextFloat() * 0.2f + 0.3f;
		float blue = 0;

		matrixStackIn.push();

		double x = offset.getX();
		double y = offset.getY();
		double z = offset.getZ();

		matrixStackIn.translate(x, y, z);
		matrixStackIn.translate(0, 1, 0);
		matrixStackIn.scale(scale, scale, scale);
		matrixStackIn.translate(0, -1, 0);

		render(matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1);
		matrixStackIn.pop();
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public ResourceLocation getTexture() {
		return TEXTURE;
	}
}