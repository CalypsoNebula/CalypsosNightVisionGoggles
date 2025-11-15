package settingdust.calypsos_nightvision_goggles.neoforge.adapter

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.core.HolderSet
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesAccessoryRenderer
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.client.CuriosRendererRegistry
import top.theillusivec4.curios.api.client.ICurioRenderer
import top.theillusivec4.curios.api.type.capability.ICurioItem
import kotlin.jvm.optionals.getOrNull

class CuriosAccessoryIntegration : AccessoryIntegration {
    object Renderer : ICurioRenderer {
        override fun <T : LivingEntity, M : EntityModel<T>> render(
            stack: ItemStack,
            slotContext: SlotContext,
            poseStack: PoseStack,
            renderLayerParent: RenderLayerParent<T, M>,
            multiBufferSource: MultiBufferSource,
            light: Int,
            limbSwing: Float,
            limbSwingAmount: Float,
            partialTicks: Float,
            ageInTicks: Float,
            netHeadYaw: Float,
            headPitch: Float
        ) {
            NightvisionGogglesAccessoryRenderer.render(
                stack,
                slotContext.entity(),
                poseStack,
                multiBufferSource,
                light
            )
        }
    }

    class NightVisionGogglesCurio(val item: NightvisionGogglesItem) : ICurioItem {
        override fun curioTick(slotContext: SlotContext, stack: ItemStack) {
            item.variant.tick(stack, slotContext.entity())
        }
    }

    override val modId = CuriosApi.MODID

    override fun init() {
        CuriosApi.registerCurio(
            CalypsosNightVisionGogglesItems.NightvisionGoggles,
            NightVisionGogglesCurio(CalypsosNightVisionGogglesItems.NightvisionGoggles)
        )
        CuriosApi.registerCurio(
            CalypsosNightVisionGogglesItems.PurifierGoggles,
            NightVisionGogglesCurio(CalypsosNightVisionGogglesItems.PurifierGoggles)
        )
        CuriosApi.registerCurio(
            CalypsosNightVisionGogglesItems.TheWatcherGoggles,
            NightVisionGogglesCurio(CalypsosNightVisionGogglesItems.TheWatcherGoggles)
        )
        CuriosApi.registerCurio(
            CalypsosNightVisionGogglesItems.NightOwlGoggles,
            NightVisionGogglesCurio(CalypsosNightVisionGogglesItems.NightOwlGoggles)
        )

        if (LoaderAdapter.isClient) {
            CuriosRendererRegistry.register(CalypsosNightVisionGogglesItems.NightvisionGoggles) {
                Renderer
            }
            CuriosRendererRegistry.register(CalypsosNightVisionGogglesItems.PurifierGoggles) {
                Renderer
            }
            CuriosRendererRegistry.register(CalypsosNightVisionGogglesItems.TheWatcherGoggles) {
                Renderer
            }
            CuriosRendererRegistry.register(CalypsosNightVisionGogglesItems.NightOwlGoggles) {
                Renderer
            }
        }
    }

    override fun getEquipped(entity: LivingEntity, items: HolderSet<out Item>): ItemStack? =
        CuriosApi.getCuriosInventory(entity).flatMap {
            it.findFirstCurio {
                it.itemHolder in items
            }
        }.getOrNull()?.stack
}