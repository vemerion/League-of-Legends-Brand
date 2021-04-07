package mod.vemerion.leagueoflegendsbrand.capability;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import mod.vemerion.leagueoflegendsbrand.entity.PillarOfFlameEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public class Brand {
	private PillarOfFlameEntity pillarOfFlame;
	private boolean isBrand;

	public PillarOfFlameEntity getPillarOfFlame() {
		return pillarOfFlame;
	}

	public void setPillarOfFlame(PillarOfFlameEntity pillarOfFlame) {
		this.pillarOfFlame = pillarOfFlame;
	}

	public boolean isBrand() {
		return isBrand;
	}

	public void setBrand(boolean value) {
		isBrand = value;
	}

	public static LazyOptional<Brand> getBrand(Entity player) {
		return player.getCapability(LeagueOfLegendsBrand.BRAND_CAP);
	}

	public static void syncBrand(Entity entity) {
		getBrand(entity).ifPresent(b -> {
			BrandMessage.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
					new BrandMessage(b.isBrand(), entity.getUniqueID()));
		});
	}

	public static void syncBrand(Entity entity, ServerPlayerEntity reciever) {
		getBrand(entity).ifPresent(b -> {
			BrandMessage.INSTANCE.send(PacketDistributor.PLAYER.with(() -> reciever),
					new BrandMessage(b.isBrand(), entity.getUniqueID()));
		});
	}
}
