package settingdust.calypsos_nightvision_goggles

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

interface CalypsosNightVisionGogglesItems {
    companion object : CalypsosNightVisionGogglesItems {
        val NIGHTVISION_GOGGLES by lazy { BuiltInRegistries.ITEM.get(CalypsosNightVisionGogglesKeys.NIGHTVISION_GOGGLES) }

        private val implementations = ServiceLoaderUtil.findServices<CalypsosNightVisionGogglesItems>()

        override fun registerItems(register: (ResourceLocation, Item) -> Unit) {
            for (implementation in implementations) {
                implementation.registerItems(register)
            }

            AccessoryIntegration.init()
        }
    }

    fun registerItems(register: (ResourceLocation, Item) -> Unit)
}