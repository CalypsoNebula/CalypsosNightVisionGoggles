package settingdust.calypsos_nightvision_goggles

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent

object CalypsosNightVisionGogglesSoundEvents {
    val UI_MODE_SWITCH by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.UiModeSwitch)!! }
    val UI_EXPAND by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.UiExpand)!! }
    val UI_COLLAPSE by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.UiCollapse)!! }

    fun registerSoundEvents(register: (ResourceLocation, (ResourceLocation) -> SoundEvent) -> Unit) {
        register(CalypsosNightVisionGogglesKeys.UiModeSwitch) { SoundEvent.createFixedRangeEvent(it, 0f) }
        register(CalypsosNightVisionGogglesKeys.UiExpand) { SoundEvent.createFixedRangeEvent(it, 0f) }
        register(CalypsosNightVisionGogglesKeys.UiCollapse) { SoundEvent.createFixedRangeEvent(it, 0f) }
    }
}