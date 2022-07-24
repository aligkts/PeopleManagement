# PeopleManagement

## Architecture

- Single Activity
- MVVM Pattern
- Manual Dependency Injection
- Manual Pagination

**View:** Renders UI and delegates user actions to ViewModel

**ViewModel:** Can have simple UI logic but most of the time just gets the data from UseCase

**UseCase:** Contains all business rules and they written in the manner of single responsibility principle

**Repository:** Single source of data. Responsible to get data from one or more data sources

<img src="https://github.com/aligkts/PeopleManagement/blob/master/images/architecture-diagram.png" width="500" />
