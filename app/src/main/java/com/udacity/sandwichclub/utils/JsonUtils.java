package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichDetailsJson = new JSONObject(json);
            JSONObject nameJson = sandwichDetailsJson.getJSONObject(NAME);
            String mainName = parseMainName(nameJson);
            List<String> asKnownAs = parseAlsoKnownAs(nameJson);
            String placeOfOrigin = sandwichDetailsJson.getString(PLACE_OF_ORIGIN);
            String description = sandwichDetailsJson.getString(DESCRIPTION);
            String image = sandwichDetailsJson.getString(IMAGE);
            List<String> ingredients = parseIngredients(sandwichDetailsJson);
            return new Sandwich(mainName, asKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            Log.d(TAG, "Exception occurred while parsing json");
        }
        return null;
    }

    private static List<String> parseIngredients(JSONObject ingredientsJson) throws JSONException {
        JSONArray ingredientsArray = ingredientsJson.getJSONArray(INGREDIENTS);
        return list(ingredientsArray);
    }

    private static String parseMainName(JSONObject nameJson) throws JSONException {
        return nameJson.getString(MAIN_NAME);
    }

    private static List<String> parseAlsoKnownAs(JSONObject nameJson) throws JSONException {
        JSONArray alsoKnownAsArray = nameJson.getJSONArray(ALSO_KNOWN_AS);
        return list(alsoKnownAsArray);
    }

    private static List<String> list(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int pos = 0; pos < jsonArray.length(); pos++) {
            list.add(jsonArray.getString(pos));
        }
        return list;
    }
}
