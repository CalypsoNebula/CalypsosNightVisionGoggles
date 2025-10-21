package settingdust.calypsos_nightvision_goggles.v1_20.adapter

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.adapter.ItemStackAdapter

class ItemStackAdapter : ItemStackAdapter {
    override fun ItemStack.isSameItemSameComponents(other: ItemStack) = ItemStack.isSameItemSameTags(this, other)
    override fun ItemStack.hurtNoBreak(
        user: LivingEntity,
        amount: Int
    ) : Boolean = hurt(amount, user.random, user as? ServerPlayer)
}