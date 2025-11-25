package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.core.HolderSet
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter.Companion.hasEffect
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.util.MobEffectPredicate
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil
import settingdust.calypsos_nightvision_goggles.util.event.PlayerBlockBreakCallback
import software.bernie.geckolib.model.DefaultedItemGeoModel

object ByteBuddiesVariant : NightvisionGogglesVariant {
    val DigSpeedPredicate = MobEffectPredicate(
        MobEffectAdapter.DigSpeed,
        duration = 60 * 20,
        amplifier = 0,
        ambient = false,
        visible = true,
        showIcon = true
    )

    override val model =
        DefaultedItemGeoModel<NightvisionGogglesItem>(CalypsosNightVisionGogglesKeys.ByteBuddiesGoggles)

    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.byte_buddies_goggles.tooltip.description.0"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.byte_buddies_goggles.tooltip.description.1"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.byte_buddies_goggles.tooltip.description.2")
    )

    init {
        PlayerBlockBreakCallback.CALLBACK.register { _, player, _, _ ->
            val stack = AccessoryIntegration.getEquipped(
                player,
                HolderSet.direct(CalypsosNightVisionGogglesItems.ByteBuddiesGoggles)
            ) ?: return@register
            val mode = stack.mode!!
            if (stack.damageValue >= stack.maxDamage - 1 || !mode.isEnabled(stack, player)) {
                return@register
            }
            if (!player.hasEffect(DigSpeedPredicate.type)) {
                player.addEffect(
                    MobEffectAdapter.createMobEffectInstance(
                        DigSpeedPredicate.type,
                        DigSpeedPredicate.duration!!,
                        DigSpeedPredicate.amplifier!!,
                        DigSpeedPredicate.ambient!!,
                        false,
                        DigSpeedPredicate.showIcon!!
                    )
                )
            }
        }
    }

    interface Ticker {
        companion object : Ticker {
            private val implementations by lazy { ServiceLoaderUtil.findServices<Ticker>(required = false) }

            override fun tick(stack: ItemStack, owner: LivingEntity) {
                for (ticker in implementations) {
                    ticker.tick(stack, owner)
                }
            }
        }

        fun tick(stack: ItemStack, owner: LivingEntity)
    }

    override fun tick(stack: ItemStack, owner: LivingEntity) {
        super.tick(stack, owner)
        Ticker.tick(stack, owner)
    }
}