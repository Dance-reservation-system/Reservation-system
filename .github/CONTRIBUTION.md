# Contribution

Thanks for wanting to help! Before you submit code, read these guidelines.

## 1. Prerequisites
- Sync your branch with `develop`.

## 2. Branch Naming
Branches should tell you what they do at a glance:
This is only a suggestion:
```
<type>-<short-description>
```
Examples: `feat-add-instructor-domain`, `fix-login-bug`

## 3. Commit Messages
- **Optional**: follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).
- In short:
  ```
  feat: add new instructor endpoint
  fix: correct email validation bug
  chore: update dependencies
  ```

## 4. Pull Requests
1. Run through the checklist in `.github/CODE_REVIEW_TEMPLATE.md`.  
2. Use `RESOLVE` in comments when addressing feedback.  
3. One PR = one issue/feature.

## 5. Code Comments
- **No** unnecessary comments—don’t explain what obvious code does.
- For unfinished work, use `// TODO: what’s left to implement, or missing future implementation`.

## 6. Deprecated Code
When deprecating:
```java
@Deprecated("Use NewFeature instead")
public void oldMethod() { … }
```
Then open a discussion in the PR (“Why deprecated?”, “When to remove?”).

## 7. CI / Automation
- Coming soon: CI or issue-tracking tool will enforce issue numbers in branch names and merge commits.
