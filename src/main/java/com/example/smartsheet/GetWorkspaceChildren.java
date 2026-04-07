package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetBuilder;
import com.smartsheet.api.models.Folder;
import com.smartsheet.api.models.Report;
import com.smartsheet.api.models.Sheet;
import com.smartsheet.api.models.Sight;
import com.smartsheet.api.models.Template;
import com.smartsheet.api.models.TokenPaginatedResult;
import com.smartsheet.api.models.Workspace;

import java.util.ArrayList;
import java.util.List;

public class GetWorkspaceChildren {

    public static void main(String[] args) throws Exception {
        long workspaceId = parseWorkspaceId(args);
        Smartsheet client = createClientFromEnv();

        Workspace workspaceMetadata = client.workspaceResources().getWorkspaceMetadata(workspaceId, null);

        List<Sheet> sheets = new ArrayList<>();
        List<Report> reports = new ArrayList<>();
        List<Sight> sights = new ArrayList<>();
        List<Template> templates = new ArrayList<>();
        List<Folder> folders = new ArrayList<>();

        String lastKey = null;
        do {
            TokenPaginatedResult<Object> response = client.workspaceResources()
                    .getWorkspaceChildren(workspaceId, null, null, lastKey, null);

            for (Object child : response.getData()) {
                if (child instanceof Folder folder) {
                    folders.add(folder);
                } else if (child instanceof Report report) {
                    reports.add(report);
                } else if (child instanceof Sheet sheet) {
                    sheets.add(sheet);
                } else if (child instanceof Sight sight) {
                    sights.add(sight);
                } else if (child instanceof Template template) {
                    templates.add(template);
                }
            }

            lastKey = response.getLastKey();
        } while (lastKey != null && !lastKey.isBlank());

        System.out.println("Workspace: " + workspaceMetadata.getName());
        for (Folder folder : folders) {
            System.out.println("Folder: " + folder.getName());
        }
        for (Report report : reports) {
            System.out.println("Report: " + report.getName());
        }
        for (Sight sight : sights) {
            System.out.println("Sight: " + sight.getName());
        }
        for (Template template : templates) {
            System.out.println("Template: " + template.getName());
        }
        for (Sheet sheet : sheets) {
            System.out.println("Sheet: " + sheet.getName());
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
