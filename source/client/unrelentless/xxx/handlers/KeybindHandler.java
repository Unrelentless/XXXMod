package unrelentless.xxx.handlers;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
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
						((EntityPlayer) player).addChatMessage("Fossil at: " + xPos+","+ yPos+","+zPos);
					}

				}
			}
		}

		float closest = 64F;
		Entity thisOne=null;
		String entityName = "";
		for (int l = 0; l < world.loadedEntityList.size(); l++){

			if (((Entity)world.loadedEntityList.get(l)).getDistanceToEntity(player)<closest)
			{
				if (world.loadedEntityList.get(l) instanceof EntityAnimal) //if it is a mob...
				{
					//closest = ((Entity)world.loadedEntityList.get(l)).getDistanceToEntity(player);
					thisOne = ((Entity)world.loadedEntityList.get(l));
					entityName = ((Entity)world.loadedEntityList.get(l)).getEntityName();
					int entityPosX = (int)((Entity)world.loadedEntityList.get(l)).posX;
					int entityPosY = (int)((Entity)world.loadedEntityList.get(l)).posY;
					int entityPosZ = (int)((Entity)world.loadedEntityList.get(l)).posZ;
					player.addChatMessage(entityName +" : "+entityPosX+","+entityPosY+","+entityPosZ);
					player.addChatMessage("" + ((Entity)world.loadedEntityList.get(l)).getEntityData().getTags());
				}
			}
		}
	}
}
