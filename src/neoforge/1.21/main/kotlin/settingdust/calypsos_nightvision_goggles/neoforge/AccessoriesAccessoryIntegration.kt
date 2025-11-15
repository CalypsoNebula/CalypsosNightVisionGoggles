package settingdust.calypsos_nightvision_goggles.neoforge

import io.wispforest.accessories.api.AccessoriesAPI
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry
import settingdust.calypsos_nightvision_goggles.adapter.impl.AccessoriesAccessoryIntegration

class AccessoriesAccessoryIntegration : AccessoriesAccessoryIntegration() {
    override fun init() {
        super.init()

        AccessoriesAPI.registerAccessory(
            NeoForgeCalypsosNightVisionGogglesItems.ByteBuddiesGoggles.value(),
            NightvisionGogglesAccessory(NeoForgeCalypsosNightVisionGogglesItems.ByteBuddiesGoggles.value())
        )
        AccessoriesRendererRegistry.registerRenderer(NeoForgeCalypsosNightVisionGogglesItems.ByteBuddiesGoggles.value()) { Renderer() }
    }
}