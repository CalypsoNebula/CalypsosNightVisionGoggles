package settingdust.calypsos_nightvision_goggles.item

import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import settingdust.calypsos_nightvision_goggles.adapter.MobEffectAdapter
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

abstract class CyberChickenEggItem(properties: Properties) : Item(properties) {
    
    interface Factory {
        companion object : Factory {
            private val implementation by lazy { ServiceLoaderUtil.findService<Factory>() }
            
            override fun invoke(): Item = implementation()
        }
        
        operator fun invoke(): Item
    }
    protected abstract fun playBerryBushSound(level: Level, player: Player)
    protected abstract fun playChickenSound(level: Level, player: Player)

    override fun finishUsingItem(stack: ItemStack, level: Level, entity: LivingEntity): ItemStack {
        if (entity is Player) {
            val random = level.random.nextFloat()

            when {
                // 75%概率回复3点饱食度
                random < 0.75f -> {
                    entity.foodData.eat(3, 0.3f)
                }
                // 20%概率模拟触电效果
                random < 0.95f -> {
                    playBerryBushSound(level, entity)
                    // 受到0点伤害(仅触发效果)
                    entity.hurt(level.damageSources().generic(), 0f)
                    // 获得30秒急迫效果
                    entity.addEffect(
                        MobEffectAdapter.createMobEffectInstance(
                            MobEffectAdapter.DigSpeed,
                            30 * 20,
                            0,
                            ambient = false,
                            visible = true,
                            showIcon = true
                        )
                    )
                }
                // 5%概率生成小鸡
                else -> {
                    playChickenSound(level, entity)

                    // 在脚下生成一个小鸡
                    if (!level.isClientSide) {
                        val chicken = EntityType.CHICKEN.create(level)
                        if (chicken != null) {
                            chicken.setPos(entity.x, entity.y, entity.z)
                            chicken.isBaby = true  // 设置为幼体
                            level.addFreshEntity(chicken)
                        }
                    }
                }
            }
        }

        return super.finishUsingItem(stack, level, entity)
    }
}
