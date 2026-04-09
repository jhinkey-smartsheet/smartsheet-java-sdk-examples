# smartsheet-java-sdk-examples

Java development environment with Smartsheet SDK examples, modeled after the Python examples.

## Prerequisites

1. Install Java 17 (JDK).
2. Install Gradle (or use a local Gradle install in your PATH).
3. Set your Smartsheet API token:
   - PowerShell: `$env:SMARTSHEET_API_TOKEN = "<your-token>"`
   - Linux/macOS: `export SMARTSHEET_API_TOKEN="<your-token>"`
4. Set your workspace ID (optional convenience):
   - PowerShell: `$env:WORKSPACE_ID = "<workspace-id>"`
   - Linux/macOS: `export WORKSPACE_ID="<workspace-id>"`

## Build

- `gradle build`

## Run examples

### How to use

Every class uses `SMARTSHEET_API_TOKEN` from the environment.
Set IDs as needed:

- PowerShell:
  - `$env:WORKSPACE_ID = "<workspace-id>"`
  - `$env:FOLDER_ID = "<folder-id>"`
- Linux/macOS:
  - `export WORKSPACE_ID="<workspace-id>"`
  - `export FOLDER_ID="<folder-id>"`

Run each class with dedicated Gradle tasks:

- `GetWorkspaceChildren` (`workspaceId`): `./gradlew runGetWorkspaceChildren -PappArgs="$WORKSPACE_ID"`
- `GetWorkspaceHierarchy` (`workspaceId`): `./gradlew runGetWorkspaceHierarchy -PappArgs="$WORKSPACE_ID"`
- `LegacyGetWorkspaceChildren` (`workspaceId`): `./gradlew runLegacyGetWorkspaceChildren -PappArgs="$WORKSPACE_ID"`
- `LegacyGetWorkspaceHierarchy` (`workspaceId`): `./gradlew runLegacyGetWorkspaceHierarchy -PappArgs="$WORKSPACE_ID"`
- `GetFolderChildren` (`folderId`): `./gradlew runGetFolderChildren -PappArgs="$FOLDER_ID"`
- `LegacyGetFolder` (`folderId`): `./gradlew runLegacyGetFolder -PappArgs="$FOLDER_ID"`
- `LegacyListFolders` (`folderId`): `./gradlew runLegacyListFolders -PappArgs="$FOLDER_ID"`
- `ListWorkspaces` (no args): `./gradlew runListWorkspaces`

You can also run any class using the generic task:

- `./gradlew runExample -PmainClass=com.example.smartsheet.GetFolderChildren -PappArgs="$FOLDER_ID"`
- `./gradlew runExample -PmainClass=com.example.smartsheet.ListWorkspaces`

PowerShell examples:

- `.\gradlew.bat runGetWorkspaceChildren -PappArgs="$env:WORKSPACE_ID"`
- `.\gradlew.bat runGetFolderChildren -PappArgs="$env:FOLDER_ID"`
- `.\gradlew.bat runLegacyGetFolder -PappArgs="$env:FOLDER_ID"`
- `.\gradlew.bat runLegacyListFolders -PappArgs="$env:FOLDER_ID"`
- `.\gradlew.bat runListWorkspaces`
- `.\gradlew.bat runExample -PmainClass=com.example.smartsheet.GetFolderChildren -PappArgs="$env:FOLDER_ID"`
- `.\gradlew.bat runExample -PmainClass=com.example.smartsheet.ListWorkspaces`

## Notes

- `legacy` examples use deprecated SDK methods (`getWorkspace`) to mirror the Python legacy scripts.
- modern examples use `getWorkspaceChildren` and folder traversal APIs.
