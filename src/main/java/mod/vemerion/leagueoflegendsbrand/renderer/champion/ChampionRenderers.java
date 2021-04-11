package mod.vemerion.leagueoflegendsbrand.renderer.champion;

import java.util.EnumMap;
import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.vemerion.leagueoflegendsbrand.champion.Champion;
import mod.vemerion.leagueoflegendsbrand.champion.Champions;
import mod.vemerion.leagueoflegendsbrand.item.SpellItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraftforge.common.util.LazyOptional;

public class ChampionRenderers {

	private static ChampionRenderers instance;

	private EnumMap<Champion, ChampionRenderer> renderers;
	private Supplier<Minecraft> mc;

	public static void init(Supplier<Minecraft> mc) {
		instance = new ChampionRenderers(mc);
	}

	public static ChampionRenderers getInstance() {
		return instance;
	}

	public ChampionRenderers(Supplier<Minecraft> mc) {
		this.mc = mc;
	}

	private ChampionRenderer getRenderer(Champion champion) {
		if (renderers == null) {
			renderers = new EnumMap<>(Champion.class);
			renderers.put(Champion.BRAND, new BrandRenderer(mc.get().getRenderManager()));
		}
		return renderers.get(champion);
	}

	public void render(AbstractClientPlayerEntity player, float yaw, float partialTicks, MatrixStack matrix,
			IRenderTypeBuffer buffer, int light) {
		Champions.get(player).ifPresent(c -> {
			if (c.isSteve())
				return;
			getRenderer(c.getChampion()).render(player, yaw, partialTicks, matrix, buffer, light);
		});
	}

	public boolean renderHand(HandSide side, AbstractClientPlayerEntity player, ItemStack stack, float partialTicks,
			MatrixStack matrix, IRenderTypeBuffer buffer, int light, float swingProgress, float equipProgress) {
		boolean cancel = false;
		LazyOptional<Champions> champs = Champions.get(player);
		if (!champs.isPresent())
			return cancel;

		Champions champions = champs.orElseGet(null);
		if (champions.isSteve())
			return cancel;

		ChampionRenderer renderer = getRenderer(champions.getChampion());
		ItemStack activeStack = player.getActiveItemStack();
		Item activeItem = activeStack.getItem();
		boolean shouldRenderHand = stack.isEmpty() || champions.isSpell(stack.getItem());
		if (champions.isSpell(activeItem)) {
			float maxProgress = (float) activeStack.getUseDuration();
			float progress = (maxProgress - (player.getItemInUseCount() - partialTicks + 1f)) / maxProgress;
			cancel = renderer.renderSpell(((SpellItem) activeItem).getKey(), side, progress, matrix, buffer, light,
					player, partialTicks);
			if (!cancel && shouldRenderHand) {
				renderer.renderHand(side, matrix, buffer, light, player, partialTicks, swingProgress, equipProgress);
				cancel = true;
			}
		} else if (shouldRenderHand) {
			renderer.renderHand(side, matrix, buffer, light, player, partialTicks, swingProgress, equipProgress);
			cancel = true;
		}
		return cancel;
	}
}
