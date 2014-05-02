package unrelentless.xxx.handlers;

import java.util.Iterator;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import unrelentless.xxx.lib.Config;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class UpdateHandler {
	private int tickTime = 0;

	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void onUpdate(LivingUpdateEvent event){
		if(Config.enableSearch){
			if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer && (tickTime % (Config.scanTime*20 + 10) == 0)){

				EntityPlayer player = (EntityPlayer) event.entity;
				World world = player.worldObj;

				int xPos = (int)player.posX;
				int yPos = (int)player.posY;
				int zPos = (int)player.posZ;
				int dir = MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;

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
				tickTime = 0;
			}
			tickTime++;
		}
	}
}
