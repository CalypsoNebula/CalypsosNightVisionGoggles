package settingdust.calypsos_nightvision_goggles.forge.item.nightvision_goggles.variant

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraftforge.common.ForgeMod
import java.util.*

class VistaVariantSpeedModifierHandler : settingdust.calypsos_nightvision_goggles.v1_20.item.nightvision_goggles.variant.VistaVariantSpeedModifierHandler() {
    companion object {
        val STEP_HEIGHT_MODIFIER_UUID = UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f12345678901")
        const val STEP_HEIGHT_ADDITION = 0.4 // 从 0.6 增加到 1.0
    }
    
    override fun updateStepHeight(entity: LivingEntity, enabled: Boolean) {
        // Forge 1.20.1 版本使用 ForgeMod.STEP_HEIGHT_ADDITION 属性
        val stepHeightAttribute = entity.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()) ?: return
        
        stepHeightAttribute.removeModifier(STEP_HEIGHT_MODIFIER_UUID)
        
        if (enabled) {
            val modifier = AttributeModifier(
                STEP_HEIGHT_MODIFIER_UUID,
                "Vista Goggles Step Height",
                STEP_HEIGHT_ADDITION.toDouble(),
                AttributeModifier.Operation.ADDITION
            )
            stepHeightAttribute.addTransientModifier(modifier)
        }
    }
}
