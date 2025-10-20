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
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesAccessory

class AccessoriesAccessoryIntegration : AccessoryIntegration {
    object Renderer : AccessoryRenderer {
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
            settingdust.calypsos_nightvision_goggles.util.AccessoryRenderer.render(
                stack,
                reference.entity(),
                matrices,
                multiBufferSource,
                light
            )
        }
    }

    override val modId = "accessories"
    override fun init() {
        AccessoriesAPI.registerAccessory(CalypsosNightVisionGogglesItems.NIGHTVISION_GOGGLES, object : Accessory {
            override fun tick(stack: ItemStack, slot: SlotReference) {
                NightvisionGogglesAccessory.tick(stack, slot.entity())
            }
        })

        AccessoriesRendererRegistry.registerRenderer(CalypsosNightVisionGogglesItems.NIGHTVISION_GOGGLES) { Renderer }
    }

    override fun getEquipped(entity: LivingEntity, item: Item) =
        AccessoriesCapability.get(entity)?.getFirstEquipped(item)?.stack()
}