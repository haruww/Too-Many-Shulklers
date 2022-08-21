package me.haru301.tms.init;

import me.haru301.tms.TooManyShulkers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, TooManyShulkers.MOD_ID);

    public static final RegistryObject<Item> ARROW_SHULKER_SPAWN_EGG = REGISTER.register( "arrow_shulker_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ARROW_SHULKER, 0x474747, 0xffde38, new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> EXPLOSIVE_SHULKER_SPAWN_EGG = REGISTER.register( "explosive_shulker_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.EXPLOSIVE_SHULKER, 0x474747, 0xff0000, new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> FIRECHARGE_SHULKER_SPAWN_EGG = REGISTER.register( "firecharge_shulker_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.HOMING_FIREBALL_SHULKER, 0x474747, 0xff7070, new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> POISON_SHULKER_SPAWN_EGG = REGISTER.register( "poison_shulker_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.HOMING_BULLET_SHULKER, 0x474747, 0x16ab39, new Item.Properties().group(ItemGroup.MISC)));
}
