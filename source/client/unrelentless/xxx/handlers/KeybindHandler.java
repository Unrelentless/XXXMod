package unrelentless.xxx.handlers;

import java.util.EnumSet;
import java.util.Iterator;

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
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeybindHandler extends KeyHandler
{
	/** Not really important. I use it to store/find keys in the config file */
	public static final String label = "Scan Key";
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
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		scanAndPrint(player);
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
						}else if(blockID == 4085-256 || blockID == 4085){
							xPos += i;
							yPos += j;
							zPos += k;
							((EntityPlayer) player).addChatMessage("Fossil at: " + xPos+","+ yPos+","+zPos);
						}

					}
				}
			}

			float closest = 64F;
			Entity thisEntity;
			String entityName;
			for (int l = 0; l < world.loadedEntityList.size(); l++){

				if (((Entity)world.loadedEntityList.get(l)).getDistanceToEntity(player)<closest)
				{
					if (world.loadedEntityList.get(l) instanceof EntityAnimal)
					{
						thisEntity = ((Entity)world.loadedEntityList.get(l));
						entityName = ((Entity)world.loadedEntityList.get(l)).getEntityName();
						int entityPosX = (int)((Entity)world.loadedEntityList.get(l)).posX;
						int entityPosY = (int)((Entity)world.loadedEntityList.get(l)).posY;
						int entityPosZ = (int)((Entity)world.loadedEntityList.get(l)).posZ;	
						NBTTagCompound compound = new NBTTagCompound();
						thisEntity.writeToNBT(compound);

						/*						player.addChatMessage("Growth: " + compound.getShort("Growth"));
						player.addChatMessage("IsShiny: " + compound.getBoolean("IsShiny"));
						player.addChatMessage("Level: " + compound.getInteger("Level"));
						player.addChatMessage("BossMode: " + compound.getShort("BossMode"));
						 */
						if(compound.getBoolean("IsShiny")){	
							player.addChatMessage(EnumChatFormatting.GOLD + "Shiny " +entityName +" due: "+ checkDir(player, dir, entityPosX, entityPosZ) +"("+entityPosX+","+entityPosY+","+entityPosZ +")");
						}else if(compound.getShort("BossMode")>0){
							player.addChatMessage(EnumChatFormatting.RED + "Boss " +entityName +" due: "+ checkDir(player, dir, entityPosX, entityPosZ) +"("+entityPosX+","+entityPosY+","+entityPosZ +")");
						}else if(compound.getShort("Growth")==6 || compound.getShort("Growth")==0){
							player.addChatMessage("Pygmy/Enormous " +entityName +" due: "+ checkDir(player, dir, entityPosX, entityPosZ) +"("+entityPosX+","+entityPosY+","+entityPosZ +")");
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

