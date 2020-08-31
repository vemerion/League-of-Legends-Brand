package mod.vemerion.leagueoflegendsbrand.model;

import mod.vemerion.leagueoflegendsbrand.item.BrandSpell;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;

public class BrandModel extends PlayerModel<AbstractClientPlayerEntity> {

	public BrandModel(float modelSize) {
		super(modelSize, false);
	}

	@Override
	public void setRotationAngles(AbstractClientPlayerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if (entityIn.getActiveItemStack().getItem() instanceof BrandSpell) {
			bipedLeftArm.rotateAngleX = -(float)Math.PI / 2;
			bipedLeftArm.rotateAngleY = (float)Math.PI / 10;
			bipedLeftArm.rotateAngleZ = 0;
			bipedRightArm.rotateAngleX = -(float)Math.PI / 2;
			bipedRightArm.rotateAngleY = -(float)Math.PI / 10;
			bipedRightArm.rotateAngleZ = 0;
		}
	}
}
