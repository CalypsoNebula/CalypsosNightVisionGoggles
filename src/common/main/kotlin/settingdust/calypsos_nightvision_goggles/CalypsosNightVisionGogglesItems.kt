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
    val NightvisionGoggles by lazy { BuiltInRegistries.ITEM.get(CalypsosNightVisionGogglesKeys.NightvisionGoggles) as NightvisionGogglesItem }
    val TheWatcherGoggles by lazy { BuiltInRegistries.ITEM.get(CalypsosNightVisionGogglesKeys.TheWatcherGoggles) as NightvisionGogglesItem }
    val PurifierGoggles by lazy { BuiltInRegistries.ITEM.get(CalypsosNightVisionGogglesKeys.PurifierGoggles) as NightvisionGogglesItem }
    val NightOwlGoggles by lazy { BuiltInRegistries.ITEM.get(CalypsosNightVisionGogglesKeys.NightOwlGoggles) as NightvisionGogglesItem }

    fun registerItems(register: (ResourceLocation, Item) -> Unit) {
        val factory = ServiceLoaderUtil.findService<NightvisionGogglesItem.Factory>()
        register(CalypsosNightVisionGogglesKeys.NightvisionGoggles, factory(RegularVariant))
        register(CalypsosNightVisionGogglesKeys.TheWatcherGoggles, factory(WatcherVariant))
        register(CalypsosNightVisionGogglesKeys.PurifierGoggles, factory(PurifierVariant))
        register(CalypsosNightVisionGogglesKeys.NightOwlGoggles, factory(NightOwlVariant))
    }
}