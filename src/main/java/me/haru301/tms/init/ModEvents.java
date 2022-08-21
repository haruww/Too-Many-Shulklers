package me.haru301.tms.init;

import me.haru301.tms.TooManyShulkers;
import me.haru301.tms.entity.shulker.ArrowShulkerEntity;
import me.haru301.tms.entity.shulker.ExplosiveShulkerEntity;
import me.haru301.tms.entity.shulker.HomingBulletShulkerEntity;
import me.haru301.tms.entity.shulker.HomingFireBallShulkerEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TooManyShulkers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents
{
    @SubscribeEvent
    public static void initAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.ARROW_SHULKER.get(), ArrowShulkerEntity.getAttributes().create());
        event.put(ModEntities.EXPLOSIVE_SHULKER.get(), ExplosiveShulkerEntity.getAttributes().create());
        event.put(ModEntities.HOMING_FIREBALL_SHULKER.get(), HomingFireBallShulkerEntity.getAttributes().create());
        event.put(ModEntities.HOMING_BULLET_SHULKER.get(), HomingBulletShulkerEntity.getAttributes().create());
    }
}
