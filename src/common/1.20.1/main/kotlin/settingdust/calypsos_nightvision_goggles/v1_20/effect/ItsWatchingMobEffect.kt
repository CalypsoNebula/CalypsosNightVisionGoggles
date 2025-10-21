package settingdust.calypsos_nightvision_goggles.v1_20.effect

import net.minecraft.world.entity.LivingEntity
import settingdust.calypsos_nightvision_goggles.effect.ItsWatchingMobEffect

class ItsWatchingMobEffect : ItsWatchingMobEffect() {
    override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
        tick(entity)
    }

    override fun isDurationEffectTick(duration: Int, amplifier: Int): Boolean {
        return shouldApplyEffectTickThisTick(duration)
    }
}