CraftBot ![Java CI with Maven](https://github.com/hfoxy/CraftBot/workflows/Java%20CI%20with%20Maven/badge.svg)
========

Minecraft bot for 1.15.2

## Todos
- [ ] API for Pathing that makes more sense
- [ ] Allow multiple clients
- [ ] Authentication
- [ ] Improved navigation around the API
   - [ ] Remove public constants such as `PathCommands.WORLD_HANDLER`
   - [ ] Ability to get to Bot via `Bot#getBot()`, `Bot#getBots()`, `Bot#findByUuid(UUID)`
- [ ] Packet recording functionality
- [ ] Check path while walking on it, to ensure missing/blocked blocks can be detected/avoided