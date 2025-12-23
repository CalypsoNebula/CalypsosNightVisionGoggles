package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.core.HolderSet
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.npc.InventoryCarrier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.util.event.LivingHurtEvents
import software.bernie.geckolib.model.DefaultedItemGeoModel
import java.util.*

object CuteChickenVariant : NightvisionGogglesVariant {
    override val model =
        DefaultedItemGeoModel<NightvisionGogglesItem>(CalypsosNightVisionGogglesKeys.CuteChickenGoggles)

    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.cute_chicken_goggles.tooltip.description.0"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.cute_chicken_goggles.tooltip.description.1"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.cute_chicken_goggles.tooltip.description.2")
    )

    // 记录每个玩家的下一次下蛋时间（游戏刻）
    private val nextEggTime = mutableMapOf<UUID, Long>()

    init {
        // 激活时减少摔落伤害
        LivingHurtEvents.MODIFY_FALL_DAMAGE.register { entity, _, damage ->
            val stack = AccessoryIntegration.getEquipped(
                entity,
                HolderSet.direct(CalypsosNightVisionGogglesItems.CuteChickenGoggles)
            ) ?: return@register damage

            val mode = stack.mode ?: return@register damage
            if (stack.damageValue >= stack.maxDamage - 1 || !mode.isEnabled(stack, entity)) {
                return@register damage
            }

            // 减少80%摔落伤害
            damage * 0.2f
        }
    }

    override fun tick(stack: ItemStack, owner: LivingEntity) {
        super.tick(stack, owner)

        val level = owner.level()
        val gameTime = level.gameTime

        // 被动效果1: 每天随机时刻下蛋
        if (!level.isClientSide && owner is InventoryCarrier) {
            val uuid = owner.uuid

            // 如果还没有设置下一次下蛋时间，或者到了新的一天，重新随机
            if (!nextEggTime.containsKey(uuid) || gameTime % 24000 == 0L) {
                // 在一天中随机一个时刻 (0-24000 ticks)
                val randomTime = (gameTime / 24000) * 24000 + level.random.nextInt(24000)
                nextEggTime[uuid] = randomTime
            }

            val targetTime = nextEggTime[uuid]!!

            // 检查是否到了下蛋时间（允许5tick的误差）
            if (gameTime >= targetTime && gameTime < targetTime + 5) {
                val eggStack = ItemStack(Items.EGG)
                val addItem = owner.inventory.addItem(eggStack)
                if (addItem.isEmpty) {
                    // 播放鸡叫声
                    level.playSound(
                        owner as? Player,
                        owner.x,
                        owner.y,
                        owner.z,
                        SoundEvents.CHICKEN_AMBIENT,
                        SoundSource.PLAYERS,
                        1.0f,
                        1.0f
                    )
                    // 播放下蛋音效
                    level.playSound(
                        owner as? Player,
                        owner.x,
                        owner.y,
                        owner.z,
                        SoundEvents.CHICKEN_EGG,
                        SoundSource.PLAYERS,
                        1.0f,
                        1.0f
                    )
                }
            }
        }
    }
}
