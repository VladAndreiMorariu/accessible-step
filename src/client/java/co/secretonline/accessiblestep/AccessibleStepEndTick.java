package co.secretonline.accessiblestep;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class AccessibleStepEndTick implements EndTick {

	private static final RegistryEntry<EntityAttribute> STEP_HEIGHT_ATTRIBUTE = Registries.ATTRIBUTE
			.getEntry(new Identifier("minecraft:generic.step_height")).get();

	/**
	 * Default step height is 0.6 blocks.
	 * From testing with other step mods, modified step heights are usually around
	 * 1.25 blocks.
	 */
	private static final float STEP_HEIGHT_MODIFIER_AMOUNT = 0.65f;

	/**
	 * Modifier to add an amount to the step height.
	 *
	 * This mod uses modifiers to avoid conflicts where the server might update the
	 * base value to be different from the normal value. This mod will stull affect
	 * the step height, but hopefully not as much.
	 */
	private static final EntityAttributeModifier STEP_HEIGHT_MODIFIER = new EntityAttributeModifier("accessiblestep",
			STEP_HEIGHT_MODIFIER_AMOUNT, Operation.ADD_VALUE);

	@Override
	public void onEndTick(MinecraftClient client) {
		if (client.player == null) {
			return;
		}

		EntityAttributeInstance stepHeightAttribute = client.player.getAttributeInstance(STEP_HEIGHT_ATTRIBUTE);

		if (client.player.isSneaking()) {
			if (stepHeightAttribute.hasModifier(STEP_HEIGHT_MODIFIER)) {
				stepHeightAttribute.removeModifier(STEP_HEIGHT_MODIFIER);
			}
		} else {
			if (!stepHeightAttribute.hasModifier(STEP_HEIGHT_MODIFIER)) {
				stepHeightAttribute.addTemporaryModifier(STEP_HEIGHT_MODIFIER);
			}
		}
	}

}
