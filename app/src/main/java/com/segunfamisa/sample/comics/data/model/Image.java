package com.segunfamisa.sample.comics.data.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Comic image model.
 */
public class Image {

    @SerializedName("path")
    private String path;

    @SerializedName("extension")
    private String extension;

    public Image() {
    }

    private Image(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }

    /**
     * Image url
     * @return the full url in the format {path.extension}
     *          or null if either path or extension is null
     */
    @Nullable
    public String getUrl() {
        if (getPath() == null || getExtension() == null) {
            return null;
        }
        return String.format("%s.%s", getPath(), getExtension());
    }

    /**
     * Builder class.
     */
    public static class Builder {
        private String path;
        private String extension;

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setExtension(String extension) {
            this.extension = extension;
            return this;
        }

        public Image build() {
            return new Image(path, extension);
        }
    }
}
