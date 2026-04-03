package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.models.Folder;
import com.smartsheet.api.models.Workspace;

public class LegacyGetWorkspaceHierarchy {

    public static void main(String[] args) throws Exception {
        long workspaceId = ExampleSupport.parseWorkspaceId(args);
        Smartsheet client = ExampleSupport.createClientFromEnv();

        Workspace workspace = client.workspaceResources().getWorkspace(workspaceId, true, null);
        if (workspace.getFolders() == null) {
            return;
        }

        for (Folder folder : workspace.getFolders()) {
            printHierarchy(folder, 0);
        }
    }

    private static void printHierarchy(Folder folder, int level) {
        String indent = "  ".repeat(level);
        System.out.println(indent + "- " + folder.getName() + " (ID: " + folder.getId() + ")");
        if (folder.getFolders() == null) {
            return;
        }
        for (Folder child : folder.getFolders()) {
            printHierarchy(child, level + 1);
        }
    }
}
