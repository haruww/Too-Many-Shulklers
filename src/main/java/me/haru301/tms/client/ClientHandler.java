package me.haru301.tms.client;

import me.haru301.tms.TooManyShulkers;
import me.haru301.tms.client.renderer.HomingShulkerBulletEntityRenderer;
import me.haru301.tms.client.renderer.shulker.ShulkerRenderer1;
import me.haru301.tms.client.renderer.shulker.ShulkerRenderer2;
import me.haru301.tms.client.renderer.shulker.ShulkerRenderer3;
import me.haru301.tms.client.renderer.shulker.ShulkerRenderer4;
import me.haru301.tms.init.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TooManyShulkers.MOD_ID, value = Dist.CLIENT)
public class ClientHandler
{
    public static void init()
    {
        initEntityRenders();
    }

    private static void initEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ARROW_SHULKER.get(), ShulkerRenderer1::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.EXPLOSIVE_SHULKER.get(), ShulkerRenderer2::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOMING_FIREBALL_SHULKER.get(), ShulkerRenderer3::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOMING_BULLET_SHULKER.get(), ShulkerRenderer4::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOMING_FIREBALL.get(), manager -> new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOMING_BULLET.get(), HomingShulkerBulletEntityRenderer::new);

        //RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOMING_FIREBALL.get(), HomingShulkerBulletEntityRenderer::new);

    }
}
