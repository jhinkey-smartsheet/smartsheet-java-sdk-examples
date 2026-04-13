package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetBuilder;
import com.smartsheet.api.models.ShareResponse;
import com.smartsheet.api.models.UpdateShareRequest;
import com.smartsheet.api.models.enums.AccessLevel;

public class UpdateShareSheet {

    public static void main(String[] args) throws Exception {
        String sheetId = requiredEnv("SHEET_ID");
        String shareId = requiredEnv("SHEET_SHARE_ID");
        Smartsheet client = createClientFromEnv();

        UpdateShareRequest shareRequest = new UpdateShareRequest.UpdateShareRequestBuilder()
                .setAccessLevel(AccessLevel.EDITOR)
                .build();

        ShareResponse shareResponse = client.assetShareResources().updateShare(
                shareId,
                sheetId,
                "sheet",
                shareRequest);

        printShare(shareResponse);

        ShareResponse getShareResponse = client.assetShareResources().getShare(
                shareId,
                sheetId,
                "sheet");
        
        printShare(getShareResponse);

        client.assetShareResources().deleteShare(
                shareId,
                sheetId,
                "sheet");
    }

    private static void printShare(ShareResponse response) {
        System.out.println("Share "
                + "\nid: " + response.getId()
                + "\ntype:" + response.getType()
                + "\ngroupId: " + response.getGroupId()
                + "\nemail: " + response.getEmail()
                + "\nname: " + response.getName()
                + "\nuserId: " + response.getUserId()
                + "\naccessLevel: " + response.getAccessLevel()
                + "\nscope: " + response.getScope());
    }

    private static String requiredEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Environment variable " + name + " is required.");
        }
        return value.trim();
    }

    private static Smartsheet createClientFromEnv() {
        String token = System.getenv("SMARTSHEET_API_TOKEN");
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("SMARTSHEET_API_TOKEN environment variable is required.");
        }
        return new SmartsheetBuilder().setAccessToken(token).build();
    }
}
