package troxi.kraken.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.TransientEntitySectionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import static troxi.kraken.Kraken.MOD_ID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ExtraInfoOverlay {
    private static final Minecraft minecraft = Minecraft.getInstance();

	private static final Logger LOGGER = LogManager.getLogger();
    
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
            	
            	if(KrakenMenuOverlay.playerListEnabled) {
            		final int startLineHeight = 14+minecraft.font.lineHeight;
	            	final TransientEntitySectionManager<Entity> entityStorage = ObfuscationReflectionHelper.getPrivateValue(ClientLevel.class, minecraft.level, "entityStorage");
	            	Iterable<Entity> map = entityStorage.getEntityGetter().getAll();
	            	int index = 0;
	            	for(Entity e : map) {
	            		if(e instanceof RemotePlayer) {
	            			String info = e.getScoreboardName()+" X: "+e.getBlockX()+" Y: "+e.getBlockY()+" Z: "+e.getBlockZ();
	            			GuiComponent.fill(mStack, 1, startLineHeight+(minecraft.font.lineHeight+2)*index, 2 + minecraft.font.width(info), startLineHeight+(minecraft.font.lineHeight+2)*(index+1), -1873784752);
	            			minecraft.font.draw(mStack, info, 2.0F, startLineHeight+index*(minecraft.font.lineHeight+2), 2996818);
	            			++index;
	            		}
	            	}
            	}
            }
        });
    }
}