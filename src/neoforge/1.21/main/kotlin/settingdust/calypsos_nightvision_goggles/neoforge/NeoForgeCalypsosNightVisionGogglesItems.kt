package settingdust.calypsos_nightvision_goggles.neoforge

import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter.Companion.creativeTab
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.neoforge.item.nightvision_goggles.variant.ByteBuddiesVariant
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

object NeoForgeCalypsosNightVisionGogglesItems {
    val ByteBuddiesGoggles by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.ByteBuddiesGoggles
            )
        ) as Holder<NightvisionGogglesItem>
    }

    fun registerItems(register: (ResourceLocation, Item) -> Unit) {
        val factory = ServiceLoaderUtil.findService<NightvisionGogglesItem.Factory>()
        register(CalypsosNightVisionGogglesKeys.ByteBuddiesGoggles, factory(ByteBuddiesVariant).also {
            it.creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
        })
    }
}