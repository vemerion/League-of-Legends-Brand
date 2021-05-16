package mod.vemerion.leagueoflegendsbrand.model;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.helper.ClientHelper;
import mod.vemerion.leagueoflegendsbrand.renderer.champion.MundoRenderer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

/**
 * Created using Tabula 8.0.0
 */
public class MundoModel extends HumanModel {

	public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID, "textures/entity/mundo.png");

	public InfectedCleaverModel<?> cleaver;

	public ModelRenderer needle1;
	public ModelRenderer needle2;
	public ModelRenderer needle3;
	public ModelRenderer needle4;
	public ModelRenderer yaw;
	public ModelRenderer backHead;
	public ModelRenderer hair1;
	public ModelRenderer hair2;
	public ModelRenderer hair3;
	public ModelRenderer lowerTeeth;
	public ModelRenderer tongue1;
	public ModelRenderer tongue2;
	public ModelRenderer nose;

	public MundoModel(float modelSize) {
		super(modelSize, 64, 128);
		this.backHead = new ModelRenderer(this, 0, 96);
		this.backHead.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.backHead.addBox(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.lowerTeeth = new ModelRenderer(this, 12, 64);
		this.lowerTeeth.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.lowerTeeth.addBox(-4.0F, -1.0F, -5.0F, 8.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
		this.bipedLeftLeg.setRotationPoint(2F, 12.0F, 0.0F);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.needle4 = new ModelRenderer(this, 0, 72);
		this.needle4.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.needle4.addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 32, 48);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.hair3 = new ModelRenderer(this, 0, 89);
		this.hair3.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.hair3.addBox(-5.5F, -5.0F, 0.0F, 11.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(hair3, 0.0F, -0.7853981633974483F, 0.0F);
		this.yaw = new ModelRenderer(this, 0, 103);
		this.yaw.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.yaw.addBox(-4.0F, -1.0F, -5.0F, 8.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(yaw, 0.23457224414434488F, 0.0F, 0.0F);
		this.needle3 = new ModelRenderer(this, 9, 64);
		this.needle3.setRotationPoint(0.0F, -5.0F, 0.0F);
		this.needle3.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.needle1 = new ModelRenderer(this, 9, 64);
		this.needle1.setRotationPoint(-2.0F, -1.7F, 0.0F);
		this.needle1.addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(needle1, 0.0F, 0.0F, -0.3127630032889644F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.setRotationPoint(-2F, 12.0F, 0.0F);
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.needle2 = new ModelRenderer(this, 0, 64);
		this.needle2.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.needle2.addBox(-1.5F, -5.0F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.hair2 = new ModelRenderer(this, 0, 89);
		this.hair2.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.hair2.addBox(-5.5F, -5.0F, 0.0F, 11.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(hair2, 0.0F, 0.7853981633974483F, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.tongue1 = new ModelRenderer(this, 22, 90);
		this.tongue1.setRotationPoint(1.0F, -1.0F, -1.0F);
		this.tongue1.addBox(-2.0F, -1.0F, -5.0F, 4.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
		this.hair1 = new ModelRenderer(this, 0, 76);
		this.hair1.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.hair1.addBox(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.tongue2 = new ModelRenderer(this, 40, 93);
		this.tongue2.setRotationPoint(0.0F, -0.5F, -4.6F);
		this.tongue2.addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(tongue2, 0.5864306020384839F, 0.0F, 0.0F);
		this.nose = new ModelRenderer(this, 0, 76);
		this.nose.setRotationPoint(-1.0F, -6.0F, -6.0F);
		this.nose.addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
		this.bipedHead.addChild(this.backHead);
		this.yaw.addChild(this.lowerTeeth);
		this.needle3.addChild(this.needle4);
		this.bipedHead.addChild(this.hair3);
		this.bipedHead.addChild(this.yaw);
		this.needle2.addChild(this.needle3);
		this.bipedRightArm.addChild(this.needle1);
		this.needle1.addChild(this.needle2);
		this.bipedHead.addChild(this.hair2);
		this.yaw.addChild(this.tongue1);
		this.bipedHead.addChild(this.hair1);
		this.tongue1.addChild(this.tongue2);
		this.bipedHead.addChild(this.nose);

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

		tongue2.rotateAngleX = ClientHelper.toRad(40) + MathHelper.cos(ageInTicks / 5) * ClientHelper.toRad(15);
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
