### Mobile prototype
- **Tool:** Claude Design
- **Task:** Turn our Figma web prototype PDF into a hi-fi interactive Mobile prototype.
- **Accepted / modified / rejected:** Accepted the generated 8-page interactive prototype as our visual reference. Some details were adjusted by us when building the actual Ionic views. The result of the prototype, we edited again to fit our requirements and created a 8 screen png from it.
- **Why:** Faster than designing every screen again in Figma.

### Project approach / planning
- **Tool:** Claude Opus 4.7
- **Task:** Asked how to approach the project and in what order to build things.
- **Accepted / modified / rejected:** Accepted the suggested order (database first, then backend, then frontend). We decided the actual scope and milestones ourselves.
 
### Understanding compile errors
- **Tool:** GitHub Copilot
- **Task:** Explain compile errors that weren't clear to us and suggest fixes.
- **Accepted / modified / rejected:** Accepted the explanations and applied fixes after we understood them. Suggestions that didn't fit our code were rejected.
- **Why:** Some error messages were hard to read; the explanations saved time and helped us learn what was actually wrong.

### Test data (seed.sql)
- **Tool:** Claude Opus 4.7
- **Task:** Generate example data for seed.sql (rooms, extras, bookings -> to simulate availability).
- **Accepted / modified / rejected:** Accepted the generated INSERT statements, then adjusted values and IDs to match our schema.
- **Why:** We needed realistic test data quickly so we could try out the app during development.

### README setup guide
- **Tool:** Claude Opus 4.8
- **Task:** Rewrite the README with a clear setup guide (database, backend, frontend).
- **Accepted / modified / rejected:** Accepted the rewritten structure and steps. We checked the commands and the DB credentials against our actual setup before using it.
- **Why:** The old README had no setup instructions and the database part was missing.

### OpenAPI / Swagger spec
- **Tool:** Claude Opus 4.8
- **Task:** Update the API spec so it matches what we actually implemented.
- **Accepted / modified / rejected:** We gave it our controllers and DTOs; it updated the spec (base path, room schema, removed pagination, etc.). We accepted the result and marked the unimplemented endpoints as planned.
- **Why:** The original spec no longer matched the code (e.g. extras structure, image fields, /api path).
 
 ### Backend implementation guidance (U2 to U5)
- **Tool:** Claude Opus 4.7
- **Task:** Step-by-step guidance for the backend structure (clean architecture layers, entity mappers, ports and adapters, controllers, DTOs, exception handler).
- **Accepted / modified / rejected:** We wrote the code ourselves following the suggested structure. Names, comments and small details were adjusted to fit our package layout and style. Some suggestions were rejected when they conflicted with what teammates had already built (for example we kept our existing AvailabilityService instead of refactoring it).


### Audit fields on bookings (created_at, price_at_booking)
- **Tool:** Claude Opus 4.7
- **Task:** Add created_at (database default timestamp) and price_at_booking (calculated server side) to the bookings table and propagate them through entity, domain, service, DTO and mapper.
- **Accepted / modified / rejected:** Accepted the use of @CreationTimestamp on the JPA entity instead of a manual refresh, and the choice to calculate the price in the service rather than accept it from the client. We typed out the changes ourselves and verified them with curl.
- **Why:** We needed support with adding missing fields without removing/adding much logic.

## AI Workflow
For each task we worked roughly like this:
1. **Define the task** - we wrote down what we needed and the constraints.
2. **Ask the AI tool** - Copilot for inline code and unclear compile errors, Claude for planning, docs and test data.
3. **Read and understand the output** - we made sure we understood what it did before using it.
4. **Verify it** - we compiled and ran the code and tested the endpoints.
5. **Adapt and integrate** - we fit it into our own structure and style.
What AI helped with vs. what we did ourselves:
- **AI helped with:** boilerplate, explaining errors, seed/test data, first drafts of documentation.
- **We did and decided ourselves:** the architecture, the data model and we reviewed every generated piece before committing it.
