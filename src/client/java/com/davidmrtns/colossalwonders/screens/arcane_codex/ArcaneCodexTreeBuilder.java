package com.davidmrtns.colossalwonders.screens.arcane_codex;

import java.util.*;

public class ArcaneCodexTreeBuilder {
    public static List<ArcaneCodexTreeNode> buildTree(Map<String, ArcaneCodexEntryData> entries) {
        Map<String, ArcaneCodexTreeNode> nodes = new HashMap<>();
        List<ArcaneCodexTreeNode> roots = new ArrayList<>();

        // creates all nodes
        for (ArcaneCodexEntryData data : entries.values()) {
            nodes.put(data.id, new ArcaneCodexTreeNode(data));
        }

        // connects the related nodes
        for (ArcaneCodexEntryData data : entries.values()) {
            ArcaneCodexTreeNode node = nodes.get(data.id);

            if (data.parent != null && nodes.containsKey(data.parent)) {
                ArcaneCodexTreeNode parent = nodes.get(data.parent);
                parent.children.add(node);
                node.parent = parent;
            } else {
                roots.add(node); // no parent, so it's a root node
            }
        }

        return roots;
    }
}
