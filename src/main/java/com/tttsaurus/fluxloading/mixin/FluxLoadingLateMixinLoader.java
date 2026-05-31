package com.tttsaurus.fluxloading.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
public class FluxLoadingLateMixinLoader implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.fluxloading.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        List<String> mixins = new ArrayList<>();
        if (loadedMods.contains("angelica")) {
            mixins.add("MixinCeleritasWorldRenderer");
        }
        if (loadedMods.contains("embeddium")) {
            mixins.add("MixinSodiumWorldRenderer");
        }
        if (loadedMods.contains("loading_screen_messages")) {
            mixins.add("MixinLoadingScreen_LoadingScreenMessages");
        }
        if (loadedMods.contains("aether_legacy")) {
            mixins.add("MixinAetherLoadingScreen");
        }
        return mixins;
    }
}
