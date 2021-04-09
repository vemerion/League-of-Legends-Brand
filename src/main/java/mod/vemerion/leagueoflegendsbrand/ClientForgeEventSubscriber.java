package mod.vemerion.leagueoflegendsbrand;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.capability.Brand;
import mod.vemerion.leagueoflegendsbrand.item.BrandSpell;
import mod.vemerion.leagueoflegendsbrand.model.CubeModel;
import mod.vemerion.leagueoflegendsbrand.renderer.BrandRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = LeagueOfLegendsBrand.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	@SubscribeEvent
	public static void renderBurning(RenderLivingEvent<?, ?> event) {
		LivingEntity entity = event.getEntity();
		Ablazed.get(entity).ifPresent(a -> {
			if (!a.isBurning())
				return;
			float width = entity.getSize(Pose.STANDING).width * 0.7f;
			CubeModel.getCube().renderBurning(200, width, a.getBurning() + event.getPartialRenderTick(),
					event.getMatrixStack(), event.getBuffers(), event.getLight());
		});

	}

	@SubscribeEvent
	public static void renderBrand(RenderPlayerEvent.Pre event) {
		Brand.get(event.getPlayer()).ifPresent(b -> {
			if (!(event.getRenderer() instanceof BrandRenderer) && b.isBrand()) {
				event.setCanceled(true);
				renderBrandTexture(event);
				renderBurningHead(event);
			}
		});
	}

	private static void renderBrandTexture(RenderPlayerEvent.Pre event) {
		BrandRenderer renderer = new BrandRenderer(event.getRenderer().getRenderManager());
		float partialTicks = event.getPartialRenderTick();
		AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) event.getPlayer();
		float yaw = MathHelper.lerp(partialTicks, player.prevRotationYaw, player.rotationYaw);
		renderer.render(player, yaw, partialTicks, event.getMatrixStack(), event.getBuffers(), event.getLight());
	}

	private static void renderBurningHead(RenderPlayerEvent.Pre event) {
		PlayerEntity player = event.getPlayer();
		Pose pose = player.getPose();
		float eyeHeight = player.getStandingEyeHeight(pose, player.getSize(pose));
		if (pose == Pose.SWIMMING || pose == Pose.FALL_FLYING) {
			return;
		}
		MatrixStack matrix = event.getMatrixStack();
		matrix.push();
		matrix.rotate(new Quaternion(-90, 0, 0, true));
		matrix.translate(0, -1, 0.1 + eyeHeight);
		BrandRenderer.renderBurning(35, player, event.getMatrixStack(), event.getBuffers(),
				event.getPartialRenderTick(), event.getLight());
		matrix.pop();
	}

	@SubscribeEvent
	public static void transformBrandAnimation(RenderHandEvent event) {
		AbstractClientPlayerEntity player = Minecraft.getInstance().player;
		Item item = event.getItemStack().getItem();
		ItemStack itemStack = event.getItemStack();
		if (item == LeagueOfLegendsBrand.SUMMONERS_RIFT_BRAND_ITEM && player.getActiveItemStack().equals(itemStack)) {
			event.setCanceled(true);
			MatrixStack matrix = event.getMatrixStack();
			float progress = player.ticksExisted + event.getPartialTicks();
			matrix.push();
			matrix.translate(0, 0, -0.4);
			matrix.rotate(new Quaternion(-90, 0, 0, true));
			BrandRenderer.renderBurning(40, player, event.getMatrixStack(), event.getBuffers(), event.getPartialTicks(),
					event.getLight());
			matrix.pop();
			matrix.push();
			matrix.translate(MathHelper.cos((progress / 10) * (float) Math.PI * 2) * 0.1,
					-0.5 + MathHelper.cos((progress / 6) * (float) Math.PI * 2) * 0.1,
					-1.5 + MathHelper.cos((progress / 15) * (float) Math.PI * 2) * 0.1);
			Minecraft.getInstance().getItemRenderer().renderItem(itemStack, TransformType.GUI, event.getLight(),
					OverlayTexture.NO_OVERLAY, event.getMatrixStack(), event.getBuffers());
			matrix.pop();
		}
	}

	@SubscribeEvent
	public static void brandHand(RenderHandEvent event) {
		AbstractClientPlayerEntity player = Minecraft.getInstance().player;
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();
		float partialTicks = event.getPartialTicks();
		Brand.get(player).ifPresent(b -> {
			if (b.isBrand()) {
				if (player.getActiveItemStack().getItem() instanceof BrandSpell || stack.isEmpty()
						|| item instanceof BrandSpell)
					event.setCanceled(true);

				if (item instanceof BrandSpell) {
					if (player.getActiveItemStack() == stack) {
						BrandRenderer renderer = new BrandRenderer(Minecraft.getInstance().getRenderManager());
						float maxProgress = (float) stack.getUseDuration();
						float progress = (maxProgress
								- ((float) player.getItemInUseCount() - (float) event.getPartialTicks() + 1.0f))
								/ maxProgress;
						if (item.equals(LeagueOfLegendsBrand.SEAR_SPELL)) {
							renderer.renderSear(progress, event.getMatrixStack(), event.getBuffers(), event.getLight(),
									player, partialTicks);
						} else if (item.equals(LeagueOfLegendsBrand.PILLAR_OF_FLAME_SPELL)) {
							renderer.renderPillarOfFlame(progress, event.getMatrixStack(), event.getBuffers(),
									event.getLight(), player, partialTicks);
						} else if (item.equals(LeagueOfLegendsBrand.CONFLAGRATION_SPELL)) {
							renderer.renderConflagration(progress, event.getMatrixStack(), event.getBuffers(),
									event.getLight(), player, partialTicks);
						} else if (item.equals(LeagueOfLegendsBrand.PYROCLASM_SPELL)) {
							renderer.renderPyroclasm(progress, event.getMatrixStack(), event.getBuffers(),
									event.getLight(), player, partialTicks);
						}
					} else {
						renderBrandHand(event);
					}
				} else if (stack.isEmpty() && event.getHand() == Hand.MAIN_HAND) {
					renderBrandHand(event);
				}
			}
		});
	}

	private static void renderBrandHand(RenderHandEvent event) {
		Minecraft mc = Minecraft.getInstance();
		AbstractClientPlayerEntity player = mc.player;
		if (player.getActiveItemStack().getItem() instanceof BrandSpell)
			return;
		BrandRenderer renderer = new BrandRenderer(mc.getRenderManager());
		mc.getTextureManager().bindTexture(BrandRenderer.TEXTURES);
		float swingProgress = event.getSwingProgress();
		float equipProgress = swingProgress > 0.01 ? 0 : event.getEquipProgress();
		HandSide side = event.getHand() == Hand.MAIN_HAND ? player.getPrimaryHand()
				: player.getPrimaryHand().opposite();
		float offset = side == HandSide.RIGHT ? 1 : -1;
		MatrixStack matrix = event.getMatrixStack();
		matrix.push();

		matrix.translate(0, 0, -1);
		matrix.rotate(
				new Quaternion(-45 - swingProgress * 100, offset * (20 + swingProgress * 100), -20 * offset, true));
		matrix.translate(offset * 1.2, -0.7 - equipProgress + swingProgress * 2.5, -swingProgress * 2);
		renderer.renderArm(side, matrix, event.getBuffers(), event.getLight(), player, event.getPartialTicks());
		matrix.pop();
	}
}
