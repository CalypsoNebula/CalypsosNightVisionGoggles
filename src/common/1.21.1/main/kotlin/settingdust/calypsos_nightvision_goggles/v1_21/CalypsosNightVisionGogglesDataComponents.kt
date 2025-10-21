package settingdust.calypsos_nightvision_goggles.v1_21

import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler
import settingdust.calypsos_nightvision_goggles.v1_21.util.CalypsosNightVisionGogglesItemsCodecs
import settingdust.calypsos_nightvision_goggles.v1_21.util.CalypsosNightVisionGogglesItemsStreamCodecs

object CalypsosNightVisionGogglesDataComponents {
    val MODE by lazy { BuiltInRegistries.DATA_COMPONENT_TYPE.get(CalypsosNightVisionGogglesKeys.Mode) as DataComponentType<NightvisionGogglesModeHandler.Mode> }

    fun registerDataComponents(register: (ResourceLocation, DataComponentType<*>) -> Unit) {
        register(
            CalypsosNightVisionGogglesKeys.Mode,
            DataComponentType.builder<NightvisionGogglesModeHandler.Mode>()
                .networkSynchronized(CalypsosNightVisionGogglesItemsStreamCodecs.NIGHTVISION_GOGGLES_MODE)
                .persistent(CalypsosNightVisionGogglesItemsCodecs.NIGHTVISION_GOGGLES_MODE)
                .build()
        )
    }
}