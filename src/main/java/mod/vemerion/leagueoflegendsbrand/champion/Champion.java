package mod.vemerion.leagueoflegendsbrand.champion;

import java.util.HashMap;
import java.util.Map;

public enum Champion {
	STEVE(0), BRAND(1);

	private static final Map<Integer, Champion> CHAMPIONS = new HashMap<>();

	static {
		for (Champion c : values())
			CHAMPIONS.put(c.getId(), c);
	}

	private int id;

	private Champion(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static Champion get(int id) {
		return CHAMPIONS.get(id);
	}
}
