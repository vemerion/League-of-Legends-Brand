package mod.vemerion.leagueoflegendsbrand.renderer.champion;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.helper.ClientHelper;
import mod.vemerion.leagueoflegendsbrand.helper.Helper;
import mod.vemerion.leagueoflegendsbrand.init.ModEffects;
import mod.vemerion.leagueoflegendsbrand.init.ModItems;
import mod.vemerion.leagueoflegendsbrand.model.CubeModel;
import mod.vemerion.leagueoflegendsbrand.model.MundoModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;

public class MundoRenderer extends ChampionRenderer {

	private final Renderer RENDERER;
	private final SadismRing SADISM_RING;

	public MundoRenderer(EntityRendererManager renderManager) {
		RENDERER = new Renderer(renderManager);
		SADISM_RING = new SadismRing();
	}

	@Override
	public void renderThirdPerson(AbstractClientPlayerEntity player, float yaw, float partialTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
		float ageInTicks = player.ticksExisted + partialTicks;
		RENDERER.render(player, yaw, partialTicks, matrix, buffer, light);
		Champions.get(player).ifPresent(c -> {
			if (c.getMundo().isBurningAgonyActivated())
				renderBurningAgony(0.2, 0, ageInTicks, matrix, buffer, light);
			if (c.hasEffect(ModEffects.SADISM)) {
				renderSadismReg(player, ageInTicks, matrix, buffer, light);
				renderSadismRing(player, ageInTicks, matrix, buffer, light);
			}
		});
	}

	@Override
	protected void renderFirstPerson(AbstractClientPlayerEntity player, float partialTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
		float ageInTicks = player.ticksExisted + partialTicks;
		Champions.get(player).ifPresent(c -> {
			if (c.getMundo().isBurningAgonyActivated()) {
				renderBurningAgony(-1.5, 0, ageInTicks, matrix, buffer, light);
			}
			if (c.hasEffect(ModEffects.SADISM)) {
				matrix.push();
				matrix.rotate(new Quaternion(player.getPitch(partialTicks), 0, 0, true));
				matrix.translate(0, -2, 0);
				renderSadismRing(player, ageInTicks, matrix, buffer, light);
				matrix.pop();
			}

		});

	}

	private void renderSadismRing(AbstractClientPlayerEntity player, float ageInTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
		SADISM_RING.render(ageInTicks, matrix, buffer, light);
	}

	private void renderSadismReg(AbstractClientPlayerEntity player, float ageInTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
		CubeModel.getCube().renderBurning(25, 0.5f, ageInTicks, matrix, buffer, light, CubeModel::green);
	}

	private void renderBurningAgony(double height, float yaw, float ageInTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
		Vector3d rotation = Vector3d.fromPitchYaw(0, yaw).rotateYaw(ageInTicks / 10);
		for (int i = 0; i < 3; i++) {
			matrix.push();
			matrix.translate(rotation.x * 1.5, height, rotation.z * 1.5);
			CubeModel.getCube().renderBall(300, 1.5f, rotation.rotateYaw(ClientHelper.toRad(-90)), ageInTicks, matrix,
					buffer, light);
			rotation = rotation.rotateYaw(ClientHelper.toRad(360 / 3));
			matrix.pop();
		}
	}

	@Override
	protected boolean renderR(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		return false;
	}

	@Override
	protected boolean renderE(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		return false;
	}

	@Override
	protected boolean renderW(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		return false;
	}

	@Override
	protected boolean renderQ(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks) {
		if (side == HandSide.RIGHT)
			return false;
		RENDERER.renderInfectedCleaver(side, progress, matrix, buffer, light, player, partialTicks);
		return true;
	}

	@Override
	public void renderHand(HandSide side, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
			AbstractClientPlayerEntity player, float partialTicks, float swingProgress, float equipProgress) {
		RENDERER.renderHand(side, matrix, buffer, light, player, partialTicks, swingProgress, equipProgress);

	}

	public static boolean shouldRenderCleaver(AbstractClientPlayerEntity player, Champions c) {
		ItemStack left = player.getHeldItem(ClientHelper.leftHand(player));
		return (left.isEmpty() || c.isSpell(left.getItem()))
				&& !player.getCooldownTracker().hasCooldown(ModItems.INFECTED_CLEAVER);
	}

	public static class Renderer extends HumanRenderer {

		MundoModel model;

		public Renderer(EntityRendererManager renderManager) {
			super(renderManager);
			model = new MundoModel(0);
			entityModel = model;
		}

		@Override
		public ResourceLocation getEntityTexture(AbstractClientPlayerEntity entity) {
			return MundoModel.TEXTURES;
		}

		@Override
		public void render(AbstractClientPlayerEntity entityIn, float entityYaw, float partialTicks,
				MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
			Champions.get(entityIn).ifPresent(c -> {
				model.cleaver.setVisible(shouldRenderCleaver(entityIn, c));
			});

			model.needle1.showModel = entityIn.inventory.armorItemInSlot(EquipmentSlotType.CHEST.getIndex()).isEmpty();
			model.hair1.showModel = entityIn.inventory.armorItemInSlot(EquipmentSlotType.HEAD.getIndex()).isEmpty();
			model.hair2.showModel = entityIn.inventory.armorItemInSlot(EquipmentSlotType.HEAD.getIndex()).isEmpty();
			model.hair3.showModel = entityIn.inventory.armorItemInSlot(EquipmentSlotType.HEAD.getIndex()).isEmpty();

			super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		}

		private void renderInfectedCleaver(HandSide side, float progress, MatrixStack matrix, IRenderTypeBuffer buffer,
				int light, AbstractClientPlayerEntity player, float partialTicks) {
			matrix.push();
			matrix.rotate(new Quaternion(-70 + MathHelper.sin(progress * ClientHelper.toRad(120)) * 40, 0, 0, true));
			matrix.translate(-1, -progress * 0.1, -0.7);
			renderArm(side, matrix, buffer, light, player, partialTicks);
			matrix.pop();
		}

		@Override
		protected void preRenderArm(HandSide side, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
				int combinedLightIn, AbstractClientPlayerEntity playerIn, float partialTicks) {
			super.preRenderArm(side, matrixStackIn, bufferIn, combinedLightIn, playerIn, partialTicks);
			Champions.get(playerIn).ifPresent(c -> {
				model.cleaver.setVisible(shouldRenderCleaver(playerIn, c));
			});
		}

		@Override
		public void renderHand(HandSide side, MatrixStack matrix, IRenderTypeBuffer buffer, int light,
				AbstractClientPlayerEntity player, float partialTicks, float swingProgress, float equipProgress) {
			matrix.push();
			Champions.get(player).ifPresent(c -> {
				if (side == HandSide.LEFT && shouldRenderCleaver(player, c) && swingProgress < 0.01
						&& equipProgress < 0.01 && player.isSprinting()) {
					matrix.rotate(new Quaternion(20, 0, 0, true));
					matrix.translate(0, 0.03, 0);
				}
			});
			super.renderHand(side, matrix, buffer, light, player, partialTicks, swingProgress, equipProgress);
			matrix.pop();
		}

	}

	private static class SadismRing extends Model {

		public static final ResourceLocation TEXTURES = new ResourceLocation(Main.MODID,
				"textures/entity/sadism_ring.png");

		private ModelRenderer ring;

		public SadismRing() {
			super(RenderType::getEntityTranslucentCull);
			this.textureWidth = 32;
			this.textureHeight = 16;
			this.ring = new ModelRenderer(this, 0, 0);
			this.ring.setRotationPoint(0, 0.1f, 0);
			this.ring.addBox(-8, 0, -8, 16, 0, 16, 0.0F, 0.0F, 0.0F);
		}

		public void render(float ageInTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int packedLightIn) {
			IVertexBuilder builder = buffer.getBuffer(getRenderType(SadismRing.TEXTURES));
			for (int i = 0; i < 4; i++) {
				float progress = (ageInTicks + i * 5) / 20;
				float scale = Helper.lerpRepeat(progress, 0, 4);
				matrix.push();
				matrix.scale(scale, 1, scale);
				render(matrix, builder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1,
						Helper.lerpRepeat(1 - progress, 0, 1));
				matrix.pop();
			}
		}

		@Override
		public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
				float red, float green, float blue, float alpha) {
			ring.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		}

	}
}
