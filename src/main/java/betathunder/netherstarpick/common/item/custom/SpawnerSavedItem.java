package betathunder.netherstarpick.common.item.custom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnerSavedItem extends BlockItem {

	public static final Logger LOGGER = LogManager.getLogger();

	public SpawnerSavedItem(Block p_i48527_1_, Properties p_i48527_2_) {
		super(p_i48527_1_, p_i48527_2_);
	}

	@Override
	public ActionResultType place(BlockItemUseContext p_195942_1_) {
		if (!p_195942_1_.canPlace()) {
			return ActionResultType.FAIL;
		} else {
			BlockItemUseContext blockitemusecontext = this.updatePlacementContext(p_195942_1_);
			if (blockitemusecontext == null) {
				return ActionResultType.FAIL;
			} else {
				BlockState blockstate = this.getPlacementState(blockitemusecontext);
				if (blockstate == null) {
					return ActionResultType.FAIL;
				} else if (!this.placeBlock(blockitemusecontext, blockstate)) {
					return ActionResultType.FAIL;
				} else {
					BlockPos blockpos = blockitemusecontext.getClickedPos();
					World world = blockitemusecontext.getLevel();
					PlayerEntity playerentity = blockitemusecontext.getPlayer();
					ItemStack itemstack = blockitemusecontext.getItemInHand();
					BlockState blockstate1 = world.getBlockState(blockpos);
					Block block = blockstate1.getBlock();
					if (block == blockstate.getBlock()) {
						world.setBlock(blockpos, Blocks.SPAWNER.defaultBlockState(), 1);
						if (!world.isClientSide) {
							TileEntity spawnerTileEntity = world.getBlockEntity(blockpos);
							CompoundNBT compoundnbt = itemstack.getTag();
							if (compoundnbt != null) {
								compoundnbt = compoundnbt.getCompound("BlockEntityTag");
							} else {
								compoundnbt = new CompoundNBT();
								LOGGER.warn(
										"Tried to create a spawner with no data assigned to it! We will default it to a pig spawner.");
							}
							compoundnbt.putInt("x", blockpos.getX());
							compoundnbt.putInt("y", blockpos.getY());
							compoundnbt.putInt("z", blockpos.getZ());
							BlockState blockStateUpdated = world.getBlockState(blockpos);
							spawnerTileEntity.load(blockStateUpdated, compoundnbt);
							block.setPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
							if (playerentity instanceof ServerPlayerEntity) {
								CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerentity, blockpos,
										itemstack);
							}

						}
						if (playerentity == null || !playerentity.abilities.instabuild) {
							itemstack.shrink(1);
						}
					}
					SoundType soundtype = blockstate1.getSoundType(world, blockpos, p_195942_1_.getPlayer());
					world.playSound(playerentity, blockpos,
							this.getPlaceSound(blockstate1, world, blockpos, p_195942_1_.getPlayer()),
							SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

					return ActionResultType.sidedSuccess(world.isClientSide);
				}
			}
		}
	}
}
