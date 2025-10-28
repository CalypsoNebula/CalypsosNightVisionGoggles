package settingdust.calypsos_nightvision_goggles.v1_21.item.nightvision_goggles

import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.ItemAttributeModifiers
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.RegularVariant

class NightvisionGogglesItem(variant: NightvisionGogglesVariant) : NightvisionGogglesItem(variant) {

    class Factory : NightvisionGogglesItem.Factory {
        override fun invoke(variant: NightvisionGogglesVariant) =
            settingdust.calypsos_nightvision_goggles.v1_21.item.nightvision_goggles.NightvisionGogglesItem(variant)
    }

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        components: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        components.appendTooltip(stack)
    }

    override fun getDefaultAttributeModifiers(): ItemAttributeModifiers {
        if (variant == RegularVariant) return ItemAttributeModifiers.EMPTY
        val builder = ItemAttributeModifiers.builder()
        builder.add(
            Attributes.ARMOR,
            AttributeModifier(
                CalypsosNightVisionGogglesKeys.NightvisionGoggles,
                2.0,
                AttributeModifier.Operation.ADD_VALUE
            ),
            EquipmentSlotGroup.HEAD
        )
        return builder.build()
    }
}