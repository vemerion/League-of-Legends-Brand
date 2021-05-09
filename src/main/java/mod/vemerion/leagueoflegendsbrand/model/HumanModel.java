package mod.vemerion.leagueoflegendsbrand.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class HumanModel extends PlayerModel<AbstractClientPlayerEntity> {

	public HumanModel(float modelSize, int textureWidth, int textureHeight) {
		super(modelSize, false);
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(bipedBody, bipedRightArm, bipedLeftArm, bipedRightLeg, bipedLeftLeg);
	}

}
