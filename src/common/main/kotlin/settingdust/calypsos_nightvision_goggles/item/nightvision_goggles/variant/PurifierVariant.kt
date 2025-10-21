package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.network.chat.Component
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant

object PurifierVariant : NightvisionGogglesVariant {
    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.purifier_goggles.tooltip.description")
    )
}