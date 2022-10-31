package dev.stashy.extrasounds.handlers

import dev.stashy.extrasounds.SoundManager
import dev.stashy.extrasounds.sounds.SoundType
import dev.stashy.extrasounds.sounds.Sounds
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.slot.SlotActionType

object InventoryEventHandler {
    fun click(clicked: Item, hand: Item, eventType: SlotActionType) {
        println("^ clicked: ${clicked.name.string} | hand: ${hand.name.string} | eventType: $eventType")

        when (eventType) {
            SlotActionType.PICKUP, SlotActionType.QUICK_MOVE, SlotActionType.SWAP -> {
                if (clicked != Items.AIR) SoundManager.playItemSound(clicked, SoundType.PLACE)
                else if (hand != Items.AIR) SoundManager.playItemSound(hand, SoundType.PICKUP)
            }

            SlotActionType.QUICK_CRAFT -> SoundManager.playItemSound(hand, SoundType.PLACE)
            SlotActionType.PICKUP_ALL -> SoundManager.playSound(Sounds.ITEM_PICK_ALL, SoundType.PICKUP)
            SlotActionType.CLONE -> SoundManager.playSound(Sounds.ITEM_CLONE, SoundType.PICKUP)

            else -> {}
        }
    }

    fun drop(stack: ItemStack) {
        if (!stack.isEmpty) {
            val pitch = 0.5f + stack.item.maxCount / stack.count
            SoundManager.playSound(
                SoundManager.getItemSound(stack.item, SoundType.ACTION),
                SoundType.ACTION,
                pitch = pitch
            )
        }
    }

    fun hotbar(item: Item) {
        if (item != Items.AIR)
            SoundManager.playItemSound(item, SoundType.HOTBAR)
        else SoundManager.playSound(Sounds.HOTBAR_SCROLL, SoundType.HOTBAR)
    }
}
