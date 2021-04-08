package mod.vemerion.leagueoflegendsbrand.renderer;

import mod.vemerion.leagueoflegendsbrand.model.CubeModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class CubeEntityRenderer<T extends Entity> extends EntityRenderer<T> {
	private final CubeModel CUBE = new CubeModel();

	protected CubeEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}
	
	protected CubeModel getCube() {
		return CUBE;
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return getCube().getTexture();
	}

}
