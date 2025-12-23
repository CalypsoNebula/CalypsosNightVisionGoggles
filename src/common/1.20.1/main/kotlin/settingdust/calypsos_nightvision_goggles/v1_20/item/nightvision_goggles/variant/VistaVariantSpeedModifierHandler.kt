package settingdust.calypsos_nightvision_goggles.v1_20.item.nightvision_goggles.variant

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.VistaVariant
import java.util.*

open class VistaVariantSpeedModifierHandler : VistaVariant.SpeedModifierHandler {
    companion object {
        val SPEED_MODIFIER_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        const val DEFAULT_STEP_HEIGHT = 0.6f
        const val ENHANCED_STEP_HEIGHT = 1.0f
    }
    
    override fun updateSpeedModifier(entity: LivingEntity, speedBonus: Double) {
        val currentBonus = VistaVariant.currentSpeedBonus[entity.uuid] ?: 0.0
        
        // 如果速度加成没有变化，不需要更新
        if (currentBonus == speedBonus) return
        
        val speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED) ?: return
        
        // 移除旧的修饰符
        speedAttribute.removeModifier(SPEED_MODIFIER_UUID)
        
        // 如果有加成，添加新的修饰符
        if (speedBonus > 0) {
            val modifier = AttributeModifier(
                SPEED_MODIFIER_UUID,
                "Vista Goggles Speed Bonus",
                speedBonus,
                AttributeModifier.Operation.MULTIPLY_TOTAL
            )
            speedAttribute.addTransientModifier(modifier)
            VistaVariant.currentSpeedBonus[entity.uuid] = speedBonus
        } else {
            VistaVariant.currentSpeedBonus.remove(entity.uuid)
        }
    }
    
    override fun removeSpeedModifier(entity: LivingEntity) {
        val speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED) ?: return
        speedAttribute.removeModifier(SPEED_MODIFIER_UUID)
        VistaVariant.currentSpeedBonus.remove(entity.uuid)
    }
    
    override fun updateStepHeight(entity: LivingEntity, enabled: Boolean) {
        // 1.20.1 版本使用 setMaxUpStep 方法
        entity.setMaxUpStep(if (enabled) ENHANCED_STEP_HEIGHT else DEFAULT_STEP_HEIGHT)
    }
}
