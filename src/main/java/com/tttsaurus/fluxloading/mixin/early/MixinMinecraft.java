package com.tttsaurus.fluxloading.mixin.early;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tttsaurus.fluxloading.FluxLoadingConfig;
import com.tttsaurus.fluxloading.core.WorldLoadingScreenOverhaul;
import com.tttsaurus.fluxloading.render.GlResourceManager;

@SuppressWarnings("unused")
@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo ci) {
        Logger logger = null;
        for (Field f : Minecraft.class.getDeclaredFields()) {
            if (f.getType()
                .equals(Logger.class) && Modifier.isStatic(f.getModifiers())) {
                f.setAccessible(true);
                try {
                    logger = (Logger) f.get(null);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
        GlResourceManager.disposeAll(logger);
        if (logger == null) {
            return;
        }
        logger.info("FluxLoading: Start disposing OpenGL resources");
        logger.info("FluxLoading: OpenGL resources disposed");
    }

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
    public void loadWorld(WorldClient worldClientIn, String loadingMessage, CallbackInfo ci) {
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world != null) {
            // leave world
            WorldLoadingScreenOverhaul.setDrawOverlay(false);

            // try save screenshot
            WorldLoadingScreenOverhaul.trySaveToLocal(
                WorldLoadingScreenOverhaul.getScreenShot(),
                WorldLoadingScreenOverhaul.LAST_SCREENSHOT_NAME);
            WorldLoadingScreenOverhaul
                .trySaveToLocal(WorldLoadingScreenOverhaul.getThumbnail(), WorldLoadingScreenOverhaul.THUMBNAIL_NAME);
        }
    }

    @WrapOperation(
        method = "displayInGameMenu",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    public void mixin_displayInGameMenu_Minecraft$displayGuiScreen(Minecraft instance, GuiScreen i,
        Operation<Void> original) {
        // when pause game
        WorldLoadingScreenOverhaul.prepareScreenShot();

        original.call(instance, i);
    }

    @Inject(method = "checkGLError", at = @At("HEAD"), cancellable = true)
    public void checkGLError(String message, CallbackInfo ci) {
        if (FluxLoadingConfig.DISABLE_GL_SPAM) {
            ci.cancel();
        }
    }
}
