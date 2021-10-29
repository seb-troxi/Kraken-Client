package troxi.kraken.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import static troxi.kraken.Kraken.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExtraInfoOverlay {
    private static final Minecraft minecraft = Minecraft.getInstance();
    
    @SubscribeEvent
    public static void setupOverlay(final FMLClientSetupEvent event) {
    	//Register our overlay to the OverlayRegistry. The lambda function is our overlay code.
    	OverlayRegistry.registerOverlayTop("KrakenInfo", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        	Player player = !(minecraft.getCameraEntity() instanceof Player) ? null : (Player)minecraft.getCameraEntity();
            if (!minecraft.options.hideGui && !minecraft.options.renderDebug && player != null)
            {
            	String fps = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, minecraft, "fps").toString();
            	String cords = "X: "+ (int)player.getX() +" Y: "+ (int)player.getY() +" Z: "+ (int)player.getZ();
            	//gui.blit(mStack, posX, posY, blitOffsetX, BlitOffsetY, blitWidth, blitHeight)
            	GuiComponent.fill(mStack, 1, 1, 2 + minecraft.font.width(cords) + 1, 14+minecraft.font.lineHeight, -1873784752);
            	//minecraft.font.draw(mStack, text, offsetX, offsetY, colorInDec);
            	minecraft.font.draw(mStack, "FPS: "+fps, 2.0F, 2.0F, 8762623);
            	minecraft.font.draw(mStack, cords, 2.0F, 12.0F, 8762623);
            }
        });
    }
}