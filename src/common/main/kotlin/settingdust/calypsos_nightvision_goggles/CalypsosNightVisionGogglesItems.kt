package settingdust.calypsos_nightvision_goggles

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.NightOwlVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.PurifierVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.RegularVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.WatcherVariant
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

object CalypsosNightVisionGogglesItems {
    val NIGHTVISION_GOGGLES by lazy { BuiltInRegistries.ITEM.get(CalypsosNightVisionGogglesKeys.NIGHTVISION_GOGGLES) }
    val THE_WATCHER_GOGGLES by lazy { BuiltInRegistries.ITEM.get(CalypsosNightVisionGogglesKeys.THE_WATCHER_GOGGLES) }
    val PURIFIER_GOGGLES by lazy { BuiltInRegistries.ITEM.get(CalypsosNightVisionGogglesKeys.PURIFIER_GOGGLES) }
    val NIGHT_OWL_GOGGLES by lazy { BuiltInRegistries.ITEM.get(CalypsosNightVisionGogglesKeys.NIGHT_OWL_GOGGLES) }

    fun registerItems(register: (ResourceLocation, Item) -> Unit) {
        val factory = ServiceLoaderUtil.findService<NightvisionGogglesItem.Factory>()
        register(CalypsosNightVisionGogglesKeys.NIGHTVISION_GOGGLES, factory(RegularVariant))
        register(CalypsosNightVisionGogglesKeys.THE_WATCHER_GOGGLES, factory(WatcherVariant))
        register(CalypsosNightVisionGogglesKeys.PURIFIER_GOGGLES, factory(PurifierVariant))
        register(CalypsosNightVisionGogglesKeys.NIGHT_OWL_GOGGLES, factory(NightOwlVariant))
    }
}