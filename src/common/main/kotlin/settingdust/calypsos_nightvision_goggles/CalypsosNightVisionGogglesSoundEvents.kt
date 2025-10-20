package settingdust.calypsos_nightvision_goggles

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent

object CalypsosNightVisionGogglesSoundEvents {
    val UI_MODE_SWITCH by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.UI_MODE_SWITCH)!! }
    val UI_EXPAND by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.UI_EXPAND)!! }
    val UI_COLLAPSE by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.UI_COLLAPSE)!! }

    fun registerSoundEvents(register: (ResourceLocation, (ResourceLocation) -> SoundEvent) -> Unit) {
        register(CalypsosNightVisionGogglesKeys.UI_MODE_SWITCH) { SoundEvent.createFixedRangeEvent(it, 0f) }
        register(CalypsosNightVisionGogglesKeys.UI_EXPAND) { SoundEvent.createFixedRangeEvent(it, 0f) }
        register(CalypsosNightVisionGogglesKeys.UI_COLLAPSE) { SoundEvent.createFixedRangeEvent(it, 0f) }
    }
}