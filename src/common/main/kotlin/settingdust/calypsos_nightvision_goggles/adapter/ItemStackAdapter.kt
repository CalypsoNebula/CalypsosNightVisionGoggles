package settingdust.calypsos_nightvision_goggles.adapter

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

interface ItemStackAdapter {
    companion object : ItemStackAdapter by ServiceLoaderUtil.findService()

    fun ItemStack.isSameItemSameComponents(other: ItemStack): Boolean

    fun ItemStack.hurtNoBreak(user: LivingEntity, amount: Int = 1): Boolean
}