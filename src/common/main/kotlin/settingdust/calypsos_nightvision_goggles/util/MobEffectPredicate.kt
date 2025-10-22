package settingdust.calypsos_nightvision_goggles.util

import net.minecraft.core.Holder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter.Companion.effectReference

data class MobEffectPredicate(
    val type: Holder<MobEffect>,
    val duration: Int? = null,
    val amplifier: Int? = null,
    val ambient: Boolean? = null,
    val visible: Boolean? = null,
    val showIcon: Boolean? = null
) {
    fun test(effect: MobEffectInstance?): Boolean {
        return when {
            type.value() != effect?.effectReference -> false
            duration != null && !effect.endsWithin(duration) -> false
            amplifier != null && effect.amplifier != amplifier -> false
            ambient != null && effect.isAmbient != ambient -> false
            visible != null && effect.isVisible != visible -> false
            showIcon != null && effect.showIcon() != showIcon -> false
            else -> true
        }
    }
}
