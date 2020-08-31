package mod.vemerion.leagueoflegendsbrand.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.item.BrandSpell;
import mod.vemerion.leagueoflegendsbrand.model.BrandModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GlowingBrandLayer
		extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(LeagueOfLegendsBrand.MODID,
			"textures/entity/fire_layer.png");
	private final BrandModel model = new BrandModel(0.1f);

	public GlowingBrandLayer(
			IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn,
			AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch) {
		if (!player.isInvisible()) {
			getEntityModel().copyModelAttributesTo(model);
			model.isSneak = getEntityModel().isSneak;
			model.setLivingAnimations(player, limbSwing, limbSwingAmount, partialTicks);
			model.setRotationAngles(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			ItemStack main = player.getHeldItemMainhand();
			ItemStack off = player.getHeldItemOffhand();
			boolean castingSpell = player.isHandActive() && player.getActiveItemStack().getItem() instanceof BrandSpell;
			if (!main.isEmpty() && !castingSpell)
				model.bipedRightArm.rotateAngleX -= Math.toRadians(18);
			if (!off.isEmpty() && !castingSpell)
				model.bipedLeftArm.rotateAngleX -= Math.toRadians(18);

			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(TEXTURES));
			float red = 0.5f + MathHelper.sin((ageInTicks / 40) * (float) Math.PI * 2) * 0.5f;
			float green = 0.3f + MathHelper.sin((ageInTicks / 40) * (float) Math.PI * 2) * 0.3f;
			model.render(matrixStackIn, ivertexbuilder, packedLightIn,
					LivingRenderer.getPackedOverlay(player, 0.0F), red, green, 0, 1.0F);
		}
	}

}
