package com.cacho.bakingtime.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("servings")
    private String servings;
    @SerializedName("image")
    private String image;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;
    @SerializedName("steps")
    private List<Steps> steps;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public class Ingredient{
        public String getQuantity() {
            return quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public String getIngredient() {
            return ingredient;
        }

        @SerializedName("quantity")
        private String quantity;
        @SerializedName("measure")
        private String measure;
        @SerializedName("ingredient")
        private String ingredient;
    }

    public class Steps{
        private String id;

        public String getId() {
            return id;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        @SerializedName("shortDescription")
        private String shortDescription;
        @SerializedName("description")
        private String description;
        @SerializedName("videoURL")
        private String videoURL;
        @SerializedName("thumbnailURL")
        private String thumbnailURL;
    }
}
