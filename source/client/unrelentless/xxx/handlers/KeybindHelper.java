package unrelentless.xxx.handlers;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.client.registry.KeyBindingRegistry;

public class KeybindHelper {
	/** Key descriptions */
	private static final String[] desc = {"Scan", "Locate"};

	/** Default key values */
	private static final int[] keyValues = {Keyboard.KEY_F, Keyboard.KEY_V};

	/** This stores all of our key bindings and is always updated with the in-game settings */
	public static final KeyBinding[] keys = new KeyBinding[desc.length];

	/** This initializes and registers all the key bindings */
	public static void init()
	{
		boolean[] repeat = new boolean[desc.length];
		// just use a for loop to run through all the values
		for (int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding(desc[i], keyValues[i]);
			repeat[i] = false;
		}
		KeyBindingRegistry.registerKeyBinding(new KeybindHandler(keys, repeat));
	}
}