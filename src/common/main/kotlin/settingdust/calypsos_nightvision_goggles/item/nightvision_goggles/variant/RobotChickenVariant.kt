package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.core.HolderSet
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.tags.TagKey
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.util.event.PlayerBlockBreakCallback
import software.bernie.geckolib.model.DefaultedItemGeoModel

object RobotChickenVariant : NightvisionGogglesVariant {
    val ORES_TAG = TagKey.create(
        Registries.BLOCK,
        CalypsosNightVisionGoggles.id("ores")
    )

    override val model =
        DefaultedItemGeoModel<NightvisionGogglesItem>(CalypsosNightVisionGogglesKeys.RobotChickenGoggles)

    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.robot_chicken_goggles.tooltip.description.0"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.robot_chicken_goggles.tooltip.description.1")
    )

    init {
        PlayerBlockBreakCallback.CALLBACK.register { level, player, _, state ->
            // 检查玩家是否装备了机器鸡夜视仪
            val stack = AccessoryIntegration.getEquipped(
                player,
                HolderSet.direct(CalypsosNightVisionGogglesItems.RobotChickenGoggles)
            ) ?: return@register
            
            val mode = stack.mode!!
            if (stack.damageValue >= stack.maxDamage - 1 || !mode.isEnabled(stack, player)) {
                return@register
            }

            // 调试：打印方块信息
            if (!level.isClientSide) {
                CalypsosNightVisionGoggles.LOGGER.info("破坏方块: ${state.block.descriptionId}, 是否是矿物: ${state.`is`(ORES_TAG)}")
            }

            // 检查是否是矿物方块
            if (!state.`is`(ORES_TAG)) {
                return@register
            }

            // 5%概率掉落赛博鸡蛋
            if (level.random.nextFloat() < 0.05f) {
                val eggStack = ItemStack(CalypsosNightVisionGogglesItems.CyberChickenEgg.value())
                if (!player.inventory.add(eggStack)) {
                    player.drop(eggStack, false)
                }
            }
        }
    }
}
