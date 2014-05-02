package unrelentless.xxx;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.MinecraftForge;
import unrelentless.xxx.handlers.ConfigHandler;
import unrelentless.xxx.handlers.KeybindHelper;
import unrelentless.xxx.handlers.UpdateHandler;
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
		ConfigHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MODID + File.separator + Reference.MODID + ".cfg"));
		

	}
	@EventHandler
	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new UpdateHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){

	}
}
