package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.models.Folder;
import com.smartsheet.api.models.Report;
import com.smartsheet.api.models.Sheet;
import com.smartsheet.api.models.Sight;
import com.smartsheet.api.models.Template;
import com.smartsheet.api.models.TokenPaginatedResult;

public class GetWorkspaceChildren {

    public static void main(String[] args) throws Exception {
        long workspaceId = ExampleSupport.parseWorkspaceId(args);
        Smartsheet client = ExampleSupport.createClientFromEnv();

        String lastKey = null;
        do {
            TokenPaginatedResult<Object> response = client.workspaceResources()
                    .getWorkspaceChildren(workspaceId, null, null, lastKey, null);

            for (Object child : response.getData()) {
                if (child instanceof Folder folder) {
                    System.out.println("Folder: " + folder.getName());
                } else if (child instanceof Sheet sheet) {
                    System.out.println("Sheet: " + sheet.getName());
                } else if (child instanceof Report report) {
                    System.out.println("Report: " + report.getName());
                } else if (child instanceof Sight sight) {
                    System.out.println("Sight: " + sight.getName());
                } else if (child instanceof Template template) {
                    System.out.println("Template: " + template.getName());
                } else {
                    System.out.println("Unknown: " + child);
                }
            }

            lastKey = response.getLastKey();
        } while (lastKey != null && !lastKey.isBlank());
    }
}
