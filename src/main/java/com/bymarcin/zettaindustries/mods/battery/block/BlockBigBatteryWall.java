package com.bymarcin.zettaindustries.mods.battery.block;

import com.bymarcin.zettaindustries.mods.battery.erogenousbeef.core.multiblock.IMultiblockPart;
import com.bymarcin.zettaindustries.mods.battery.erogenousbeef.core.multiblock.MultiblockControllerBase;
import com.bymarcin.zettaindustries.mods.battery.tileentity.TileEntityWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class BlockBigBatteryWall extends BasicBlockMultiblockBase {
    public BlockBigBatteryWall() {
        super("batterywall");
        info.add(localize("tooltip.validfor") + " " + localize("tooltip.bottom") + ", " + localize("tooltip.top") +
                ", " + localize("tooltip.sides") + ", " + localize("tooltip.frame"));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityWall();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            return false;
        }

        if (!world.isRemote) {
            if (heldItem == null) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof IMultiblockPart) {
                    MultiblockControllerBase controller = ((IMultiblockPart) te).getMultiblockController();
                    if (controller != null) {
                        Exception e = controller.getLastValidationException();
                        if (e != null) {
                            player.addChatMessage(new TextComponentString(e.getMessage()));
                            return true;
                        }
                    } else {
                        player.addChatMessage(new TextComponentString("Block is not connected to a battery. This could be due to lag, or a bug. If the problem persists, try breaking and re-placing the block."));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
