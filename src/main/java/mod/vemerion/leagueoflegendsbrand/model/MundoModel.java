package mod.vemerion.leagueoflegendsbrand.model;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.helper.ClientHelper;
import mod.vemerion.leagueoflegendsbrand.renderer.champion.MundoRenderer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * Created using Tabula 8.0.0
 */
public class MundoModel extends PlayerModel<AbstractClientPlayerEntity> {
	
	public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID, "textures/entity/mundo.png");
	
	public ModelRenderer needle1;
	public ModelRenderer needle2;
	public ModelRenderer needle3;
	public ModelRenderer needle4;
	public ModelRenderer yaw;
	public ModelRenderer backHead;
	
	public InfectedCleaverModel<?> cleaver;

	public MundoModel(float modelSize) {
		super(modelSize, false);
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.textureWidth = 64;
		this.textureHeight = 128;
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 6.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.needle4 = new ModelRenderer(this, 0, 72);
		this.needle4.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.needle4.addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 32, 48);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.needle3 = new ModelRenderer(this, 9, 64);
		this.needle3.setRotationPoint(0.0F, -5.0F, 0.0F);
		this.needle3.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.backHead = new ModelRenderer(this, 0, 8);
		this.backHead.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.backHead.addBox(-4.0F, -2.0F, -5.0F, 8.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.yaw = new ModelRenderer(this, 2, 8);
		this.yaw.setRotationPoint(0.0F, -2.0F, -1.0F);
		this.yaw.addBox(-4.0F, 0.0F, -4.0F, 8.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(yaw, 0.23457224414434488F, 0.0F, 0.0F);
		this.needle2 = new ModelRenderer(this, 0, 64);
		this.needle2.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.needle2.addBox(-1.5F, -5.0F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.needle1 = new ModelRenderer(this, 9, 64);
		this.needle1.setRotationPoint(-1.0F, -2.0F, 0.0F);
		this.needle1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.needle3.addChild(this.needle4);
		this.needle2.addChild(this.needle3);
		this.bipedHead.addChild(this.backHead);
		this.bipedHead.addChild(this.yaw);
		this.needle1.addChild(this.needle2);
		this.bipedRightArm.addChild(this.needle1);
		
		cleaver = new InfectedCleaverModel<>();
		this.bipedLeftArm.addChild(cleaver.cleaverHandle);
	}

	@Override
	public void setRotationAngles(AbstractClientPlayerEntity entityIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		Champions.get(entityIn).ifPresent(c -> {
			if (MundoRenderer.shouldRenderCleaver(entityIn, c) && limbSwingAmount > 0.1 && swingProgress < 0.01)
				bipedLeftArm.rotateAngleX -= ClientHelper.toRad(110);
		});
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
