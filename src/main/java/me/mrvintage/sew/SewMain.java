package me.mrvintage.sew;

import com.google.common.base.Supplier;
import me.mrvintage.sew.client.SewModel;
import me.mrvintage.sew.client.SewModelLoader;
import me.mrvintage.sew.test.TestBlock;
import me.mrvintage.sew.test.TestBlockEntity;
import me.mrvintage.sew.test.TestBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SewMain implements ModInitializer, ClientModInitializer {

    public static final TestBlock TEST_BLOCK = new TestBlock();

    public static BlockEntityType<TestBlockEntity> TEST_BLOCK_ENTITY;

    @Override
    public void onInitialize() {
        System.out.println("Init from common!");
        Registry.register(Registry.BLOCK, new Identifier("sew", "test_block"), TEST_BLOCK);

        TEST_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY,
                new Identifier("sew", "test_entity"),
                BlockEntityType.Builder.create(TestBlockEntity::new, TEST_BLOCK).build(null));
    }

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SewModelLoader("sew", (models) -> {
            BlockEntityRendererRegistry.INSTANCE.register(TestBlockEntity.class, new TestBlockEntityRenderer(models.get("sew:block.json")));
        }));
        System.out.println("Init from Client!");
    }
}
