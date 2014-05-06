package unrelentless.xxx.handlers;

import java.util.EnumSet;
import java.util.Iterator;

import unrelentless.xxx.lib.Config;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeybindHandler extends KeyHandler
{
	private final int SCAN = 0;
	private final int LOCATE = 1;

	/** Not really important. I use it to store/find keys in the config file */
	public static final String label = "XXX";
	public KeybindHandler(KeyBinding[] keyBindings, boolean[] repeatings) {
		super(keyBindings, repeatings);
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{

		if (FMLClientHandler.instance().getClient().inGameHasFocus){
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			if (tickEnd && FMLCommonHandler.instance().getEffectiveSide().isClient()){
				if(kb == keyBindings[SCAN]){
					player.addChatMessage("Scanning Started");
					scanAndPrint(player);
					player.addChatMessage("Scanning Complete");
				}else if(kb == keyBindings[LOCATE]){
					player.addChatMessage("Locating Players");
					locateAndPrint(player);
					player.addChatMessage("Found Players");
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		// Don't need to do anything here!
	}

	@Override
	public EnumSet<TickType> ticks() {
		// We're only interested in client ticks, as that's when the keyboard will fire
		return EnumSet.of(TickType.CLIENT);
	}

	public void scanAndPrint(EntityPlayer player){

		World world = player.worldObj;
		int xPos = (int)player.posX;
		int yPos = (int)player.posY;
		int zPos = (int)player.posZ;
		int dir = MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;

		if(Config.enableSearch){
			for(int k=-Config.xRadius;k<=Config.xRadius; k++){
				for(int i=-Config.zRadius;i<=Config.zRadius; i++){
					for(int j=-Config.yRadius;j<=Config.yRadius; j++){

						int blockID = world.getBlockId(xPos+i, yPos+k, zPos+j);
						int blockPosX = xPos+i;
						int blockPosY = yPos+k;
						int blockPosZ = zPos+j;
						if(blockID == Config.search1 && Config.search1 != 0){
							((EntityPlayer) player).addChatMessage(Block.blocksList[Config.search1].getLocalizedName()+" at: "+blockPosX+","+blockPosY+","+blockPosZ);
						}else if(blockID == Config.search2 && Config.search2 != 0){
							((EntityPlayer) player).addChatMessage(Block.blocksList[Config.search2].getLocalizedName()+" at: "+blockPosX+","+blockPosY+","+blockPosZ);
						}else if(blockID == Config.search3 && Config.search3 != 0){
							((EntityPlayer) player).addChatMessage(Block.blocksList[Config.search3].getLocalizedName()+" at: "+blockPosX+","+blockPosY+","+blockPosZ);
						}else if(blockID == Config.search4 && Config.search4 != 0){
							((EntityPlayer) player).addChatMessage(Block.blocksList[Config.search4].getLocalizedName()+" at: "+blockPosX+","+blockPosY+","+blockPosZ);
						}else if(blockID == Config.search5 && Config.search5 != 0){
							((EntityPlayer) player).addChatMessage(Block.blocksList[Config.search5].getLocalizedName()+" at: "+blockPosX+","+blockPosY+","+blockPosZ);
						}
					}
				}
			}
		}
	}

	public void locateAndPrint(EntityPlayer player){
		World world = player.worldObj;
		float closest = 64F;
		Entity thisEntity;
		String entityName;
		int[] location = new int[3];

		//Loop through all currently loaded entities.
		for (int l = 0; l < world.loadedEntityList.size(); l++){
			//Narrow down the entities to display to only creatures.
			if (world.loadedEntityList.get(l) instanceof EntityLiving && !((Entity)world.loadedEntityList.get(l)).getEntityName().equalsIgnoreCase("unknown"))
			{
				thisEntity = ((Entity)world.loadedEntityList.get(l));
				entityName = ((Entity)world.loadedEntityList.get(l)).getEntityName();
				int entityPosX = (int)((Entity)world.loadedEntityList.get(l)).posX;
				int entityPosY = (int)((Entity)world.loadedEntityList.get(l)).posY;
				int entityPosZ = (int)((Entity)world.loadedEntityList.get(l)).posZ;	
				if(thisEntity != player){
					player.addChatMessage(entityName+" is at: ("+entityPosX+","+entityPosY+","+entityPosZ+")");
				}
			}
		}
	}
	public String checkDir(EntityPlayer player, int dir, int posX, int posZ){
		switch(dir){
		case 0: //South
			if((int)player.posX > posX && (int)player.posZ < posZ){
				return "South West";
			}else if((int)player.posX < posX && (int)player.posZ > posZ){
				return "North East";
			}else if((int)player.posX < posX && (int)player.posZ < posZ){
				return "South East";
			}else if((int)player.posX > posX && (int)player.posZ > posZ){
				return "North West";
			}
		case 1: //West
			if((int)player.posX > posX && (int)player.posZ < posZ){
				return "South West";
			}else if((int)player.posX < posX && (int)player.posZ > posZ){
				return "North East";
			}else if((int)player.posX < posX && (int)player.posZ < posZ){
				return "South East";
			}else if((int)player.posX > posX && (int)player.posZ > posZ){
				return "North West";
			}
		case 2: //North
			if((int)player.posX < posX && (int)player.posZ > posZ){
				return "North East";
			}else if((int)player.posX > posX && (int)player.posZ < posZ){
				return "South West";
			}else if((int)player.posX < posX && (int)player.posZ < posZ){
				return "South East";
			}else if((int)player.posX > posX && (int)player.posZ > posZ){
				return "North West";
			}
		case 3: //East
			if((int)player.posX > posX && (int)player.posZ < posZ){
				return "South West";
			}else if((int)player.posX < posX && (int)player.posZ > posZ){
				return "North East";
			}else if((int)player.posX < posX && (int)player.posZ < posZ){
				return "South East";
			}else if((int)player.posX > posX && (int)player.posZ > posZ){
				return "North West";
			}
		default:
		}
		return "This way";
	}
}

