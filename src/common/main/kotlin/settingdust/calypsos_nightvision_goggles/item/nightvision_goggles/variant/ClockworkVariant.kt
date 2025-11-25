package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant

import net.minecraft.core.HolderSet
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.Projectile
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesMobEffects
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter.Companion.getEffect
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter.Companion.hasEffect
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter.Companion.removeEffect
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesItem
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesVariant
import settingdust.calypsos_nightvision_goggles.util.MobEffectPredicate
import settingdust.calypsos_nightvision_goggles.util.event.LivingHurtEvents
import software.bernie.geckolib.model.DefaultedItemGeoModel

object ClockworkVariant : NightvisionGogglesVariant {
    val ClockworkFocusPredicate = MobEffectPredicate(
        CalypsosNightVisionGogglesMobEffects.ClockworkFocus,
        duration = 10 * 20,
        amplifier = 0,
        ambient = false,
        visible = false,
        showIcon = true
    )

    override val model = DefaultedItemGeoModel<NightvisionGogglesItem>(CalypsosNightVisionGogglesKeys.ClockworkGoggles)

    override val description = listOf(
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.clockwork_goggles.tooltip.description.0"),
        Component.translatable("item.${CalypsosNightVisionGoggles.ID}.clockwork_goggles.tooltip.description.1")
    )

    init {
        LivingHurtEvents.MODIFY_DAMAGE.register { entity, source, _, amount ->
            val shooter = source.entity
            if (source.directEntity == shooter
                || source.directEntity !is Projectile
                || shooter !is LivingEntity
            ) return@register amount
            if (!shooter.hasEffect(CalypsosNightVisionGogglesMobEffects.ClockworkFocus)) {
                val stack = AccessoryIntegration.getEquipped(
                    shooter,
                    HolderSet.direct(CalypsosNightVisionGogglesItems.ClockworkGoggles)
                ) ?: return@register amount
                stack.mode?.isEnabled(stack, shooter)?.takeIf { it } ?: return@register amount
                shooter.addEffect(
                    MobEffectAdapter.createMobEffectInstance(
                        ClockworkFocusPredicate.type,
                        ClockworkFocusPredicate.duration!!,
                        ClockworkFocusPredicate.amplifier!!,
                        ClockworkFocusPredicate.ambient!!,
                        ClockworkFocusPredicate.visible!!,
                        ClockworkFocusPredicate.showIcon!!
                    )
                )
            }
            val effect = shooter.getEffect(CalypsosNightVisionGogglesMobEffects.ClockworkFocus)
            val amplifier = effect?.amplifier ?: 0
            shooter.removeEffect(CalypsosNightVisionGogglesMobEffects.ClockworkFocus)
            shooter.addEffect(
                MobEffectAdapter.createMobEffectInstance(
                    ClockworkFocusPredicate.type,
                    ClockworkFocusPredicate.duration!!,
                    amplifier + 1,
                    ClockworkFocusPredicate.ambient!!,
                    ClockworkFocusPredicate.visible!!,
                    ClockworkFocusPredicate.showIcon!!
                )
            )
            return@register amount + (4 * amplifier)
        }
    }
}