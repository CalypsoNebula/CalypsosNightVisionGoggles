package settingdust.calypsos_nightvision_goggles.adapter

import net.minecraft.core.Holder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

interface MobEffectAdapter {
    companion object : MobEffectAdapter by ServiceLoaderUtil.findService()

    val Nightvision: Holder<MobEffect>

    val MobEffectInstance.effectReference: MobEffect

    fun createMobEffectInstance(
        effect: Holder<MobEffect>,
        duration: Int,
        amplifier: Int,
        ambient: Boolean,
        visible: Boolean,
        showIcon: Boolean
    ): MobEffectInstance
}