package troxi.kraken.controller;

import static org.lwjgl.glfw.GLFW.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Abilities;
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
	private static final Minecraft minecraft = Minecraft.getInstance();
	private static final Logger LOGGER = LogManager.getLogger();
	//Mapping for keys
    public static final KeyMapping krakenMenuKey = new KeyMapping("Kraken Menu", GLFW_KEY_M, MOD_ID);
    public static final KeyMapping krakenPlayerListKey = new KeyMapping("Kraken Player List", GLFW_KEY_N, MOD_ID);
    public static final KeyMapping krakenFlyKey = new KeyMapping("Kraken fly", GLFW_KEY_B, MOD_ID);
    
    @SubscribeEvent
    public static void keyBindings(final KeyInputEvent event) {
    	//Menu key
    	while(krakenMenuKey.consumeClick()) {
    		LOGGER.info("Menu key Press");
    		KrakenMenuOverlay.menuEnabled = !KrakenMenuOverlay.menuEnabled;
    		OverlayRegistry.enableOverlay(KrakenMenuOverlay.getOverlay(), KrakenMenuOverlay.menuEnabled);
    	}
    	//Playerlist with pos key
    	while(krakenPlayerListKey.consumeClick()) {
    		LOGGER.info("Playerlist key Press");
    		KrakenMenuOverlay.playerListEnabled = !KrakenMenuOverlay.playerListEnabled;
    	}
    	//No falling
    	while(krakenFlyKey.consumeClick()) {
    		LOGGER.info("Fly key Press");
    		boolean state = KrakenMenuOverlay.flyEnabled;
    		Abilities abilities = minecraft.player.getAbilities();
    		KrakenMenuOverlay.flyEnabled = !state;
    		abilities.mayfly = state;
    		abilities.invulnerable = state;
    	}
    }
    
    //Inner class for binding the keys
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    class ModEventSubscriber {
        @SubscribeEvent
        static void bindKeys(final FMLClientSetupEvent event) {
        	ClientRegistry.registerKeyBinding(krakenMenuKey);
        	ClientRegistry.registerKeyBinding(krakenPlayerListKey);
        	ClientRegistry.registerKeyBinding(krakenFlyKey);
        }
    }
}

