package settingdust.calypsos_nightvision_goggles.v1_20.item

import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.level.Level
import settingdust.calypsos_nightvision_goggles.item.CyberChickenEggItem as BaseCyberChickenEggItem

class CyberChickenEggItem : BaseCyberChickenEggItem(
    Properties()
        .food(
            FoodProperties.Builder()
                .nutrition(0)
                .saturationMod(0f)
                .alwaysEat()
                .build()
        )
) {
    class Factory : BaseCyberChickenEggItem.Factory {
        override fun invoke() = CyberChickenEggItem()
    }

    override fun playBerryBushSound(level: Level, player: Player) {
        level.playSound(
            player,
            player.x,
            player.y,
            player.z,
            SoundEvents.PLAYER_HURT_SWEET_BERRY_BUSH,
            SoundSource.PLAYERS,
            1.0f,
            1.0f
        )
    }

    override fun playChickenSound(level: Level, player: Player) {
        level.playSound(
            player,
            player.x,
            player.y,
            player.z,
            SoundEvents.CHICKEN_AMBIENT,
            SoundSource.NEUTRAL,
            1.0f,
            1.0f
        )
    }
}
