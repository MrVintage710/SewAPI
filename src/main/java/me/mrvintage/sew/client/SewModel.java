package me.mrvintage.sew.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Cuboid;
import net.minecraft.client.model.Model;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class SewModel extends Model {

    private SewModelType type;

    public SewModel(String data, SewModelType type) {
        this.type = type;
        switch (type) {
            case BEDROCK:
                initBedrockModel(data);
                break;
            case SEW_MODEL:
                initSewModel();
                break;
        }
    }

    private void initBedrockModel(String data) {
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(data, JsonElement.class).getAsJsonObject();
        for(Map.Entry<String, JsonElement> entry: obj.entrySet()) {
            if(entry.getKey().equals("format_version")) {
                //TODO: ADD BDRK Versioning
            } else {
                String[] split = entry.getKey().split("\\.");
                System.out.println(split.length);
                if(split[0].equals("geometry")) {
                    JsonObject geo = entry.getValue().getAsJsonObject();
                    this.textureWidth = geo.get("texturewidth").getAsInt();
                    this.textureHeight = geo.get("textureheight").getAsInt();
                    JsonArray bones = geo.get("bones").getAsJsonArray();
                    for(JsonElement element: bones) {
                        this.addCuboid(element.getAsJsonObject());
                    }
                }
            }
        }

    }

    private void initSewModel() {

    }

    private void addCuboid(JsonObject obj) {
        JsonArray pivot = obj.get("pivot").getAsJsonArray();
        JsonArray cubes = obj.get("cubes").getAsJsonArray();
        String name = obj.get("name").getAsString();

        Cuboid cuboid = new Cuboid(this, name);
        cuboid.setRotationPoint(pivot.get(0).getAsInt(), pivot.get(1).getAsInt(), pivot.get(2).getAsInt());

        int index = 0;
        for (JsonElement cube: cubes) {
            JsonObject cubeObj = cube.getAsJsonObject();
            JsonArray origin = cubeObj.get("origin").getAsJsonArray();
            JsonArray size = cubeObj.get("size").getAsJsonArray();
            JsonArray uv = cubeObj.get("uv").getAsJsonArray();

            cuboid.addBox(name + ":cube_" + index,
                    origin.get(0).getAsFloat(),
                    origin.get(1).getAsFloat(),
                    origin.get(2).getAsFloat(),
                    size.get(0).getAsInt(),
                    size.get(1).getAsInt(),
                    size.get(2).getAsInt(),
                    0.0f,
                    uv.get(0).getAsInt(),
                    uv.get(1).getAsInt());
            index++;
        }
    }

    public void render() {
        for (Cuboid cuboid: this.cuboidList) {
            cuboid.render(0.0625F);
        }
    }

    public SewModelType getType() {
        return type;
    }
}
