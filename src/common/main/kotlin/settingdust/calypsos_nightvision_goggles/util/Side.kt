package settingdust.calypsos_nightvision_goggles.util

import net.minecraft.core.Direction

enum class Side(val direction: Direction) {
    BOTTOM(Direction.DOWN),
    TOP(Direction.UP),
    BACK(Direction.NORTH),
    FRONT(Direction.SOUTH),
    LEFT(Direction.WEST),
    RIGHT(Direction.EAST)
}