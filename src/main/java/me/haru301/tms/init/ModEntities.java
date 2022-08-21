package me.haru301.tms.init;

import me.haru301.tms.TooManyShulkers;
import me.haru301.tms.entity.projectile.HomingBulletEntity;
import me.haru301.tms.entity.projectile.HomingFireballEntity;
import me.haru301.tms.entity.shulker.ArrowShulkerEntity;
import me.haru301.tms.entity.shulker.ExplosiveShulkerEntity;
import me.haru301.tms.entity.shulker.HomingBulletShulkerEntity;
import me.haru301.tms.entity.shulker.HomingFireBallShulkerEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, TooManyShulkers.MOD_ID);

    public static final RegistryObject<EntityType<ArrowShulkerEntity>> ARROW_SHULKER = REGISTER.register("arrow_shulker", () -> EntityType.Builder.create(ArrowShulkerEntity::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().build("arrow_shulker"));
    public static final RegistryObject<EntityType<ExplosiveShulkerEntity>> EXPLOSIVE_SHULKER = REGISTER.register("explosive_shulker", () -> EntityType.Builder.create(ExplosiveShulkerEntity::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().build("explosive_shulker"));
    public static final RegistryObject<EntityType<HomingFireBallShulkerEntity>> HOMING_FIREBALL_SHULKER = REGISTER.register("homing_fireball_shulker", () -> EntityType.Builder.create(HomingFireBallShulkerEntity::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().build("homing_fireball_shulker"));
    public static final RegistryObject<EntityType<HomingBulletShulkerEntity>> HOMING_BULLET_SHULKER = REGISTER.register("homing_bullet_shulker", () -> EntityType.Builder.create(HomingBulletShulkerEntity::new, EntityClassification.MONSTER).size(1.0f, 1.0f).immuneToFire().build("homing_bullet_shulker"));



    public static final RegistryObject<EntityType<HomingFireballEntity>> HOMING_FIREBALL = REGISTER.register("homing_fireball", () -> EntityType.Builder.<HomingFireballEntity>create(HomingFireballEntity::new, EntityClassification.MISC).size(1.0f, 1.0f).build("homing_fireball"));
    public static final RegistryObject<EntityType<HomingBulletEntity>> HOMING_BULLET = REGISTER.register("homing_bullet", () -> EntityType.Builder.<HomingBulletEntity>create(HomingBulletEntity::new, EntityClassification.MISC).size(1.0f, 1.0f).build("homing_bullet"));

}
