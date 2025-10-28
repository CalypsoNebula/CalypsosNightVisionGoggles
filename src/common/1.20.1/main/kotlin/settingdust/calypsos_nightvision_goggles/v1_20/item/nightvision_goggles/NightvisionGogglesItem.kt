package settingdust.calypsos_nightvision_goggles.v1_20.item.nightvision_goggles

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.RegularVariant
import java.util.*

open class NightvisionGogglesItem(variant: NightvisionGogglesVariant) : NightvisionGogglesItem(variant) {
    companion object {
        val ARMOR_ID = UUID.fromString("c4255a94-b552-4e33-8dc9-90290efdb544")
    }

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

    override fun getDefaultAttributeModifiers(slot: EquipmentSlot): Multimap<Attribute, AttributeModifier> {
        if (variant == RegularVariant) return ImmutableMultimap.of()
        val builder = ImmutableMultimap.builder<Attribute, AttributeModifier>()
        when (slot) {
            EquipmentSlot.HEAD -> builder.put(
                Attributes.ARMOR,
                AttributeModifier(ARMOR_ID, "Nightvision Goggles", 2.0, AttributeModifier.Operation.ADDITION)
            )

            else -> {}
        }
        return builder.build()
    }
}