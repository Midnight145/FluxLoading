package com.tttsaurus.fluxloading.mixin.early;

import net.minecraft.client.gui.inventory.GuiInventory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.tttsaurus.fluxloading.FluxLoadingConfig;
import com.tttsaurus.fluxloading.core.WorldLoadingScreenOverhaul;

@Mixin(GuiInventory.class)
public class MixinGuiInventory {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void onInit(CallbackInfo ci) {
        if (FluxLoadingConfig.SS_ON_INV) {
            WorldLoadingScreenOverhaul.prepareScreenShot();
        }
    }
}
