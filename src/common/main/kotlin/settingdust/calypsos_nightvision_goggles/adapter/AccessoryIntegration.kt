package settingdust.calypsos_nightvision_goggles.adapter

import net.minecraft.core.HolderSet
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

interface AccessoryIntegration {
    companion object : AccessoryIntegration {
        private val services by lazy {
            ServiceLoaderUtil
                .findServices<AccessoryIntegration>(required = false)
                .filter { LoaderAdapter.isModLoaded(it.modId) }
        }
        override val modId: String
            get() = throw UnsupportedOperationException()

        override fun init() {
            for (adapter in services) {
                adapter.init()
            }
        }

        override fun getEquipped(entity: LivingEntity, items: HolderSet<out Item>): ItemStack? {
            for (slot in EquipmentSlot.entries) {
                val stack = entity.getItemBySlot(slot)
                if (stack.itemHolder in items) {
                    return stack
                }
            }
            for (adapter in services) {
                val equipped = adapter.getEquipped(entity, items)
                if (equipped != null) return equipped
            }
            return null
        }
    }

    val modId: String

    fun init()

    fun getEquipped(entity: LivingEntity, items: HolderSet<out Item>): ItemStack?
}