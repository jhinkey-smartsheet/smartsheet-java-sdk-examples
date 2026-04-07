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

## Run each module

All modules take `workspaceId` as a required positional argument.

- `gradle run --args="1234567890"`
  - Runs default main class (`GetWorkspaceChildren`)

- `gradle run --args="$env:WORKSPACE_ID" -PmainClass=com.example.smartsheet.CollectAllWorkspaceSheetIds`

- `gradle run --args="$env:WORKSPACE_ID" -PmainClass=com.example.smartsheet.GetWorkspaceHierarchy`

- `gradle run --args="$env:WORKSPACE_ID" -PmainClass=com.example.smartsheet.LegacyGetWorkspaceChildren`

- `gradle run --args="$env:WORKSPACE_ID" -PmainClass=com.example.smartsheet.LegacyGetWorkspaceHierarchy`

## Notes

- `legacy` examples use deprecated SDK methods (`getWorkspace`) to mirror the Python legacy scripts.
- modern examples use `getWorkspaceChildren` and folder traversal APIs.
