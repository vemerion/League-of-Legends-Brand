package mod.vemerion.leagueoflegendsbrand.model;

import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.Main;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

/**
 * Created using Tabula 8.0.0
 */
public class CubeModel extends Model {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID,
			"textures/brand_particle.png");

	private static final CubeModel CUBE = new CubeModel();

	public ModelRenderer particle;

	public CubeModel() {
		super(RenderType::getEntitySolid);
		this.textureWidth = 4;
		this.textureHeight = 4;
		this.particle = new ModelRenderer(this, 0, 0);
		this.particle.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.particle.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
	}

	public static CubeModel getCube() {
		return CUBE;
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

	public void renderBurning(int count, float width, float ageInTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(getRenderType(getTexture()));
		Random random = new Random(0);

		for (int i = 0; i < count; i++) {
			int interval = random.nextInt(12) + 12;
			float direction = random.nextFloat() * 360;
			float radius = random.nextFloat() * width;
			Vector3d offset = Vector3d.fromPitchYaw(0, direction).scale(radius);

			float scale = (float) MathHelper.clampedLerp(2, 0, (ageInTicks + random.nextFloat() * 3) / interval);
			double x = offset.getX();
			double y = -1 + offset.getY() + MathHelper.lerp((ageInTicks + random.nextFloat() * 3) / interval, 0, 4);
			double z = offset.getZ();

			render(random, ageInTicks, scale, new Vector3d(x, y, z), matrixStackIn, ivertexbuilder,
					packedLightIn);
		}
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