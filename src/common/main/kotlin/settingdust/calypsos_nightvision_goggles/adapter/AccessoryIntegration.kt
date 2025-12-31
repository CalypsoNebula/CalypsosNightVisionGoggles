package settingdust.calypsos_nightvision_goggles.adapter

import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
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

        fun init() {
            registerItem(CalypsosNightVisionGogglesItems.NightvisionGoggles)
            registerItem(CalypsosNightVisionGogglesItems.PurifierGoggles)
            registerItem(CalypsosNightVisionGogglesItems.TheWatcherGoggles)
            registerItem(CalypsosNightVisionGogglesItems.NightOwlGoggles)
            registerItem(CalypsosNightVisionGogglesItems.ByteBuddiesGoggles)
            registerItem(CalypsosNightVisionGogglesItems.ClockworkGoggles)
            registerItem(CalypsosNightVisionGogglesItems.RobotChickenGoggles)
            registerItem(CalypsosNightVisionGogglesItems.CuteChickenGoggles)
            registerItem(CalypsosNightVisionGogglesItems.VistaGoggles)
        }

        override fun getEquipped(entity: LivingEntity, items: HolderSet<out Item>): ItemStack? {
            for (adapter in services) {
                val equipped = adapter.getEquipped(entity, items)
                if (equipped != null) return equipped
            }
            return null
        }

        override fun registerItem(item: Holder<NightvisionGogglesItem>) {
            for (adapter in services) {
                adapter.registerItem(item)
            }
        }
    }

    val modId: String

    fun getEquipped(entity: LivingEntity, items: HolderSet<out Item>): ItemStack?

    fun registerItem(item: Holder<NightvisionGogglesItem>)
}