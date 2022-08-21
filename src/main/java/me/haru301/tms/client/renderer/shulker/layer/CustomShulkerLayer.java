package me.haru301.tms.client.renderer.shulker.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.haru301.tms.client.renderer.shulker.ShulkerRenderer1;
import me.haru301.tms.entity.AbstractCustomShulkerEntity;
import me.haru301.tms.entity.shulker.ArrowShulkerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

public class CustomShulkerLayer extends LayerRenderer<AbstractCustomShulkerEntity, ShulkerModel<AbstractCustomShulkerEntity>>
{
    ResourceLocation location;

    public CustomShulkerLayer(IEntityRenderer<AbstractCustomShulkerEntity, ShulkerModel<AbstractCustomShulkerEntity>> p_i50924_1_) {
        super(p_i50924_1_);
    }

    public CustomShulkerLayer(IEntityRenderer<AbstractCustomShulkerEntity, ShulkerModel<AbstractCustomShulkerEntity>> p_i50924_1_, ResourceLocation location) {
        super(p_i50924_1_);
        this.location = location;
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, AbstractCustomShulkerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 1.0D, 0.0D);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        Quaternion quaternion = entitylivingbaseIn.getAttachmentFacing().getOpposite().getRotation();
        quaternion.conjugate();
        matrixStackIn.rotate(quaternion);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.translate(0.0D, -1.0D, 0.0D);
        DyeColor dyecolor = entitylivingbaseIn.getColor();
        //ResourceLocation resourcelocation = ShulkerRenderer1.SHULKER_TEXTURE;
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(location));
        this.getEntityModel().getHead().render(matrixStackIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F));
        matrixStackIn.pop();
    }
}
