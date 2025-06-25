package com.davidmrtns.colossalwonders.screens.grimoire;

import java.util.*;

public class GrimoireTreeBuilder {
    public static List<GrimoireTreeNode> buildTree(Map<String, GrimoireEntryData> entries) {
        Map<String, GrimoireTreeNode> nodes = new HashMap<>();
        List<GrimoireTreeNode> roots = new ArrayList<>();

        // creates all nodes
        for (GrimoireEntryData data : entries.values()) {
            nodes.put(data.id, new GrimoireTreeNode(data));
        }

        // connects the related nodes
        for (GrimoireEntryData data : entries.values()) {
            GrimoireTreeNode node = nodes.get(data.id);

            if (data.parent != null && nodes.containsKey(data.parent)) {
                GrimoireTreeNode parent = nodes.get(data.parent);
                parent.children.add(node);
                node.parent = parent;
            } else {
                roots.add(node); // no parent, so it's a root node
            }
        }

        return roots;
    }
}
