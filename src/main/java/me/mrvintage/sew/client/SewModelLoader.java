package me.mrvintage.sew.client;

import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SewModelLoader implements SimpleResourceReloadListener {

    private static final HashMap<String, SewModel> MODELS = new HashMap<>();
    private SewModelLoaderCallback callback;
    private String namespace;

    public SewModelLoader(String namespace, SewModelLoaderCallback callback) {
        this.namespace = namespace;
        this.callback = callback;
    }

    @Override
    public CompletableFuture load(ResourceManager resourceManager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            Collection<Identifier> files =  resourceManager.findResources("sew_data/models/" + namespace, (text) -> {
                //return text.split("\\.")[1].equals("json") ? true : false;
                return true;
            });
            for (Identifier file: files) {
                try {
                    Resource resource = resourceManager.getResource(file);
                    String data = IOUtils.toString(resource.getInputStream());
                    SewModel model = new SewModel(data, SewModelType.BEDROCK);
                    MODELS.put(namespace + ":" + FilenameUtils.getName(file.getPath()), model);
                    System.out.println("Loaded Model: " + namespace + ":" + FilenameUtils.getName(file.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        });
    }

    @Override
    public CompletableFuture<Void> apply(Object o, ResourceManager resourceManager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> {
            callback.onLoadComplete(MODELS);
            System.out.println("APPLY");
        });
    }

    @Override
    public Identifier getFabricId() {
        return null;
    }
}
