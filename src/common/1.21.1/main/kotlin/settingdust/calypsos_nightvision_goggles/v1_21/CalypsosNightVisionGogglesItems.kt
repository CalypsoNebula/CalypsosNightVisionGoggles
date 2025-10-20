package settingdust.calypsos_nightvision_goggles.v1_21

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter.Companion.creativeTab
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesAccessory
import settingdust.calypsos_nightvision_goggles.util.AccessoryRenderer
import settingdust.calypsos_nightvision_goggles.v1_21.item.nightvision_goggles.NightvisionGogglesItem

class CalypsosNightVisionGogglesItems : CalypsosNightVisionGogglesItems {
    override fun registerItems(register: (ResourceLocation, Item) -> Unit) {
        register(CalypsosNightVisionGogglesKeys.NIGHTVISION_GOGGLES, NightvisionGogglesItem().apply {
            creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
            AccessoryRenderer.registerRenderer(this, NightvisionGogglesAccessory)
        })
    }
}