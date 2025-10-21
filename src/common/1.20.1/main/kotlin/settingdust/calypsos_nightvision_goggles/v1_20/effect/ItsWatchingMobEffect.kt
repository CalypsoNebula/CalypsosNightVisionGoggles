package settingdust.calypsos_nightvision_goggles.v1_20.effect

import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import settingdust.calypsos_nightvision_goggles.effect.ItsWatchingMobEffect

class ItsWatchingMobEffect : ItsWatchingMobEffect() {
    init {
        addAttributeModifier(
            Attributes.MOVEMENT_SPEED,
            "DBAFBA45-2353-44C2-8D36-D1CD33EE2554",
            -0.15,
            AttributeModifier.Operation.MULTIPLY_TOTAL
        )

        addAttributeModifier(
            Attributes.ATTACK_DAMAGE,
            "81347642-F5B6-4944-8AE8-CDAD2E2DADBB",
            -4.0,
            AttributeModifier.Operation.ADDITION
        )
    }
}