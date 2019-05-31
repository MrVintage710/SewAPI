package me.mrvintage.sew.test;

import me.mrvintage.sew.client.SewBlockEntityRenderer;
import me.mrvintage.sew.client.SewModel;

public class TestBlockEntityRenderer extends SewBlockEntityRenderer<TestBlockEntity> {

    public TestBlockEntityRenderer(SewModel model) {
        super(model);
    }

    @Override
    protected void decideState(TestBlockEntity blockEntity) {

    }
}
