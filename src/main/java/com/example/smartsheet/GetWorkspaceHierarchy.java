package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.models.Folder;
import com.smartsheet.api.models.TokenPaginatedResult;
import com.smartsheet.api.models.enums.ChildrenResourceType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class GetWorkspaceHierarchy {

    private static final class TreeNode {
        private final String name;
        private final long id;
        private final List<TreeNode> children = new ArrayList<>();

        private TreeNode(String name, long id) {
            this.name = name;
            this.id = id;
        }
    }

    public static void main(String[] args) throws Exception {
        long workspaceId = ExampleSupport.parseWorkspaceId(args);
        Smartsheet client = ExampleSupport.createClientFromEnv();

        TreeNode workspaceNode = new TreeNode("Workspace", workspaceId);

        String lastKey = null;
        do {
            TokenPaginatedResult<Object> response = client.workspaceResources().getWorkspaceChildren(
                    workspaceId,
                    EnumSet.of(ChildrenResourceType.FOLDERS),
                    null,
                    lastKey,
                    null
            );

            for (Object child : response.getData()) {
                if (child instanceof Folder folder) {
                    TreeNode folderNode = new TreeNode(folder.getName(), folder.getId());
                    workspaceNode.children.add(folderNode);
                    expandTree(client, folderNode);
                }
            }

            lastKey = response.getLastKey();
        } while (lastKey != null && !lastKey.isBlank());

        printTree(workspaceNode, 0);
    }

    private static void expandTree(Smartsheet client, TreeNode parent) throws Exception {
        String lastKey = null;
        do {
            TokenPaginatedResult<Object> response = client.folderResources().getFolderChildren(
                    parent.id,
                    EnumSet.of(ChildrenResourceType.FOLDERS),
                    null,
                    lastKey,
                    null
            );

            for (Object child : response.getData()) {
                if (child instanceof Folder folder) {
                    TreeNode node = new TreeNode(folder.getName(), folder.getId());
                    parent.children.add(node);
                    expandTree(client, node);
                }
            }

            lastKey = response.getLastKey();
        } while (lastKey != null && !lastKey.isBlank());
    }

    private static void printTree(TreeNode node, int level) {
        String indent = "  ".repeat(level);
        System.out.println(indent + "- " + node.name + " (ID: " + node.id + ")");
        for (TreeNode child : node.children) {
            printTree(child, level + 1);
        }
    }
}
