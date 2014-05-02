package unrelentless.xxx.handlers;

import java.io.File;
import java.util.logging.Level;



import unrelentless.xxx.lib.Config;
import unrelentless.xxx.lib.Reference;
import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.Configuration;

public class ConfigHandler {

	static Configuration config = new Configuration();

	public static void init(File file){

		config = new Configuration(file);

		try{
			config.load();
			
			//OTHER
			Config.search1 = config.get(config.CATEGORY_GENERAL, "Search 1", Config.search1_default).getInt(Config.search1_default);
			Config.search2 = config.get(config.CATEGORY_GENERAL, "Search 2", Config.search2_default).getInt(Config.search2_default);
			Config.search3 = config.get(config.CATEGORY_GENERAL, "Search 3", Config.search3_default).getInt(Config.search3_default);
			Config.search4 = config.get(config.CATEGORY_GENERAL, "Search 4", Config.search4_default).getInt(Config.search4_default);
			Config.search5 = config.get(config.CATEGORY_GENERAL, "Search 5", Config.search5_default).getInt(Config.search5_default);
			Config.xRadius = config.get(config.CATEGORY_GENERAL, "x Radius", Config.xRadius_default).getInt(Config.xRadius_default);
			Config.yRadius = config.get(config.CATEGORY_GENERAL, "y Radius", Config.yRadius_default).getInt(Config.yRadius_default);
			Config.zRadius = config.get(config.CATEGORY_GENERAL, "z Radius", Config.zRadius_default).getInt(Config.zRadius_default);
			Config.enableSearch = config.get(config.CATEGORY_GENERAL, "Enable searching", Config.enableSearch_default).getBoolean(Config.enableSearch_default);
			Config.scanTime = config.get(config.CATEGORY_GENERAL, "Search every x seconds", Config.scanTime_default).getInt(Config.scanTime_default);
			Config.printTime = config.get(config.CATEGORY_GENERAL, "Search every x seconds", Config.scanTime_default).getInt(Config.scanTime_default);
			
		}
		catch(Exception e){

			FMLLog.log(Level.SEVERE, e, Reference.MODID +  " config crashed and burned.");
		}

		finally{

			config.save();
		}

	}
}