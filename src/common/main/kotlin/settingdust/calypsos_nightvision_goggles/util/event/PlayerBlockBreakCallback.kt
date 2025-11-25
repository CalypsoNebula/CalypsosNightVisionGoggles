package settingdust.calypsos_nightvision_goggles.util.event

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.state.BlockState

object PlayerBlockBreakCallback {
    @JvmField
    val CALLBACK = Event { listeners ->
        Callback { level, player, pos, state ->
            for (listener in listeners) {
                listener.onBreak(level, player, pos, state)
            }
        }
    }

    fun interface Callback {
        fun onBreak(level: LevelAccessor, player: Player, pos: BlockPos, state: BlockState)
    }
}