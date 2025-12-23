package settingdust.calypsos_nightvision_goggles.v1_21.item.nightvision_goggles.variant

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.variant.VistaVariant

class VistaVariantSpeedModifierHandler : VistaVariant.SpeedModifierHandler {
    companion object {
        val SPEED_MODIFIER_ID = CalypsosNightVisionGoggles.id("vista_goggles_speed_bonus")
        val STEP_HEIGHT_MODIFIER_ID = CalypsosNightVisionGoggles.id("vista_goggles_step_height")
        const val STEP_HEIGHT_BONUS = 0.4 // 从 0.6 增加到 1.0
    }
    
    override fun updateSpeedModifier(entity: LivingEntity, speedBonus: Double) {
        val currentBonus = VistaVariant.currentSpeedBonus[entity.uuid] ?: 0.0
        
        // 如果速度加成没有变化，不需要更新
        if (currentBonus == speedBonus) return
        
        val speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED) ?: return
        
        // 移除旧的修饰符
        speedAttribute.removeModifier(SPEED_MODIFIER_ID)
        
        // 如果有加成，添加新的修饰符
        if (speedBonus > 0) {
            val modifier = AttributeModifier(
                SPEED_MODIFIER_ID,
                speedBonus,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
            speedAttribute.addTransientModifier(modifier)
            VistaVariant.currentSpeedBonus[entity.uuid] = speedBonus
        } else {
            VistaVariant.currentSpeedBonus.remove(entity.uuid)
        }
    }
    
    override fun removeSpeedModifier(entity: LivingEntity) {
        val speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED) ?: return
        speedAttribute.removeModifier(SPEED_MODIFIER_ID)
        VistaVariant.currentSpeedBonus.remove(entity.uuid)
    }
    
    override fun updateStepHeight(entity: LivingEntity, enabled: Boolean) {
        // 1.21.1 版本使用 STEP_HEIGHT 属性
        val stepHeightAttribute = entity.getAttribute(Attributes.STEP_HEIGHT) ?: return
        
        stepHeightAttribute.removeModifier(STEP_HEIGHT_MODIFIER_ID)
        
        if (enabled) {
            val modifier = AttributeModifier(
                STEP_HEIGHT_MODIFIER_ID,
                STEP_HEIGHT_BONUS.toDouble(),
                AttributeModifier.Operation.ADD_VALUE
            )
            stepHeightAttribute.addTransientModifier(modifier)
        }
    }
}
