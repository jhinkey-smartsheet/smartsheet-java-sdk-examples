package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetBuilder;
import com.smartsheet.api.models.PagedResult;
import com.smartsheet.api.models.PaginationParameters;
import com.smartsheet.api.models.Workspace;

public class ListWorkspaces {

    public static void main(String[] args) throws Exception {
        Smartsheet client = createClientFromEnv();

        String lastKey = null;
        do {
            PagedResult<Workspace> page = client.workspaceResources()
                    .listWorkspaces(
                        new PaginationParameters("token", lastKey, 100));

            for (Workspace workspace : page.getData()) {
                System.out.println("Workspace: " + workspace.getName() + " (ID: " + workspace.getId() + ")");
            }

            lastKey = page.getLastKey();
        } while (lastKey != null && !lastKey.isBlank());
    }

    private static Smartsheet createClientFromEnv() {
        String token = System.getenv("SMARTSHEET_API_TOKEN");
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("SMARTSHEET_API_TOKEN environment variable is required.");
        }
        return new SmartsheetBuilder().setAccessToken(token).build();
    }
}
