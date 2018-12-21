package com.example.ilham.smartmuseum.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MuseumCollection {

        @SerializedName("items")
        @Expose
        private List<MuseumItem> items = null;

        public List<MuseumItem> getItems() {
            return items;
        }

        public void setItems(List<MuseumItem> items) {
            this.items = items;
        }
}
