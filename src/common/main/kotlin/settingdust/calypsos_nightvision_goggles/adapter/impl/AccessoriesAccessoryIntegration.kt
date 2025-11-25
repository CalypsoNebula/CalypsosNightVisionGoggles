package settingdust.calypsos_nightvision_goggles.adapter.impl

import com.mojang.blaze3d.vertex.PoseStack
import io.wispforest.accessories.api.AccessoriesAPI
import io.wispforest.accessories.api.AccessoriesCapability
import io.wispforest.accessories.api.Accessory
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry
import io.wispforest.accessories.api.client.AccessoryRenderer
import io.wispforest.accessories.api.slot.SlotReference
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

open class AccessoriesAccessoryIntegration : AccessoryIntegration {
    class Renderer : AccessoryRenderer {
        override fun <M : LivingEntity> render(
            stack: ItemStack,
            reference: SlotReference,
            matrices: PoseStack,
            model: EntityModel<M>,
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
                reference.entity(),
                matrices,
                multiBufferSource,
                light
            )
        }
    }

    class NightvisionGogglesAccessory(val item: NightvisionGogglesItem) : Accessory {
        override fun tick(stack: ItemStack, slot: SlotReference) {
            item.variant.tick(stack, slot.entity())
        }
    }

    override val modId = "accessories"

    override fun registerItem(item: Holder<NightvisionGogglesItem>) {
        AccessoriesAPI.registerAccessory(item.value(), NightvisionGogglesAccessory(item.value()))
        AccessoriesRendererRegistry.registerRenderer(item.value()) { Renderer() }
    }

    override fun getEquipped(entity: LivingEntity, items: HolderSet<out Item>): ItemStack? =
        AccessoriesCapability.get(entity)?.getFirstEquipped { it.itemHolder in items }?.stack()
}