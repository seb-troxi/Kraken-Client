package troxi.kraken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static troxi.kraken.Kraken.MOD_ID;

@Mod(MOD_ID)
public class Kraken {
	public static final String MOD_ID = "kraken";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();


    public Kraken() {
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::onClientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
    
    private void onClientSetup(final FMLClientSetupEvent event) {
    	LOGGER.info("BLOOOCKKK of DIRT >> {}", Blocks.DIRT.getRegistryName());
    }
   
}
