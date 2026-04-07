package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetBuilder;
import com.smartsheet.api.models.Folder;
import com.smartsheet.api.models.Report;
import com.smartsheet.api.models.Sheet;
import com.smartsheet.api.models.Sight;
import com.smartsheet.api.models.Template;
import com.smartsheet.api.models.Workspace;
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
        
        long workspaceId = parseWorkspaceId(args);
        Smartsheet client = createClientFromEnv();

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

    private static long parseWorkspaceId(String[] args) {
        if (args == null || args.length == 0 || args[0] == null || args[0].isBlank()) {
            throw new IllegalArgumentException("Missing required argument: workspaceId");
        }
        return Long.parseLong(args[0]);
    }

    private static Smartsheet createClientFromEnv() {
        String token = System.getenv("SMARTSHEET_API_TOKEN");
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("SMARTSHEET_API_TOKEN environment variable is required.");
        }
        return new SmartsheetBuilder().setAccessToken(token).build();
    }
}
