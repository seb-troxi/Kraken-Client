package troxi.kraken.gui;

import static troxi.kraken.Kraken.MOD_ID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import troxi.kraken.controller.Controller;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KrakenMenuOverlay {
    private static final Minecraft minecraft = Minecraft.getInstance();
    
    private static final int RED = -65536;
    private static final int GREEN = -16711936;
    
    private static IIngameOverlay krakenMenuOverlay = null;
    public static boolean menuEnabled = false;
    public static boolean testEnabled = false;
    
    private static final int NUMBER_FUNCTIONS = 2;
    
    @SubscribeEvent
    public static void setupOverlay(final FMLClientSetupEvent event) {
    	//Register our overlay to the OverlayRegistry. The lambda function is our overlay code.
        krakenMenuOverlay = OverlayRegistry.registerOverlayTop("KrakenMenu", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        	Player player = !(minecraft.getCameraEntity() instanceof Player) ? null : (Player)minecraft.getCameraEntity();
        	int menuWidth = minecraft.font.width("Functionality Feature: (KEY)");
            if (!minecraft.options.hideGui && !minecraft.options.renderDebug && player != null)
            {
            	String menuText = "Kraken Menu";
            	GuiComponent.fill(mStack, screenWidth-1, minecraft.font.lineHeight, screenWidth-menuWidth-1, 1, -14671840);
            	GuiComponent.fill(mStack, screenWidth-1, minecraft.font.lineHeight*2*NUMBER_FUNCTIONS+minecraft.font.lineHeight, screenWidth-menuWidth-1, minecraft.font.lineHeight+2, -1873784752);
            	//minecraft.font.draw(mStack, text, offsetX, offsetY, colorInDec);
            	minecraft.font.draw(mStack, menuText, (float)screenWidth-menuWidth, 2.0F, 14737632);
            	
            	//Test Feature
            	minecraft.font.draw(mStack, "Test Feature: ("+Controller.krakenTestKey.getKey()+")", (float)screenWidth-menuWidth, minecraft.font.lineHeight+2, testEnabled ? GREEN:RED);
            }
        });
        OverlayRegistry.enableOverlay(krakenMenuOverlay, menuEnabled);
    }
    
    public static IIngameOverlay getOverlay() {
    	return krakenMenuOverlay;
    }
}
