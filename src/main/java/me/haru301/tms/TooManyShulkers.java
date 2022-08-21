package me.haru301.tms;

import me.haru301.tms.client.ClientHandler;
import me.haru301.tms.entity.shulker.ArrowShulkerEntity;
import me.haru301.tms.init.ModEntities;
import me.haru301.tms.init.ModItems;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("tms")
public class TooManyShulkers
{
    public static final String MOD_ID = "tms";
    public static final Logger LOGGER = LogManager.getLogger();

    public TooManyShulkers()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        ModEntities.REGISTER.register(bus);
        ModItems.REGISTER.register(bus);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void onClientSetup(final FMLClientSetupEvent event)
    {
        ClientHandler.init();
    }


}
