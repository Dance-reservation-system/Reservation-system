### 📘 This guide is also available in the [🇵🇱 Polish version](./contribution-pl.md)

# Contribution

Thanks for wanting to help! Before submitting code,
please read and follow these guidelines to keep everything consistent and clean.

## 1. Prerequisites
- Make sure your branch is synced with the latest `develop` branch before starting work.

---


## 2. Task Tracking – GitHub & Linear

- **Issues are created in GitHub** and then **pulled automatically into Linear**.
- Assign yourself to the issue in **Linear**.
- In Linear, each issue includes a suggested branch name. **Copy that name** and create the branch **locally**.
- Always include the **issue ID prefix** (e.g. `rsb-123`) at the start of your PR title.

  Example:

  `rsb-123-implement-instructor-registration`

---


## 3. Branch Naming
- Branch names should follow this format:

`<type>/<issue-id><short-description>`

Where `<type>` is one of:
- `feature/` – for new features
- `fix/` – for bug fixes
- `chore/` – for maintenance or refactoring

**Examples:**
- `feature/rsb-10-setup-ci-pipeline-with-sonarqube`
- `fix/rsb-12-correct-token-expiry-logic`
- `chore/rsb-15-cleanup-unused-dependencies`

**Copy the name directly from Linear.**

**Note:** Branch name format will soon be enforced automatically via GitHub Actions.

---


## 4. Commit Messages

- **Optional**: Use [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

Examples:
```
feat: add instructor registration endpoint  
fix: correct session time overlap validation  
chore: update dependency versions  
```

---


## 5. Pull Requests

- **One PR = one Linear issue.**
- PR title must start with the issue ID, e.g. `rsb-10: implement CI pipeline with SonarQube`.
- Run through the checklist in `.github/code_review_template.md`.
- Use `RESOLVE` in code review comments to confirm feedback has been addressed.

---


## 6. Code Comments

- Do **not** add comments explaining obvious or self-explanatory code.
- Use comments only when:
  - Logic is non-trivial or could be misinterpreted
  - You need to leave a clear **TODO**

Example:
```java
// TODO: validate edge case when user is already activated
```

---

## 7. Deprecating Code

When deprecating functionality, annotate and explain:

```java
@Deprecated("Use NewFeature instead")
public void oldMethod() {}
```

Also:
- Explain the deprecation rationale in the PR description
- Start a discussion if migration/removal plan is unclear

---


## 8. CI / Automation

We're gradually introducing CI rules that will:
- Require valid branch names (e.g. `feature/rsb-XX-description`)
- Enforce PR title format
- Fail builds on test failures or critical code quality issues via SonarQube

---

## Questions?

- Use comments in your PR
- Tag a reviewer or maintainer for clarification
