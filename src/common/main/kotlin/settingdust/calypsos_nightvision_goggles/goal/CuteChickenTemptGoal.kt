package settingdust.calypsos_nightvision_goggles.goal

import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.goal.TemptGoal
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

/**
 * CuteChickenTemptGoal的工厂接口
 * 用于创建版本特定的TemptGoal实现（1.20和1.21的构造函数参数不同）
 */
interface CuteChickenTemptGoalFactory {
    companion object : CuteChickenTemptGoalFactory by ServiceLoaderUtil.findService()
    
    fun create(
        mob: PathfinderMob,
        original: TemptGoal
    ): TemptGoal
}
