package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetBuilder;
import com.smartsheet.api.models.Folder;
import com.smartsheet.api.models.Workspace;

public class LegacyGetWorkspaceHierarchy {

    public static void main(String[] args) throws Exception {
        
        long workspaceId = parseWorkspaceId(args);
        Smartsheet client = createClientFromEnv();

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
