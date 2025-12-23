package settingdust.calypsos_nightvision_goggles.v1_21.goal

import net.minecraft.core.HolderSet
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.goal.TemptGoal
import net.minecraft.world.item.ItemStack
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesItems
import settingdust.calypsos_nightvision_goggles.adapter.AccessoryIntegration
import settingdust.calypsos_nightvision_goggles.goal.CuteChickenTemptGoalFactory
import java.util.function.Predicate

class CuteChickenTemptGoal(
    mob: PathfinderMob,
    speedModifier: Double,
    items: Predicate<ItemStack>,
    canScare: Boolean
) : TemptGoal(mob, speedModifier, items, canScare) {
    
    init {
        // 初始化targetingConditions，使用自定义的shouldFollow
        val tempTargetingField = TemptGoal::class.java.getDeclaredField("TEMP_TARGETING")
        tempTargetingField.isAccessible = true
        val tempTargeting = tempTargetingField.get(null) as net.minecraft.world.entity.ai.targeting.TargetingConditions
        
        val targetingConditionsField = TemptGoal::class.java.getDeclaredField("targetingConditions")
        targetingConditionsField.isAccessible = true
        targetingConditionsField.set(this, tempTargeting.copy().selector(::shouldFollow))
    }
    
    class Factory : CuteChickenTemptGoalFactory {
        override fun create(
            mob: PathfinderMob,
            original: TemptGoal
        ): TemptGoal {
            // 通过反射获取原TemptGoal的参数
            val itemsField = TemptGoal::class.java.getDeclaredField("items")
            itemsField.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val items = itemsField.get(original) as Predicate<ItemStack>
            
            val speedModifierField = TemptGoal::class.java.getDeclaredField("speedModifier")
            speedModifierField.isAccessible = true
            val speedModifier = speedModifierField.getDouble(original)
            
            val canScareField = TemptGoal::class.java.getDeclaredField("canScare")
            canScareField.isAccessible = true
            val canScare = canScareField.getBoolean(original)
            
            return CuteChickenTemptGoal(mob, speedModifier, items, canScare)
        }
    }
    
    /**
     * 1.21.1版本的shouldFollow实现
     */
    private fun shouldFollow(entity: LivingEntity): Boolean {
        // 先检查是否装备了可爱鸡夜视仪
        val stack = AccessoryIntegration.getEquipped(
            entity,
            HolderSet.direct(CalypsosNightVisionGogglesItems.CuteChickenGoggles)
        )
        if (stack != null) {
            return true
        }
        
        // 访问父类的items字段
        val itemsField = TemptGoal::class.java.getDeclaredField("items")
        itemsField.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val items = itemsField.get(this) as Predicate<ItemStack>
        
        return items.test(entity.mainHandItem) || items.test(entity.offhandItem)
    }
}
