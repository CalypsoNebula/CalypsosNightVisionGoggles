package settingdust.calypsos_nightvision_goggles.v1_20.item.nightvision_goggles

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant

class NightvisionGogglesItem(variant: NightvisionGogglesVariant) :
    NightvisionGogglesItem(variant) {
    class Factory : NightvisionGogglesItem.Factory {
        override fun invoke(variant: NightvisionGogglesVariant) =
            settingdust.calypsos_nightvision_goggles.v1_20.item.nightvision_goggles.NightvisionGogglesItem(variant)
    }

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        components: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        components.appendTooltip(stack)
    }
}