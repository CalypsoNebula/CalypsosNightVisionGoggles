package settingdust.calypsos_nightvision_goggles

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffect
import settingdust.calypsos_nightvision_goggles.effect.ItsWatchingMobEffect
import settingdust.calypsos_nightvision_goggles.effect.ShadowHopperMobEffect
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

object CalypsosNightVisionGogglesMobEffects {
    val ItsWatching by lazy {
        BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
            ResourceKey.create(
                Registries.MOB_EFFECT,
                CalypsosNightVisionGogglesKeys.ItsWatching
            )
        )
    }

    val ShadowHopper by lazy {
        BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(
            ResourceKey.create(
                Registries.MOB_EFFECT,
                CalypsosNightVisionGogglesKeys.ShadowHopper
            )
        )
    }

    fun registerMobEffects(register: (ResourceLocation, MobEffect) -> Unit) {
        register(CalypsosNightVisionGogglesKeys.ItsWatching, ServiceLoaderUtil.findService<ItsWatchingMobEffect>())
        register(CalypsosNightVisionGogglesKeys.ShadowHopper, ServiceLoaderUtil.findService<ShadowHopperMobEffect>())
    }
}