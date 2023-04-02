package com.hamba.intellijplantumlgeneratorplugin.utils;

import java.util.List;

public class TreeNode {
    private List<TreeNode> children;

    private String name;
    private TreeNode parent;
    public TreeNode(String name, TreeNode parent) {
        this.name = name;
        this.parent = parent;
    }

    public void addChild(TreeNode newChild) {
        children.add(newChild);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

}
