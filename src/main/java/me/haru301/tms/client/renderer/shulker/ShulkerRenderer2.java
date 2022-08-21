package me.haru301.tms.client.renderer.shulker;

import me.haru301.tms.TooManyShulkers;
import me.haru301.tms.client.renderer.shulker.layer.CustomShulkerLayer;
import me.haru301.tms.entity.AbstractCustomShulkerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerRenderer2 extends MobRenderer<AbstractCustomShulkerEntity, ShulkerModel<AbstractCustomShulkerEntity>>
{
    public static final ResourceLocation SHULKER_TEXTURE = new ResourceLocation(TooManyShulkers.MOD_ID, "textures/entity/shulker/explosive_shulker.png");

    public ShulkerRenderer2(EntityRendererManager renderManagerIn)
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
