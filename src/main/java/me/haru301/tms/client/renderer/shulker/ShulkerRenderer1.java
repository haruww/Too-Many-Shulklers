package me.haru301.tms.client.renderer.shulker;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.haru301.tms.TooManyShulkers;
import me.haru301.tms.client.renderer.shulker.layer.CustomShulkerLayer;
import me.haru301.tms.entity.AbstractCustomShulkerEntity;
import me.haru301.tms.entity.shulker.ArrowShulkerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.layers.ShulkerColorLayer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerRenderer1 extends MobRenderer<AbstractCustomShulkerEntity, ShulkerModel<AbstractCustomShulkerEntity>>
{
    public static final ResourceLocation SHULKER_TEXTURE = new ResourceLocation(TooManyShulkers.MOD_ID, "textures/entity/shulker/arrow_shulker.png");

    public ShulkerRenderer1(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new ShulkerModel<>(), 0.0F);
        this.addLayer(new CustomShulkerLayer(this, SHULKER_TEXTURE));
    }

    @Override
    public ResourceLocation getEntityTexture(AbstractCustomShulkerEntity entity)
    {
        return SHULKER_TEXTURE;
    }
}
