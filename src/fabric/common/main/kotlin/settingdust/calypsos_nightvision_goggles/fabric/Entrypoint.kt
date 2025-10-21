package settingdust.calypsos_nightvision_goggles.fabric

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesMobEffects
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesSoundEvents
import settingdust.calypsos_nightvision_goggles.adapter.Entrypoint

object Entrypoint {
    init {
        requireNotNull(CalypsosNightVisionGoggles)
        Entrypoint.construct()
    }

    fun init() {
        CalypsosNightVisionGogglesMobEffects.registerMobEffects { id, effect ->
            Registry.register(BuiltInRegistries.MOB_EFFECT, id, effect)
        }
        CalypsosNightVisionGogglesItems.registerItems { id, item ->
            Registry.register(BuiltInRegistries.ITEM, id, item)
        }
        CalypsosNightVisionGogglesSoundEvents.registerSoundEvents { id, factory ->
            Registry.register(BuiltInRegistries.SOUND_EVENT, id, factory(id))
        }
        Entrypoint.init()
    }

    fun clientInit() {
        Entrypoint.clientInit()
    }
}
