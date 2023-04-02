package com.hamba.intellijplantumlgeneratorplugin.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> {
    protected List<TreeNode> children = new ArrayList<>();

    protected T data;
    private TreeNode parent;

    public TreeNode(T data, TreeNode parent) {
        this.data = data;
        this.parent = parent;
    }

    public void addChild(TreeNode<T> newChild) {
        children.add(newChild);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }
}
