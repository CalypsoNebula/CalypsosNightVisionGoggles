package settingdust.calypsos_nightvision_goggles

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent

object CalypsosNightVisionGogglesSoundEvents {
    val UiModeSwitch by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.UiModeSwitch)!! }
    val UiExpand by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.UiExpand)!! }
    val UiCollapse by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.UiCollapse)!! }

    val AccessoryPurify by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.AccessoryPurify)!! }
    val AccessoryWatching by lazy { BuiltInRegistries.SOUND_EVENT.get(CalypsosNightVisionGogglesKeys.AccessoryWatching)!! }

    fun registerSoundEvents(register: (ResourceLocation, (ResourceLocation) -> SoundEvent) -> Unit) {
        register(CalypsosNightVisionGogglesKeys.UiModeSwitch) { SoundEvent.createFixedRangeEvent(it, 0f) }
        register(CalypsosNightVisionGogglesKeys.UiExpand) { SoundEvent.createFixedRangeEvent(it, 0f) }
        register(CalypsosNightVisionGogglesKeys.UiCollapse) { SoundEvent.createFixedRangeEvent(it, 0f) }
        register(CalypsosNightVisionGogglesKeys.AccessoryPurify) { SoundEvent.createFixedRangeEvent(it, 16f) }
        register(CalypsosNightVisionGogglesKeys.AccessoryWatching) { SoundEvent.createFixedRangeEvent(it, 16f) }
    }
}