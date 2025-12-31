package settingdust.calypsos_nightvision_goggles.adapter.impl

import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem

class EquipmentSlotAccessoryIntegration : AccessoryIntegration {
    override val modId = "minecraft"

    override fun registerItem(item: Holder<NightvisionGogglesItem>) {
    }

    override fun getEquipped(entity: LivingEntity, items: HolderSet<out Item>): ItemStack? {
        for (slot in EquipmentSlot.entries) {
            val stack = entity.getItemBySlot(slot)
            if (stack.itemHolder in items) {
                return stack
            }
        }
        return null
    }
}
