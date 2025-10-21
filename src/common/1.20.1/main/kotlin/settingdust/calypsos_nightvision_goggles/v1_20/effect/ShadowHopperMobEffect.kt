package settingdust.calypsos_nightvision_goggles.v1_20.effect

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import settingdust.calypsos_nightvision_goggles.effect.ShadowHopperMobEffect
import java.util.*

class ShadowHopperMobEffect : ShadowHopperMobEffect() {
    companion object {
        val SpeedId = UUID.fromString("817CDADE-42FE-475E-B93E-B7A77120DDBF")
    }

    init {
        addAttributeModifier(
            Attributes.MOVEMENT_SPEED,
            SpeedId.toString(),
            0.06,
            AttributeModifier.Operation.MULTIPLY_TOTAL
        )
    }

    override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
        tick(entity)
    }

    override fun isDurationEffectTick(duration: Int, amplifier: Int) = shouldApplyEffectTickThisTick(duration)
}