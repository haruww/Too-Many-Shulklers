package me.haru301.tms.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.world.World;

public abstract class AbstractCustomShulkerEntity extends ShulkerEntity
{

    public AbstractCustomShulkerEntity(EntityType<? extends ShulkerEntity> type, World world)
    {
        super(type, world);
    }

    @Override
    protected void registerGoals()
    {

    }

    @Override
    protected boolean tryTeleportToNewPosition()
    {
        return true;
    }
}
