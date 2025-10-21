package settingdust.calypsos_nightvision_goggles.v1_21.effect

import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeys
import settingdust.calypsos_nightvision_goggles.effect.ItsWatchingMobEffect

class ItsWatchingMobEffect : ItsWatchingMobEffect() {
    init {
        addAttributeModifier(
            Attributes.MOVEMENT_SPEED,
            CalypsosNightVisionGogglesKeys.ItsWatching,
            -0.15,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        )
        addAttributeModifier(
            Attributes.ATTACK_DAMAGE,
            CalypsosNightVisionGogglesKeys.ItsWatching,
            -4.0,
            AttributeModifier.Operation.ADD_VALUE
        )
    }
}