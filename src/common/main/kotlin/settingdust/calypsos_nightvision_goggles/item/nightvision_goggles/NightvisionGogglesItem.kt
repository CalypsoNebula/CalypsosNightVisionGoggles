package settingdust.calypsos_nightvision_goggles.item.nightvision_goggles

import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.item.Equipable
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGoggles
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesKeyBindings
import settingdust.calypsos_nightvision_goggles.CalypsosNightVisionGogglesSoundEvents
import settingdust.calypsos_nightvision_goggles.adapter.LoaderAdapter
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.NightvisionGogglesModeHandler.Companion.mode
import settingdust.calypsos_nightvision_goggles.item.nightvision_goggles.render.NightvisionGogglesGeoItem
import settingdust.calypsos_nightvision_goggles.mixin.AbstractContainerScreenAccessor
import settingdust.calypsos_nightvision_goggles.util.ServiceLoaderUtil
import software.bernie.geckolib.animatable.GeoItem

abstract class NightvisionGogglesItem(val variant: NightvisionGogglesVariant) :
    Item(Properties().stacksTo(1).durability(1800 * 20 + 1)),
    Equipable,
    GeoItem by ServiceLoaderUtil.findService<NightvisionGogglesGeoItem>() {
    interface Factory {
        companion object : Factory by ServiceLoaderUtil.findService()

        operator fun invoke(variant: NightvisionGogglesVariant): NightvisionGogglesItem
    }

    private var expanded = false

    val DURABILITY_PROVIDERS = mapOf(
        Items.SPIDER_EYE to 180 * 20,
        Items.FERMENTED_SPIDER_EYE to 540 * 20,
        Items.GLOWSTONE_DUST to 540 * 20
    )

    init {
        if (LoaderAdapter.isClient) {
            LoaderAdapter.onKeyPressedInScreen(CalypsosNightVisionGogglesKeyBindings.ACCESSORY_MODE) { screen ->
                if (screen !is AbstractContainerScreen<*>) return@onKeyPressedInScreen
                val hoveredSlot = (screen as AbstractContainerScreenAccessor).hoveredSlot
                if (hoveredSlot == null
                    || hoveredSlot is CreativeModeInventoryScreen.CustomCreativeSlot
                    || !hoveredSlot.hasItem()
                    || hoveredSlot.item.item !== this
                ) return@onKeyPressedInScreen
                NightvisionGogglesNetworking.c2sSwitchMode(hoveredSlot, screen is CreativeModeInventoryScreen)
                Minecraft.getInstance().soundManager.play(
                    SimpleSoundInstance.forUI(CalypsosNightVisionGogglesSoundEvents.UiModeSwitch, 1f, 1f)
                )
            }
        }

        LoaderAdapter.onLivingEntityTick { entity ->
            val stack = entity.getItemBySlot(EquipmentSlot.HEAD)
            if (!stack.`is`(this)) return@onLivingEntityTick
            variant.tick(stack, entity)
        }

        LoaderAdapter.onItemStackedOnOther { _, carriedItem, stackedOnItem, slot, clickAction ->
            if (clickAction !== ClickAction.SECONDARY) return@onItemStackedOnOther false
            if (!carriedItem.`is`(this)) return@onItemStackedOnOther false
            val value = DURABILITY_PROVIDERS[stackedOnItem.item] ?: return@onItemStackedOnOther false
            carriedItem.damageValue -= value
            stackedOnItem.shrink(1)
            true
        }
    }

    fun MutableList<Component>.appendTooltip(stack: ItemStack) {
        if (stack.mode == null) stack.mode = NightvisionGogglesModeHandler.Mode.AUTO
        val spiderEye = Items.SPIDER_EYE.description.copy().withStyle { it.withColor(0xC85A54) }
        addAll(variant.description)
        add(
            Component.translatable(
                "item.${CalypsosNightVisionGoggles.ID}.nightvision_goggles.tooltip.description",
                Component.translatable("effect.minecraft.night_vision").withStyle { it.withColor(0x658963) },
                spiderEye
            )
        )
        val modes = NightvisionGogglesModeHandler.Mode.entries
            .map { mode ->
                Component.translatable("item.${CalypsosNightVisionGoggles.ID}.nightvision_goggles.mode.${mode.name.lowercase()}")
                    .withStyle { style ->
                        if (stack.mode == mode) style.withColor(mode.color) else style.withColor(
                            ChatFormatting.GRAY
                        )
                    }
            }.toTypedArray()
        add(
            Component.translatable(
                "item.${CalypsosNightVisionGoggles.ID}.nightvision_goggles.tooltip.mode",
                *modes,
                CalypsosNightVisionGogglesKeyBindings.ACCESSORY_MODE.translatedKeyMessage
            )
        )
        if (LoaderAdapter.isClient && !Screen.hasShiftDown()) {
            if (expanded) {
                Minecraft.getInstance().soundManager.play(
                    SimpleSoundInstance.forUI(
                        CalypsosNightVisionGogglesSoundEvents.UiCollapse,
                        1f,
                        1f
                    )
                )
            }
            expanded = false
            add(Component.translatable("tooltip.${CalypsosNightVisionGoggles.ID}.expand"))
        } else {
            if (LoaderAdapter.isClient && !expanded) {
                Minecraft.getInstance().soundManager.play(
                    SimpleSoundInstance.forUI(
                        CalypsosNightVisionGogglesSoundEvents.UiExpand,
                        1f,
                        1f
                    )
                )
                expanded = true
            }
            add(Component.translatable("item.${CalypsosNightVisionGoggles.ID}.nightvision_goggles.tooltip.expand.0"))
            add(
                Component.translatable(
                    "item.${CalypsosNightVisionGoggles.ID}.nightvision_goggles.tooltip.expand.1",
                    spiderEye,
                    Items.FERMENTED_SPIDER_EYE.description.copy().withStyle { it.withColor(0xD4696F) },
                    Items.GLOWSTONE_DUST.description.copy().withStyle { it.withColor(0xF4A460) }
                )
            )
            add(Component.translatable("item.${CalypsosNightVisionGoggles.ID}.nightvision_goggles.tooltip.expand.2"))
            add(Component.translatable("item.${CalypsosNightVisionGoggles.ID}.nightvision_goggles.tooltip.expand.3"))
        }
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        stack.mode =
            NightvisionGogglesModeHandler.Mode.entries[
                stack.mode?.ordinal?.let { (it + 1) % NightvisionGogglesModeHandler.Mode.entries.size } ?: 0
            ]
        return InteractionResultHolder.success(stack)
    }

    override fun getEquipmentSlot() = EquipmentSlot.HEAD

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        if (entity !is ServerPlayer) return
        val inventory = entity.inventory
        if (!(slotId >= inventory.items.size && slotId < inventory.items.size + inventory.armor.size)) return
        variant.tick(stack, entity)
    }

    override fun onCraftedBy(stack: ItemStack, level: Level, player: Player) {
        stack.damageValue = stack.maxDamage
    }
}