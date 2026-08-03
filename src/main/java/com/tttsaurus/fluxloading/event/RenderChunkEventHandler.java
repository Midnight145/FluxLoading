package com.tttsaurus.fluxloading.event;

import java.util.function.Consumer;

import com.gtnewhorizons.angelica.event.RenderChunkEvent;
import com.tttsaurus.fluxloading.core.WorldLoadingScreenOverhaul;

public class RenderChunkEventHandler {

    private static final Consumer<RenderChunkEvent> listener = renderChunkEvent -> {
        WorldLoadingScreenOverhaul.onChunkRendered();
    };

    public static void register() {
        RenderChunkEvent.BUS.addListener(listener);
    }
}
