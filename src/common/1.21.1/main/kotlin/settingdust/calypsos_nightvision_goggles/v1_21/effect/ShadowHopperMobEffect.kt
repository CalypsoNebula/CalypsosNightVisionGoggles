package settingdust.calypsos_nightvision_goggles.v1_21.effect

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.effect.ShadowHopperMobEffect

class ShadowHopperMobEffect : ShadowHopperMobEffect() {
    init {
        addAttributeModifier(
            Attributes.MOVEMENT_SPEED,
            CalypsosNightVisionGogglesKeys.ShadowHopper,
            0.06,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        )
    }

    override fun applyEffectTick(entity: LivingEntity, amplifier: Int): Boolean {
        tick(entity)
        return true
    }

    override fun shouldApplyEffectTickThisTick(duration: Int, amplifier: Int): Boolean {
        return shouldApplyEffectTickThisTick(duration)
    }
}