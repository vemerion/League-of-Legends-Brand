package mod.vemerion.leagueoflegendsbrand.network;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Network {

	private static int index = 0;
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(LeagueOfLegendsBrand.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);
	
	public static int index() {
		return index++;
	}
}
