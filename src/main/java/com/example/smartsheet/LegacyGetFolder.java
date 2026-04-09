package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetBuilder;
import com.smartsheet.api.models.Folder;
import com.smartsheet.api.models.Report;
import com.smartsheet.api.models.Sheet;
import com.smartsheet.api.models.Sight;
import com.smartsheet.api.models.Template;
import com.smartsheet.api.models.TokenPaginatedResult;

import java.util.ArrayList;
import java.util.List;

public class LegacyGetFolder {

    public static void main(String[] args) throws Exception {
        long folderId = parseFolderId(args);
        Smartsheet client = createClientFromEnv();

        Folder parentFolder = client.folderResources().getFolder(folderId, null);

        System.out.println("Parent Folder " +
            "\n  name: " + parentFolder.getName() +
            "\n  id: " + parentFolder.getId() +
            "\n  permalink: " + parentFolder.getPermalink() +
            "\n  created at: " + parentFolder.getCreatedAt() +
            "\n  modified at: " + parentFolder.getModifiedAt());

        List<Sheet> sheets = parentFolder.getSheets();
        List<Report> reports = parentFolder.getReports();
        List<Sight> sights = parentFolder.getSights();
        List<Template> templates = parentFolder.getTemplates();
        List<Folder> folders = parentFolder.getFolders();

        System.out.println("Workspace: " + parentFolder.getName());
        for (Sheet sheet : sheets) {
            System.out.println("Sheet: " + sheet.getName()
            + "\n  id: " + sheet.getId()
            + "\n  permalink: " + sheet.getPermalink()
            + "\n  created at: " + sheet.getCreatedAt()
            + "\n  modified at: " + sheet.getModifiedAt()
            + "\n  access level: " + sheet.getAccessLevel());
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
            System.out.println("Folder: " + folder.getName()
            + "\n  id: " + folder.getId()
            + "\n  permalink: " + folder.getPermalink());
        }
    }

    private static long parseFolderId(String[] args) {
        if (args == null || args.length == 0 || args[0] == null || args[0].isBlank()) {
            throw new IllegalArgumentException("Missing required argument: folderId");
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
