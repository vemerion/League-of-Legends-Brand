package mod.vemerion.leagueoflegendsbrand.model;

import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.helper.ClientHelper;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;

public class BrandModel extends HumanModel {

	public BrandModel(float modelSize) {
		super(modelSize, 64, 64);
	}

	@Override
	public void setRotationAngles(AbstractClientPlayerEntity entityIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		Champions.get(entityIn).ifPresent(c -> {
			if (c.isSpell(entityIn.getActiveItemStack().getItem()))
				for (HandSide side : HandSide.values()) {
					ModelRenderer arm = getArmForSide(side);
					arm.rotateAngleX = -ClientHelper.toRad(90);
					arm.rotateAngleY = ClientHelper.toRad(20) * (side == HandSide.LEFT ? 1 : -1);
					arm.rotateAngleZ = 0;
				}
		});
	}
}
