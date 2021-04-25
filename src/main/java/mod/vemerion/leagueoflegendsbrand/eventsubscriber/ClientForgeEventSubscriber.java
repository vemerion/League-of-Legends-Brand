package mod.vemerion.leagueoflegendsbrand.eventsubscriber;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.leagueoflegendsbrand.Main;
import mod.vemerion.leagueoflegendsbrand.capability.Ablazed;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.init.ModItems;
import mod.vemerion.leagueoflegendsbrand.model.CubeModel;
import mod.vemerion.leagueoflegendsbrand.renderer.champion.ChampionRenderers;
import mod.vemerion.leagueoflegendsbrand.renderer.champion.CustomRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
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

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
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
	public static void renderChampion(RenderPlayerEvent.Pre event) {
		if (!(event.getRenderer() instanceof CustomRenderer)) {
			Champions.get(event.getPlayer()).ifPresent(c -> {
				if (c.isChampion())
					event.setCanceled(true);
			});
			AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) event.getPlayer();
			float partialTicks = event.getPartialRenderTick();
			float yaw = MathHelper.lerp(partialTicks, player.prevRotationYaw, player.rotationYaw);

			ChampionRenderers.getInstance().render(player, yaw, partialTicks, event.getMatrixStack(),
					event.getBuffers(), event.getLight());
		}
	}

	@SubscribeEvent
	public static void renderChampionHand(RenderHandEvent event) {
		AbstractClientPlayerEntity player = Minecraft.getInstance().player;
		ItemStack stack = event.getItemStack();
		float partialTicks = event.getPartialTicks();
		HandSide side = event.getHand() == Hand.MAIN_HAND ? player.getPrimaryHand()
				: player.getPrimaryHand().opposite();
		event.setCanceled(
				ChampionRenderers.getInstance().renderHand(side, player, stack, partialTicks, event.getMatrixStack(),
						event.getBuffers(), event.getLight(), event.getSwingProgress(), event.getEquipProgress()));
	}

	@SubscribeEvent
	public static void renderSummonersRiftItem(RenderHandEvent event) {
		AbstractClientPlayerEntity player = Minecraft.getInstance().player;
		Item item = event.getItemStack().getItem();
		ItemStack itemStack = event.getItemStack();
		if (item == ModItems.SUMMONERS_RIFT_BRAND && player.getActiveItemStack().equals(itemStack)) {
			event.setCanceled(true);
			MatrixStack matrix = event.getMatrixStack();
			float progress = player.ticksExisted + event.getPartialTicks();
			matrix.push();
			matrix.translate(MathHelper.cos((progress / 10) * (float) Math.PI * 2) * 0.1,
					-0.5 + MathHelper.cos((progress / 6) * (float) Math.PI * 2) * 0.1,
					-1.5 + MathHelper.cos((progress / 15) * (float) Math.PI * 2) * 0.1);
			Minecraft.getInstance().getItemRenderer().renderItem(itemStack, TransformType.GUI, event.getLight(),
					OverlayTexture.NO_OVERLAY, event.getMatrixStack(), event.getBuffers());
			matrix.pop();
		} else if (item == ModItems.SUMMONERS_RIFT_MUNDO && player.getActiveItemStack().equals(itemStack)) {
			event.setCanceled(true);
			MatrixStack matrix = event.getMatrixStack();
			int maxDuration = itemStack.getUseDuration();
			float duration = (float) maxDuration - ((float) player.getItemInUseCount() - event.getPartialTicks() + 1.0f);
			matrix.push();
			matrix.translate(0, -0.2, -2 + duration * 0.05);
			matrix.rotate(new Quaternion(-30, 90, 0, true));
			matrix.rotate(new Quaternion(-duration, -duration, 0, true));
			Minecraft.getInstance().getItemRenderer().renderItem(itemStack, TransformType.GUI, event.getLight(),
					OverlayTexture.NO_OVERLAY, event.getMatrixStack(), event.getBuffers());
			matrix.pop();
		}
	}
}
