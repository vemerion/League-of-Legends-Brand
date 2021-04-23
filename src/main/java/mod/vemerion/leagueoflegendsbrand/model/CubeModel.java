package mod.vemerion.leagueoflegendsbrand.model;

import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
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
	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID, "textures/brand_particle.png");

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
			IVertexBuilder bufferIn, int packedLightIn, Function<Random, Integer> color) {
		int c = color.apply(rand);
		float red = Helper.red(c) / 255f;
		float green = Helper.green(c) / 255f;
		float blue = Helper.blue(c) / 255f;
		float alfa = Helper.alfa(c) / 255f;

		matrixStackIn.push();

		double x = offset.getX();
		double y = offset.getY();
		double z = offset.getZ();

		matrixStackIn.translate(x, y, z);
		matrixStackIn.translate(0, 1, 0);
		matrixStackIn.scale(scale, scale, scale);
		matrixStackIn.translate(0, -1, 0);

		render(matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, alfa);
		matrixStackIn.pop();
	}

	public static int orange(Random rand) {
		return Helper.color(200 + rand.nextInt(55), 75 + rand.nextInt(55), 0, 255);
	}

	public static int green(Random rand) {
		return Helper.color(0, 200 + rand.nextInt(55), 75 + rand.nextInt(55), 255);
	}

	public void render(Random rand, float age, float scale, Vector3d offset, MatrixStack matrixStackIn,
			IVertexBuilder bufferIn, int packedLightIn) {
		render(rand, age, scale, offset, matrixStackIn, bufferIn, packedLightIn, CubeModel::orange);
	}

	public void renderBurning(int count, float width, float ageInTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn, Function<Random, Integer> color) {
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(getRenderType(getTexture()));
		Random random = new Random(0);

		for (int i = 0; i < count; i++) {
			int interval = random.nextInt(12) + 12;
			float direction = random.nextFloat() * 360;
			float radius = random.nextFloat() * width;
			Vector3d offset = Vector3d.fromPitchYaw(0, direction).scale(radius);

			float progress = (ageInTicks + random.nextFloat() * 3) / interval;
			float scale = (float) Helper.lerpRepeat(progress, 2, 0);
			double x = offset.getX();
			double y = -1 + offset.getY() + Helper.lerpRepeat(progress, 0, 4);
			double z = offset.getZ();

			render(random, ageInTicks, scale, new Vector3d(x, y, z), matrixStackIn, ivertexbuilder, packedLightIn,
					color);
		}
	}

	public void renderBurning(int count, float width, float ageInTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		renderBurning(count, width, ageInTicks, matrixStackIn, bufferIn, packedLightIn, CubeModel::orange);
	}

	public void renderBall(int count, float width, Vector3d direction, float ageInTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int packedLightIn) {
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(getCube().getRenderType(getTexture()));
		Random random = new Random(0);

		for (int i = 0; i < count; i++) {
			int interval = random.nextInt(6) + 6;
			float offsetYaw = random.nextFloat() * 360;
			float offsetPitch = random.nextFloat() * 360;
			float radius = random.nextFloat() * width / 2;
			Vector3d offset = Vector3d.fromPitchYaw(offsetPitch, offsetYaw).scale(radius);

			float scale = Helper.lerpRepeat(ageInTicks / interval, width, 0);
			double x = offset.getX() + Helper.lerpRepeat(ageInTicks / interval, 0, (float) direction.getX());
			double y = -0.5 + offset.getY() + Helper.lerpRepeat(ageInTicks / interval, 0, (float) direction.getY());
			double z = offset.getZ() + Helper.lerpRepeat(ageInTicks / interval, 0, (float) direction.getZ());

			render(random, ageInTicks, scale, new Vector3d(x, y, z), matrixStackIn, ivertexbuilder, packedLightIn);
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