package co.secretonline.accessiblestep.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import co.secretonline.accessiblestep.options.AccessibleStepOptions;
import net.minecraft.client.option.GameOptions;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
	@Inject(at = @At("HEAD"), method = "accept(Lnet/minecraft/client/option/GameOptions$Visitor;)V")
	private void injectStepOption(GameOptions.Visitor visitor, CallbackInfo info) {
		visitor.accept(AccessibleStepOptions.STEP_MODE_OPTIONS_KEY, AccessibleStepOptions.getStepModeOption());
	}
}
