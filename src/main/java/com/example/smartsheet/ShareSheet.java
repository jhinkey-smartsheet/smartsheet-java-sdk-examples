package com.example.smartsheet;

import com.smartsheet.api.Smartsheet;
import com.smartsheet.api.SmartsheetBuilder;
import com.smartsheet.api.models.enums.AccessLevel;
import com.smartsheet.api.models.Sheet;
import com.smartsheet.api.models.ShareResponse;
import com.smartsheet.api.models.CreateShareRequest;

import java.util.ArrayList;
import java.util.List;

public class ShareSheet {

    public static void main(String[] args) throws Exception {
        long sheetId = parseSheetId(args);
        Smartsheet client = createClientFromEnv();

        CreateShareRequest shareRequest = new
            CreateShareRequest.CreateShareRequestBuilder()
                .setEmail("jane.doe@smartsheet.com")
                .setAccessLevel(AccessLevel.VIEWER)
                .setMessage("What do you think of this sheet?")
                .setSubject("Sheet for review")
                .build();
        
        List<CreateShareRequest> shareRequests =
            new ArrayList<CreateShareRequest>();
        shareRequests.add(shareRequest);

        List<ShareResponse> shareResponses = client.assetShareResources().shareTo(
            Long.toString(sheetId), "sheet", shareRequests, true);

        for (ShareResponse response : shareResponses) {
            System.out.println("Share " +
                "\nid: " + response.getId() +
                "\ntype:" + response.getType() +
                "\ngroupId: " + response.getGroupId() +                
                "\nemail: " + response.getEmail() +
                "\nname: " + response.getName() +
                "\nuserId: " + response.getUserId() +
                "\naccessLevel: " + response.getAccessLevel() +
                "\nscope: " + response.getScope());

        }
    }

    private static long parseSheetId(String[] args) {
        if (args == null || args.length == 0 || args[0] == null || args[0].isBlank()) {
            throw new IllegalArgumentException("Missing required argument: sheetId");
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
