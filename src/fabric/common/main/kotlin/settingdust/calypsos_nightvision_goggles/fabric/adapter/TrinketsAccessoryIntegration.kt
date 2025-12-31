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
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
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

        override fun onEquip(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
            item.variant.onEquipped(stack, entity)
        }

        override fun onUnequip(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
            item.variant.onUnequipped(stack, entity)
        }
    }

    override val modId = TrinketsMain.MOD_ID

    override fun getEquipped(entity: LivingEntity, items: HolderSet<out Item>): ItemStack? =
        TrinketsApi.getTrinketComponent(entity).getOrNull()?.getEquipped { it.itemHolder in items }?.firstOrNull()?.b

    override fun registerItem(item: Holder<NightvisionGogglesItem>) {
        TrinketsApi.registerTrinket(item.value(), NightvisionGogglesTrinket(item.value()))
        TrinketRendererRegistry.registerRenderer(item.value(), Renderer)
    }
}