package settingdust.calypsos_nightvision_goggles.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import settingdust.calypsos_nightvision_goggles.adapter.LivingEntityAdapter.Companion.hasEffect
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter

abstract class ShadowHopperMobEffect : MobEffect(
    MobEffectCategory.BENEFICIAL,
    0x74B602
) {
    fun tick(target: LivingEntity) {
        val brightness = target.level().getMaxLocalRawBrightness(target.blockPosition())
        val amplifier = 7 - brightness
        if (amplifier < 0) return
        if (!target.hasEffect(MobEffectAdapter.JumpBoost)) {
            target.addEffect(
                MobEffectAdapter.createMobEffectInstance(
                    MobEffectAdapter.JumpBoost,
                    2 * 20,
                    amplifier,
                    ambient = false,
                    visible = false,
                    showIcon = false
                )
            )
        }
    }

    fun shouldApplyEffectTickThisTick(duration: Int): Boolean {
        return duration % 20 == 0
    }
}