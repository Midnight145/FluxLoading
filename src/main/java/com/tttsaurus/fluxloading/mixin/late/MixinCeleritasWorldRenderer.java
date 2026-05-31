package com.tttsaurus.fluxloading.mixin.late;

import com.gtnewhorizons.angelica.rendering.celeritas.CeleritasWorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.tttsaurus.fluxloading.core.WorldLoadingScreenOverhaul;

@Mixin(value = CeleritasWorldRenderer.class, remap = false)
public class MixinCeleritasWorldRenderer {

    @Inject(
        method = "drawChunkLayer",
        at = @At(
            value = "INVOKE",
            target="Lorg/embeddedt/embeddium/impl/render/terrain/SimpleWorldRenderer;drawChunkLayer(Ljava/lang/Object;DDD)V"))
    public void onDrawChunkLayer(CallbackInfo ci) {
        WorldLoadingScreenOverhaul.onChunkRendered();
    }
}
