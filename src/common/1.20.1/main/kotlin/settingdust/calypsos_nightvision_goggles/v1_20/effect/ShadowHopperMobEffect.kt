package settingdust.calypsos_nightvision_goggles.v1_20.effect

import net.minecraft.world.entity.LivingEntity
import settingdust.calypsos_nightvision_goggles.effect.ShadowHopperMobEffect

class ShadowHopperMobEffect : ShadowHopperMobEffect() {
    override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
        tick(entity)
    }
    override fun isDurationEffectTick(duration: Int, amplifier: Int) = shouldApplyEffectTickThisTick(duration)
}