package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.core.HolderSet
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.entity.npc.InventoryCarrier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.AABB
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil
import settingdust.calypsos_nightvision_goggles.util.event.LivingHurtEvents
import software.bernie.geckolib.model.DefaultedItemGeoModel
import java.util.*

object VistaVariant : NightvisionGogglesVariant {
    // 音乐唱片标签
    val MUSIC_DISCS_TAG = TagKey.create(
        Registries.ITEM,
        CalypsosNightVisionGoggles.id("music_discs")
    )

    // 磁带标签
    val CASSETTES_TAG = TagKey.create(
        Registries.ITEM,
        CalypsosNightVisionGoggles.id("cassettes")
    )
    override val model =
        DefaultedItemGeoModel<NightvisionGogglesItem>(CalypsosNightVisionGogglesKeys.VistaGoggles)

    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.vista_goggles.tooltip.description.0"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.vista_goggles.tooltip.description.1"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.vista_goggles.tooltip.description.2")
    )

    // 记录每个玩家的当前速度加成值，用于检测变化
    val currentSpeedBonus = mutableMapOf<UUID, Double>()

    init {
        // 受到伤害时高亮周围敌对生物
        LivingHurtEvents.MODIFY_DAMAGE.register { entity, _, _, damage ->
            val stack = AccessoryIntegration.getEquipped(
                entity,
                HolderSet.direct(CalypsosNightVisionGogglesItems.VistaGoggles)
            ) ?: return@register damage

            val mode = stack.mode ?: return@register damage
            if (stack.damageValue >= stack.maxDamage - 1 || !mode.isEnabled(stack, entity)) {
                return@register damage
            }

            // 高亮30格半径内所有敌对生物
            val level = entity.level()
            val aabb = AABB(
                entity.x - 30.0, entity.y - 30.0, entity.z - 30.0,
                entity.x + 30.0, entity.y + 30.0, entity.z + 30.0
            )

            val hostileMobs = level.getEntitiesOfClass(Mob::class.java, aabb) { mob ->
                mob.type.category == MobCategory.MONSTER
            }

            for (mob in hostileMobs) {
                mob.addEffect(
                    MobEffectAdapter.createMobEffectInstance(
                        MobEffectAdapter.Glowing,
                        10 * 20, // 10秒发光
                        0,
                        ambient = false,
                        visible = false,
                        showIcon = false
                    )
                )
            }

            damage
        }
    }

    override fun tick(stack: ItemStack, owner: LivingEntity) {
        super.tick(stack, owner)

        val mode = stack.mode!!
        if (stack.damageValue >= stack.maxDamage - 1 || !mode.isEnabled(stack, owner)) {
            // 移除速度修饰符
            SpeedModifierHandler.removeSpeedModifier(owner)
            return
        }

        // 计算背包中唱片和磁带的数量
        val inventory = (owner as? InventoryCarrier)?.inventory ?: (owner as? Player)?.inventory ?: return
        val uniqueDiscs = mutableSetOf<Item>()
        val uniqueCassettes = mutableSetOf<Item>()

        // 检查背包中的所有物品
        for (i in 0 until inventory.containerSize) {
            val itemStack = inventory.getItem(i)
            if (itemStack.isEmpty) continue

            // 检查是否是唱片（使用自定义标签，包含c:music_discs和minecraft:music_discs）
            if (itemStack.`is`(MUSIC_DISCS_TAG)) {
                uniqueDiscs.add(itemStack.item)
            }
            // 检查是否是磁带
            if (itemStack.`is`(CASSETTES_TAG)) {
                uniqueCassettes.add(itemStack.item)
            }
        }

        // 计算速度加成
        // 每个唱片10%移速，每个磁带20%移速
        // 速度1级=20%，所以：1个唱片=0级(10%)，2个唱片=1级(20%+10%=30%)，以此类推
        // 1个磁带=1级(20%)，2个磁带=2级(40%)，以此类推
        val discCount = uniqueDiscs.size
        val cassetteCount = uniqueCassettes.size

        // 计算总速度加成：唱片每个10% + 磁带每个20%
        val totalSpeedBonus = discCount * 0.10 + cassetteCount * 0.20

        // 更新AttributeModifier
        SpeedModifierHandler.updateSpeedModifier(owner, totalSpeedBonus)

        // 激活时自动走上1格高的方块
        SpeedModifierHandler.updateStepHeight(owner, mode.isEnabled(stack, owner))
    }

    /**
     * 版本特定的速度修饰符处理器
     */
    interface SpeedModifierHandler {
        companion object : SpeedModifierHandler by ServiceLoaderUtil.findService<SpeedModifierHandler>()

        fun updateSpeedModifier(entity: LivingEntity, speedBonus: Double)
        fun removeSpeedModifier(entity: LivingEntity)
        fun updateStepHeight(entity: LivingEntity, enabled: Boolean)
    }
}
