package com.davidmrtns.colossalwonders.screens.arcane_codex;

import java.util.ArrayList;
import java.util.List;

public class ArcaneCodexTreeNode {
    public final ArcaneCodexEntryData data;
    public final List<ArcaneCodexTreeNode> children = new ArrayList<>();
    public ArcaneCodexTreeNode parent = null;

    public ArcaneCodexTreeNode(ArcaneCodexEntryData data) {
        this.data = data;
    }
}

