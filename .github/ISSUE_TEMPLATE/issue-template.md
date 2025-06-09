---
name: Issue template
about: Template for an Issue
title: ''
labels: ''
assignees: ''

---

name: "📐 DDD & Clean Architecture Issue"
about: "Report a bug, request a feature, or discuss domain modeling"
title: "[ISSUE] "
labels: ["enhancement"]
---

## 📝 Issue Type
_Select one (or more):_
- [ ] 🐛 Bug
- [ ] ✨ Feature Request
- [ ] 🔄 Refactoring / Tech Debt
- [ ] 📚 Documentation
- [ ] 💡 Domain Modeling Discussion

---

## 📂 Domain / Bounded Context
> Which domain or bounded context does this relate to?  
(e.g. **Billing**, **User Management**, **Catalog**)

**Context:** _Your answer here_

---

## 🏗️ Affected Layer(s)
_Select all that apply:_
- [ ] **Domain** (Entities, Value Objects, Aggregates, Services)
- [ ] **Application** (Use Cases, Commands, Queries)
- [ ] **Infrastructure** (Repositories, Gateways, External APIs)
- [ ] **UI / API** (Controllers, Views, Endpoints)

---

## 📝 Description
_A clear and concise description of the issue or request._

**Description:**  
\`\`\`
Your detailed description here.
\`\`\`

---

## 📈 Proposed Solution or Business Impact
_For a feature: what outcome? For a bug: what happens if we don’t fix it?_

**Impact / Solution:**  
\`\`\`
Your proposed approach or business justification.
\`\`\`

---

### (🛠️ Bugs Only) Steps to Reproduce
1.  
2.  
3.  

**Expected Behavior:**  
\`\`\`
…
\`\`\`

**Actual Behavior:**  
\`\`\`
…
\`\`\`

---

### (💡 Features / Domain Modeling) Domain Model Changes
_Which entities, aggregates, value objects, domain events, or services are you adding or modifying?_

- **Entity / Aggregate:**  
- **Value Object / Event:**  
- **Domain Service:**  
- **Repository Interface:**  

---

### (🔗 API / Endpoints) If applicable
_List new or changed HTTP endpoints, message contracts, or CLI commands._

- `POST /api/v1/orders`  
- `OrderCreated` event on message bus  

---

## 📊 Architecture Diagrams / Screenshots
_If you have a sketch, link to a Lucidchart/PlantUML, or attach a screenshot, paste it here._

---

## 🔍 Additional Context
_Add any other context (logs, stack traces, links to RFCs, etc.)_

\`\`\`
Your extra notes here.
\`\`\`
