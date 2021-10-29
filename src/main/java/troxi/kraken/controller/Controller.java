package troxi.kraken.controller;

import static org.lwjgl.glfw.GLFW.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import troxi.kraken.gui.KrakenMenuOverlay;

import static troxi.kraken.Kraken.MOD_ID;

//Not omitting the bus to clarify which one is used
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class Controller {
	private static final Logger LOGGER = LogManager.getLogger();
	//Mapping for keys
    public static final KeyMapping krakenMenuKey = new KeyMapping("Kraken Menu", GLFW_KEY_M, MOD_ID);
    public static final KeyMapping krakenTestKey = new KeyMapping("Kraken Test", GLFW_KEY_N, MOD_ID);
    
    @SubscribeEvent
    public static void keyBindings(final KeyInputEvent event) {
    	//Menu key
    	while(krakenMenuKey.consumeClick()) {
    		LOGGER.info("Menu key Press");
    		KrakenMenuOverlay.menuEnabled = !KrakenMenuOverlay.menuEnabled;
    		OverlayRegistry.enableOverlay(KrakenMenuOverlay.getOverlay(), KrakenMenuOverlay.menuEnabled);
    	}
    	//Test key
    	while(krakenTestKey.consumeClick()) {
    		LOGGER.info("Test key Press");
    		KrakenMenuOverlay.testEnabled = !KrakenMenuOverlay.testEnabled;
    	}
    }
    
    //Inner class for binding the keys
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    class ModEventSubscriber {
        @SubscribeEvent
        static void bindKeys(final FMLClientSetupEvent event) {
        	ClientRegistry.registerKeyBinding(krakenMenuKey);
        	ClientRegistry.registerKeyBinding(krakenTestKey);
        }
    }
}

