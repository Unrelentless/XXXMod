package unrelentless.xxx;

import java.io.IOException;

import unrelentless.xxx.handlers.KeybindHelper;
import unrelentless.xxx.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

//Mod Information
@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.MODVERSION)

public class XXXMod {

	@Instance(Reference.MODID)
	public static XXXMod instance = new XXXMod();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws IOException{
		KeybindHelper.init();

	}
	@EventHandler
	public void init(FMLInitializationEvent event){

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){

	}
}
