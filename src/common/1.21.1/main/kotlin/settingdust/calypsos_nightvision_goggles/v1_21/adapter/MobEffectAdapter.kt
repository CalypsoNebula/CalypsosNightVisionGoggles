package settingdust.calypsos_nightvision_goggles.v1_21.adapter

import net.minecraft.core.Holder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter

class MobEffectAdapter : MobEffectAdapter {
    override val Nightvision = MobEffects.NIGHT_VISION
    override val MobEffectInstance.effectReference: MobEffect
        get() = effect.value()

    override fun createMobEffectInstance(
        effect: Holder<MobEffect>,
        duration: Int,
        amplifier: Int,
        ambient: Boolean,
        visible: Boolean,
        showIcon: Boolean
    ) = MobEffectInstance(effect, duration, amplifier, ambient, visible, showIcon)
}