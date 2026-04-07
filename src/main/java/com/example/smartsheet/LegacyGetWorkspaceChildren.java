package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetBuilder;
import com.smartsheet.api.models.Folder;
import com.smartsheet.api.models.Report;
import com.smartsheet.api.models.Sheet;
import com.smartsheet.api.models.Sight;
import com.smartsheet.api.models.Template;
import com.smartsheet.api.models.Workspace;

import java.util.ArrayList;
import java.util.List;

public class LegacyGetWorkspaceChildren {

    public static void main(String[] args) throws Exception {
        
        long workspaceId = parseWorkspaceId(args);
        Smartsheet client = createClientFromEnv();

        Workspace workspace = client.workspaceResources().getWorkspace(workspaceId, null, null);

        List<Sheet> sheets = workspace.getSheets();
        List<Report> reports = workspace.getReports();
        List<Sight> sights = workspace.getSights();
        List<Template> templates = workspace.getTemplates();
        List<Folder> folders = workspace.getFolders();

        System.out.println("Workspace: " + workspace.getName());
        for (Sheet sheet : sheets) {
            System.out.println("Sheet: " + sheet.getName());
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
        for (Folder folder : folders) {
            System.out.println("Folder: " + folder.getName());
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
