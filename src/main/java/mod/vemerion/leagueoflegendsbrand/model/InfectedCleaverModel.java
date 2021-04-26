package mod.vemerion.leagueoflegendsbrand.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.vemerion.leagueoflegendsbrand.helper.ClientHelper;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created using Tabula 8.0.0
 */
public class InfectedCleaverModel<T extends Entity> extends EntityModel<T> {
    public ModelRenderer cleaverHandle;
    public ModelRenderer cleaver;
    public ModelRenderer cleaverEdge1;
    public ModelRenderer cleaverEdge2;
    public ModelRenderer cleaverEdge3;
    public ModelRenderer cleaverEdge4;

    public InfectedCleaverModel() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.cleaverEdge3 = new ModelRenderer(this, 52, 124);
        this.cleaverEdge3.setRotationPoint(0.0F, 2.0F, -9.1F);
        this.cleaverEdge3.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(cleaverEdge3, 0.7853981633974483F, 0.0F, 0.0F);
        this.cleaverEdge1 = new ModelRenderer(this, 52, 124);
        this.cleaverEdge1.setRotationPoint(0.0F, 2.0F, -1.4F);
        this.cleaverEdge1.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(cleaverEdge1, 0.7853981633974483F, 0.0F, 0.0F);
        this.cleaverHandle = new ModelRenderer(this, 0, 118);
        this.cleaverHandle.setRotationPoint(1.0F, 8.0F, -1.0F);
        this.cleaverHandle.addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.cleaver = new ModelRenderer(this, 20, 109);
        this.cleaver.setRotationPoint(0.0F, 1.0F, -3.0F);
        this.cleaver.addBox(-1.0F, -3.0F, -14.0F, 2.0F, 5.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.cleaverEdge2 = new ModelRenderer(this, 52, 124);
        this.cleaverEdge2.setRotationPoint(0.0F, 2.0F, -5.4F);
        this.cleaverEdge2.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(cleaverEdge2, 0.7853981633974483F, 0.0F, 0.0F);
        this.cleaverEdge4 = new ModelRenderer(this, 52, 124);
        this.cleaverEdge4.setRotationPoint(0.0F, 2.0F, -12.4F);
        this.cleaverEdge4.addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(cleaverEdge4, 0.7853981633974483F, 0.0F, 0.0F);
        this.cleaver.addChild(this.cleaverEdge3);
        this.cleaver.addChild(this.cleaverEdge1);
        this.cleaverHandle.addChild(this.cleaver);
        this.cleaver.addChild(this.cleaverEdge2);
        this.cleaver.addChild(this.cleaverEdge4);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.cleaverHandle).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	cleaverHandle.rotateAngleX = ageInTicks / 3;
    }
    
    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
    	cleaverHandle.rotateAngleY = ClientHelper.toRad(entityIn.getYaw(partialTick));
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

	public void setVisible(boolean b) {
		cleaverHandle.showModel = b;
	}
}
