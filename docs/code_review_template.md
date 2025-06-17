## 📋 Code Review Checklist

### 1. Branches & Commits
- [ ] Branch name clearly describes its purpose (e.g. `feature/rs-10-setup-ci-pipeline-with-sonarqube` Linear helps with that :) ).
- [ ] (Optional) Commits follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) – you don’t have to, but it makes automation easier.

### 2. Pull Request
- [ ] PR covers exactly one issue/feature.
- [ ] In PR comments: when a comment is addressed, mark `RESOLVED`.
- [ ] No loose “fix this later” comments.

### 3. Code & Comments
- [ ] **Zero** unnecessary comments in code.
- [ ] If functionality isn’t ready yet, use `// TODO: describe what’s left or to be implemented in the future`.

### 4. Quality
- [ ] Local SonarQube/formatter set up and build passes without warnings. (to be configured/added)
- [ ] If a feature is slated for replacement, annotate with `@Deprecated` and start a discussion in the PR.

### 5. Merge Commit
- [ ] Merge commit title clearly states the feature (e.g. `feature: Add instructor domain` / add-instructor-domain), or any else (descriptive).
- [ ] (Optional) Prefix with the issue number `123`-feature-name, CI will handle this soon (or not, depends on Issue tracker atm.)

