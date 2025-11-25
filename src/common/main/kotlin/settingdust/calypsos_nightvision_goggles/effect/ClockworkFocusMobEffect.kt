package settingdust.calypsos_nightvision_goggles.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.Projectile
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesMobEffects
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter.Companion.getEffect
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter.Companion.hasEffect
import settingdust.calypsos_nightvision_goggles.util.event.LivingHurtEvents

object ClockworkFocusMobEffect : MobEffect(MobEffectCategory.BENEFICIAL, 0xf0c846) {
    init {
        LivingHurtEvents.MODIFY_DAMAGE.register { entity, source, originalAmount, amount ->
            val shooter = source.entity
            if (source.directEntity != shooter
                || source.directEntity !is Projectile
                || shooter !is LivingEntity
                || !shooter.hasEffect(CalypsosNightVisionGogglesMobEffects.ClockworkFocus)
            )
                return@register amount
            if (entity.hasEffect(CalypsosNightVisionGogglesMobEffects.ClockworkFocus)) {
                val amplifier = entity.getEffect(CalypsosNightVisionGogglesMobEffects.ClockworkFocus)?.amplifier ?: 0
                return@register amount * (1 + 0.1 * amplifier)
            }
            return@register amount
        }
    }
}