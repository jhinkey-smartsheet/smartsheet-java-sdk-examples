package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.models.Report;
import com.smartsheet.api.models.Sheet;
import com.smartsheet.api.models.Sight;
import com.smartsheet.api.models.Template;
import com.smartsheet.api.models.Workspace;

public class LegacyGetWorkspaceChildren {

    public static void main(String[] args) throws Exception {
        long workspaceId = ExampleSupport.parseWorkspaceId(args);
        Smartsheet client = ExampleSupport.createClientFromEnv();

        Workspace workspace = client.workspaceResources().getWorkspace(workspaceId, null, null);

        if (workspace.getSheets() != null) {
            for (Sheet sheet : workspace.getSheets()) {
                System.out.println("Sheet: " + sheet.getName());
            }
        }
        if (workspace.getReports() != null) {
            for (Report report : workspace.getReports()) {
                System.out.println("Report: " + report.getName());
            }
        }
        if (workspace.getSights() != null) {
            for (Sight sight : workspace.getSights()) {
                System.out.println("Sight: " + sight.getName());
            }
        }
        if (workspace.getTemplates() != null) {
            for (Template template : workspace.getTemplates()) {
                System.out.println("Template: " + template.getName());
            }
        }
    }
}
