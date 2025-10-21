package settingdust.calypsos_nightvision_goggles.v1_21.adapter

import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.adapter.ItemStackAdapter

class ItemStackAdapter : ItemStackAdapter {
    override fun ItemStack.isSameItemSameComponents(other: ItemStack) =
        ItemStack.isSameItemSameComponents(this, other)

    override fun ItemStack.hurtNoBreak(
        user: LivingEntity,
        amount: Int
    ): Boolean {
        var result = false
        hurtAndBreak(1, user.level() as ServerLevel, user as? ServerPlayer) {
            result = true
        }
        return result
    }
}