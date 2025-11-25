package settingdust.calypsos_nightvision_goggles.v1_21.adapter

import net.minecraft.core.Holder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter

class MobEffectAdapter : MobEffectAdapter {
    override val Nightvision = MobEffects.NIGHT_VISION
    override val Weakness = MobEffects.WEAKNESS
    override val Slowness = MobEffects.MOVEMENT_SLOWDOWN
    override val Speed = MobEffects.MOVEMENT_SPEED
    override val JumpBoost = MobEffects.JUMP
    override val Glowing = MobEffects.GLOWING
    override val DigSpeed = MobEffects.DIG_SPEED


    override val MobEffectInstance.effectHolder: Holder<MobEffect>
        get() = effect
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