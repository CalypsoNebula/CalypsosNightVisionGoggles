package settingdust.calypsos_nightvision_goggles.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter

abstract class ItsWatchingMobEffect : MobEffect(
    MobEffectCategory.HARMFUL,
    0xED740C
) {
    fun tick(target: LivingEntity) {
        target.addEffect(
            MobEffectAdapter.createMobEffectInstance(
                MobEffectAdapter.Slowness,
                2 * 20,
                0,
                ambient = false,
                visible = false,
                showIcon = false
            )
        )
        target.addEffect(
            MobEffectAdapter.createMobEffectInstance(
                MobEffectAdapter.Weakness,
                2 * 20,
                0,
                ambient = false,
                visible = false,
                showIcon = false
            )
        )
    }

    fun shouldApplyEffectTickThisTick(duration: Int): Boolean {
        return duration % 20 == 0
    }
}