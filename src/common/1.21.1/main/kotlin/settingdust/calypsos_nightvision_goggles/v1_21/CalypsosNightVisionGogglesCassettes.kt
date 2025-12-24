package settingdust.calypsos_nightvision_goggles.v1_21

import net.mehvahdjukaar.vista.VistaMod
import net.mehvahdjukaar.vista.common.CassetteItem
import net.mehvahdjukaar.vista.common.CassetteTape
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter

/**
 * 1.21 版本专用的磁带物品注册
 * 需要 Vista 模组支持
 */
object CalypsosNightVisionGogglesCassettes {
    // 懒加载物品 Holder
    val ByteBuddiesCollarCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.ByteBuddiesCollarCassette)
        )
    }
    val ByteBuddiesGogglesCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.ByteBuddiesGogglesCassette)
        )
    }
    val ByteBuddiesTurtleCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.ByteBuddiesTurtleCassette)
        )
    }
    val CandyWorkshopCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.CandyWorkshopCassette)
        )
    }
    val ChickGogglesCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.ChickGogglesCassette)
        )
    }
    val ElaraCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.ElaraCassette)
        )
    }
    val FurnaceSpriteCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.FurnaceSpriteCassette)
        )
    }
    val HayaTimeCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.HayaTimeCassette)
        )
    }
    val MiraCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.MiraCassette)
        )
    }
    val NightVisionGogglesCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.NightVisionGogglesCassette)
        )
    }
    val RepoMoreHeadCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.RepoMoreHeadCassette)
        )
    }
    val VoidPulserCassette by lazy {
        BuiltInRegistries.ITEM.getHolderOrThrow(
            ResourceKey.create(Registries.ITEM, CalypsosNightVisionGogglesKeys.VoidPulserCassette)
        )
    }

    /**
     * 注册所有磁带物品并添加到创造模式标签页
     * 只在 Vista 模组存在时注册
     */
    fun registerCassettes(register: (ResourceLocation, Item) -> Unit) {
        // 检查 Vista 模组是否存在
        if (!LoaderAdapter.isModLoaded("vista")) {
            return
        }

        val cassetteIds = listOf(
            CalypsosNightVisionGogglesKeys.ByteBuddiesCollarCassette,
            CalypsosNightVisionGogglesKeys.ByteBuddiesGogglesCassette,
            CalypsosNightVisionGogglesKeys.ByteBuddiesTurtleCassette,
            CalypsosNightVisionGogglesKeys.CandyWorkshopCassette,
            CalypsosNightVisionGogglesKeys.ChickGogglesCassette,
            CalypsosNightVisionGogglesKeys.ElaraCassette,
            CalypsosNightVisionGogglesKeys.FurnaceSpriteCassette,
            CalypsosNightVisionGogglesKeys.HayaTimeCassette,
            CalypsosNightVisionGogglesKeys.MiraCassette,
            CalypsosNightVisionGogglesKeys.NightVisionGogglesCassette,
            CalypsosNightVisionGogglesKeys.RepoMoreHeadCassette,
            CalypsosNightVisionGogglesKeys.VoidPulserCassette
        )

        // 注册所有磁带物品
        for (cassetteId in cassetteIds) {
            val item = CassetteItem(Item.Properties().rarity(Rarity.RARE).stacksTo(1))
            register(cassetteId, item)
            // 添加到创造模式标签页，使用 supplier 延迟创建 ItemStack
            LoaderAdapter.addToCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES) { parameters ->
                val stack = ItemStack(item)
                // 通过 ResourceKey 创建磁带数据的 Holder
                // 注意：tape ID 需要去掉 "_cassette" 后缀
                val tapePath = cassetteId.path.removeSuffix("_cassette")
                val tapeId = ResourceLocation.fromNamespaceAndPath(cassetteId.namespace, tapePath)
                val tapeKey = ResourceKey.create(VistaMod.CASSETTE_TAPE_REGISTRY_KEY, tapeId)
                val cassetteRegistry = parameters.holders().lookup(VistaMod.CASSETTE_TAPE_REGISTRY_KEY).orElseThrow()
                val tapeHolder = cassetteRegistry.get(tapeKey).orElse(null)
                stack.set(VistaMod.CASSETTE_TAPE_COMPONENT.get(), tapeHolder)
                stack
            }
        }
    }
}
