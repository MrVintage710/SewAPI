package me.mrvintage.sew.test;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class TestBlock extends Block implements BlockEntityProvider {

    public TestBlock() {
        super(Settings.of(Material.METAL));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new TestBlockEntity();
    }

    @Override
    public boolean isOpaque(BlockState blockState_1) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.INVISIBLE;
    }
}
