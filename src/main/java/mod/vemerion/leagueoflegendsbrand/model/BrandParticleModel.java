package mod.vemerion.leagueoflegendsbrand.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.LeagueOfLegendsBrand;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * Created using Tabula 8.0.0
 */
public class BrandParticleModel extends Model {
	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(LeagueOfLegendsBrand.MODID,
			"textures/brand_particle.png");
	
    public ModelRenderer particle;

    public BrandParticleModel() {
		super(RenderType::getEntitySolid);
        this.textureWidth = 4;
        this.textureHeight = 4;
        this.particle = new ModelRenderer(this, 0, 0);
        this.particle.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.particle.addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.particle).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}                      