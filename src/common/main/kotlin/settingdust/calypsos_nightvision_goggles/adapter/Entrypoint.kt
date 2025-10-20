package settingdust.calypsos_nightvision_goggles.adapter

import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil

interface Entrypoint {
    companion object : Entrypoint {
        private val services by lazy { ServiceLoaderUtil.findServices<Entrypoint>(required = false) }

        override fun construct() {
            services.forEach { it.construct() }
        }

        override fun init() {
            services.forEach { it.init() }
        }

        override fun clientInit() {
            services.forEach { it.clientInit() }
        }
    }

    fun construct() {}

    fun init() {}

    fun clientInit() {}
}