package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetBuilder;
import com.smartsheet.api.models.Folder;
import com.smartsheet.api.models.PagedResult;
import com.smartsheet.api.models.PaginationParameters;

import java.util.ArrayList;
import java.util.List;

public class LegacyListFolders {

    public static void main(String[] args) throws Exception {
        long folderId = parseFolderId(args);
        Smartsheet client = createClientFromEnv();

        PagedResult<Folder> page = client.folderResources().listFolders(
            folderId, new PaginationParameters(true, null, null));

        for (Folder folder : page.getData()) {
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
