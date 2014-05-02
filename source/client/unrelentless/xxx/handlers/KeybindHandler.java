package unrelentless.xxx.handlers;

import java.util.EnumSet;
import java.util.Iterator;

import unrelentless.xxx.lib.Config;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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
	/** Not really important. I use it to store/find keys in the config file */
	public static final String label = "Toggle Scan";
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
			if (tickEnd && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
				Config.enableSearch = !Config.enableSearch;
				player.addChatMessage("Scanning "+ Config.enableSearch);
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
		int count = 1;
		Iterator iterator;
		int dir = MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;

		if (FMLClientHandler.instance().getClient().inGameHasFocus){
			for(int i=-8;i<=8; i++){
				for(int j=-8;j<=8; j++){
					for(int k=-8;k<=8; k++){
						int blockID = world.getBlockId(xPos+i, yPos+j, zPos+k);
						if(blockID == Block.oreDiamond.blockID){
							xPos += i;
							yPos += j;
							zPos += k;
							((EntityPlayer) player).addChatMessage("Diamonds at: "+xPos+","+yPos+","+zPos);
						}else if(blockID == Block.oreEmerald.blockID){
							xPos += i;
							yPos += j;
							zPos += k;
							((EntityPlayer) player).addChatMessage("Emeralds at: "+xPos+","+yPos+","+zPos);
						}/*else if(blockID == 4085-256 || blockID == 4085){
							xPos += i;
							yPos += j;
							zPos += k;
							((EntityPlayer) player).addChatMessage("Fossil at: " + xPos+","+ yPos+","+zPos);
						}*/
					}
				}
			}

			for(int i=-32;i<=132; i++){
				for(int j=-32;j<=32; j++){
					for(int k=-8;k<=8; k++){
						int blockID = world.getBlockId(xPos+i, yPos+j, zPos+k);
						if(blockID == 2416){
							xPos += i;
							yPos += j;
							zPos += k;
							((EntityPlayer) player).addChatMessage("Node at: "+xPos+","+yPos+","+zPos);
						}
					}
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

