# MCP Configuration Instructions

This file explains how to configure Model Context Protocol (MCP) servers
for your project. It is generated from a template and tailored for each project.

---

## 1. Local Filesystem MCP

- **Purpose**: Allows Claude Code to navigate the project file tree efficiently
- **Root directory**: /Users/ymor/github/canmogol/spring-boot-test
- **Server command**:
```bash
npx @modelcontextprotocol/server-filesystem --path "/Users/ymor/github/canmogol/spring-boot-test"
```

## 2. GitHub MCP (optional)
- **Purpose**: Efficient issue management, pull request creation, and repository analysis
- **Repository**: https://github.com/canmogol/spring-boot-test.git
- **Required**: GITHUB_TOKEN environment variable

```bash
export GITHUB_TOKEN=<your-token>
```
- **Add MCP server command**:
```
claude mcp add github --transport http "https://github.com/canmogol/spring-boot-test.git"
```
⚠️ If `GITHUB_TOKEN` is not set, GitHub MCP will not work.

## 3. Local Git MCP (optional)
- **Purpose**: Analyze commit history, blame, logs.
- **Root directory**: /Users/ymor/github/canmogol/spring-boot-test
- **Server command**:
```bash
npx @modelcontextprotocol/server-git --path "/Users/ymor/github/canmogol/spring-boot-test"
```

## Best Practices
1. Load skills once per session - avoids unnecessary token usage
2. Batch operations - process multiple files/issues together
3. Use MCP whenever possible - more efficient than shell commands
4. Keep this file updated - helps onboarding new contributors

