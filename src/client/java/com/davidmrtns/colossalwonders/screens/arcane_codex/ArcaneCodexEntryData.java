package com.davidmrtns.colossalwonders.screens.arcane_codex;

import java.util.List;

public class ArcaneCodexEntryData {
    public String id;
    public String title;
    public String icon;
    public String tooltip;
    public String parent;
    public List<ArcaneCodexContent> content;

    public static class ArcaneCodexContent {
        public String type;
        public String text;     // for "text"
        public String texture;  // for "image"
    }
}

