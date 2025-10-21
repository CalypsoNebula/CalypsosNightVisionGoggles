package settingdust.calypsos_nightvision_goggles.fabric.adapter

import com.mojang.blaze3d.vertex.PoseStack
import dev.emi.trinkets.TrinketsMain
import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.Trinket
import dev.emi.trinkets.api.TrinketsApi
import dev.emi.trinkets.api.client.TrinketRenderer
import dev.emi.trinkets.api.client.TrinketRendererRegistry
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.core.HolderSet
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesAccessoryRenderer
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import kotlin.jvm.optionals.getOrNull

class TrinketsAccessoryIntegration : AccessoryIntegration {
    object Renderer : TrinketRenderer {
        override fun render(
            stack: ItemStack,
            slotReference: SlotReference,
            contextModel: EntityModel<out LivingEntity>,
            matrices: PoseStack,
            vertexConsumers: MultiBufferSource,
            light: Int,
            entity: LivingEntity,
            limbAngle: Float,
            limbDistance: Float,
            tickDelta: Float,
            animationProgress: Float,
            headYaw: Float,
            headPitch: Float
        ) {
            NightvisionGogglesAccessoryRenderer.render(stack, entity, matrices, vertexConsumers, light)
        }
    }

    class NightvisionGogglesTrinket(val item: NightvisionGogglesItem) : Trinket {
        override fun tick(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
            item.variant.tick(stack, entity)
        }
    }

    override val modId = TrinketsMain.MOD_ID

    override fun init() {
        TrinketsApi.registerTrinket(
            CalypsosNightVisionGogglesItems.NightvisionGoggles,
            NightvisionGogglesTrinket(CalypsosNightVisionGogglesItems.NightvisionGoggles)
        )
        TrinketsApi.registerTrinket(
            CalypsosNightVisionGogglesItems.TheWatcherGoggles,
            NightvisionGogglesTrinket(CalypsosNightVisionGogglesItems.TheWatcherGoggles)
        )
        TrinketsApi.registerTrinket(
            CalypsosNightVisionGogglesItems.PurifierGoggles,
            NightvisionGogglesTrinket(CalypsosNightVisionGogglesItems.PurifierGoggles)
        )
        TrinketsApi.registerTrinket(
            CalypsosNightVisionGogglesItems.NightOwlGoggles,
            NightvisionGogglesTrinket(CalypsosNightVisionGogglesItems.NightOwlGoggles)
        )

        TrinketRendererRegistry.registerRenderer(CalypsosNightVisionGogglesItems.NightvisionGoggles, Renderer)
        TrinketRendererRegistry.registerRenderer(CalypsosNightVisionGogglesItems.TheWatcherGoggles, Renderer)
        TrinketRendererRegistry.registerRenderer(CalypsosNightVisionGogglesItems.PurifierGoggles, Renderer)
        TrinketRendererRegistry.registerRenderer(CalypsosNightVisionGogglesItems.NightOwlGoggles, Renderer)
    }

    override fun getEquipped(entity: LivingEntity, items: HolderSet<Item>): ItemStack? =
        TrinketsApi.getTrinketComponent(entity).getOrNull()?.getEquipped { it.itemHolder in items }?.firstOrNull()?.b
}