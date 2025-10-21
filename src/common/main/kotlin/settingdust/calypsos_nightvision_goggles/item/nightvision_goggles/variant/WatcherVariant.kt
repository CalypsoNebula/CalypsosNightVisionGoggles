package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.network.chat.Component
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant

object WatcherVariant : NightvisionGogglesVariant {
    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.the_watcher_goggles.tooltip.description")
    )
}