package com.davidmrtns.colossalwonders.screens.grimoire;

import java.util.ArrayList;
import java.util.List;

public class GrimoireTreeNode {
    public final GrimoireEntryData data;
    public final List<GrimoireTreeNode> children = new ArrayList<>();
    public GrimoireTreeNode parent = null;

    public GrimoireTreeNode(GrimoireEntryData data) {
        this.data = data;
    }
}

