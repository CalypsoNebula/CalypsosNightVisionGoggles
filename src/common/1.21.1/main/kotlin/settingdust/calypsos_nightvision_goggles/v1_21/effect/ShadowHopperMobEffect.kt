package settingdust.calypsos_nightvision_goggles.v1_21.effect

import net.minecraft.world.entity.LivingEntity
import settingdust.calypsos_nightvision_goggles.effect.ShadowHopperMobEffect

class ShadowHopperMobEffect : ShadowHopperMobEffect() {
    override fun applyEffectTick(entity: LivingEntity, amplifier: Int): Boolean {
        tick(entity)
        return true
    }

    override fun shouldApplyEffectTickThisTick(duration: Int, amplifier: Int): Boolean {
        return shouldApplyEffectTickThisTick(duration)
    }
}