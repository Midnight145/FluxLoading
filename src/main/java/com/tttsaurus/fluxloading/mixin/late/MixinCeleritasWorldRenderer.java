package com.tttsaurus.fluxloading.mixin.late;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.tttsaurus.fluxloading.core.WorldLoadingScreenOverhaul;

@Pseudo
@Mixin(targets = "com.gtnewhorizons.angelica.rendering.celeritas.CeleritasWorldRenderer", remap = false)
public class MixinCeleritasWorldRenderer {

    @Inject(method = "drawChunkLayer", at = @At("RETURN"))
    public void onDrawChunkLayer(CallbackInfo ci) {
        WorldLoadingScreenOverhaul.onChunkRendered();
    }
}
