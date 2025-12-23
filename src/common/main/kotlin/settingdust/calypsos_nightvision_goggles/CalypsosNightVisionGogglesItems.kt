package settingdust.calypsos_nightvision_goggles

import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter.Companion.creativeTab
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.ByteBuddiesVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.ClockworkVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.CuteChickenVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.NightOwlVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.PurifierVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.RegularVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.RobotChickenVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.WatcherVariant
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

object CalypsosNightVisionGogglesItems {
    val NightvisionGoggles by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.NightvisionGoggles
            )
        ) as Holder<NightvisionGogglesItem>
    }
    val TheWatcherGoggles by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.TheWatcherGoggles
            )
        ) as Holder<NightvisionGogglesItem>
    }
    val PurifierGoggles by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.PurifierGoggles
            )
        ) as Holder<NightvisionGogglesItem>
    }
    val NightOwlGoggles by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.NightOwlGoggles
            )
        ) as Holder<NightvisionGogglesItem>
    }
    val ByteBuddiesGoggles by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.ByteBuddiesGoggles
            )
        ) as Holder<NightvisionGogglesItem>
    }
    val ClockworkGoggles by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.ClockworkGoggles
            )
        ) as Holder<NightvisionGogglesItem>
    }
    val RobotChickenGoggles by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.RobotChickenGoggles
            )
        ) as Holder<NightvisionGogglesItem>
    }
    val CuteChickenGoggles by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.CuteChickenGoggles
            )
        ) as Holder<NightvisionGogglesItem>
    }
    val CyberChickenEgg by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(
                Registries.ITEM,
                CalypsosNightVisionGogglesKeys.CyberChickenEgg
            )
        )
    }

    fun registerItems(register: (ResourceLocation, Item) -> Unit) {
        val factory = ServiceLoaderUtil.findService<NightvisionGogglesItem.Factory>()
        register(CalypsosNightVisionGogglesKeys.NightvisionGoggles, factory(RegularVariant).also {
            it.creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
        })
        register(CalypsosNightVisionGogglesKeys.TheWatcherGoggles, factory(WatcherVariant).also {
            it.creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
        })
        register(CalypsosNightVisionGogglesKeys.PurifierGoggles, factory(PurifierVariant).also {
            it.creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
        })
        register(CalypsosNightVisionGogglesKeys.NightOwlGoggles, factory(NightOwlVariant).also {
            it.creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
        })
        register(CalypsosNightVisionGogglesKeys.ByteBuddiesGoggles, factory(ByteBuddiesVariant).also {
            it.creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
        })
        register(CalypsosNightVisionGogglesKeys.ClockworkGoggles, factory(ClockworkVariant).also {
            it.creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
        })
        register(CalypsosNightVisionGogglesKeys.RobotChickenGoggles, factory(RobotChickenVariant).also {
            it.creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
        })
        register(CalypsosNightVisionGogglesKeys.CuteChickenGoggles, factory(CuteChickenVariant).also {
            it.creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES)
        })
        register(CalypsosNightVisionGogglesKeys.CyberChickenEgg, settingdust.calypsos_nightvision_goggles.item.CyberChickenEggItem.Factory().also {
            it.creativeTab(CreativeModeTabs.FOOD_AND_DRINKS)
        })

        AccessoryIntegration.init()
    }

    object Tags {
        @JvmField
        val Goggles = TagKey.create(Registries.ITEM, CalypsosNightVisionGoggles.id("goggles"))
    }
}