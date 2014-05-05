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

	}
}

