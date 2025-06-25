package com.davidmrtns.colossalwonders.screens.grimoire;

import java.util.List;

public class GrimoireEntryData {
    public String id;
    public String title;
    public String icon;
    public String tooltip;
    public String parent;
    public List<GrimoireContent> content;

    public static class GrimoireContent {
        public String type;
        public String text;     // for "text"
        public String texture;  // for "image"
    }
}

