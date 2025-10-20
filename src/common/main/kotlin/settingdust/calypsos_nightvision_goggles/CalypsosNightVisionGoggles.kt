package settingdust.calypsos_nightvision_goggles

import org.apache.logging.log4j.LogManager
import settingdust.calypsos_nightvision_goggles.adapter.MinecraftAdapter
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

object CalypsosNightVisionGoggles {
    const val ID = "calypsos_nightvision_goggles"

    val LOGGER = LogManager.getLogger()

    init {
        ServiceLoaderUtil.defaultLogger = LOGGER
    }

    fun id(path: String) = MinecraftAdapter.id(ID, path)
}