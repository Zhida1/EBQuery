# EBQuery

## Table of Contents

**[Introduction - Enterprise Backend as a Service](#introduction)**

**[Requirements](#requirements)**

**[Getting started](#getting-started)**

**[Using EBQuery](#usage)**

**[Features](#features)**

## <a name="introduction"/>Introduction - Enterprise Backend as a Service

EBQuery allows you to easily access databases through a REST API.

All you have to do is enter your database connection settings and EBQuery will generate REST API endpoints for all
your database tables and views. Moreover, you can easily add custom SQL queries for access via REST API calls.

EBQuery provides SQL result sets as JSON, XML, HTML or CSV data.

EBQuery is an Enterprise Backend as a Service along lines of [Parse](https://parse.com/) but for your own databases.
It's like [Heroku Dataclips](https://devcenter.heroku.com/articles/dataclips) but for non-cloud, on-premises databases.

EBQuery is a JavaEE application that's securely hosted on your servers. No data ever leaves your company!

## <a name="requirements"/>Requirements

EBQuery has the following minimum requirements:

- Java 6
- a Servlet container or application server (stand-alone option available)
- a database server (embedded H2 database available)

## <a name="getting-started"/>Getting started

### Installation

EBQuery can be built and installed as a standard web application archive (WAR). Any JavaEE 7 or JavaEE 6 servlet container should work.

#### <a name="configuration"/>Configuration

In order to start and run properly two settings need to be configured for EBQuery:

- the database to store the application data in
- security preferences

#### Database

If you're using a dedicated database for EBQuery (as you probably should for production) you first need to run a DDL
script that creates the necessary tables. DDL scripts for the following databases are included in src/main/resources/sql
in the EBQuery directory:

- H2
- MySQL
- Oracle Database
- PostgreSQL

Then you need to set the system property DATABASE_URL according to your database connection settings.

How you set this system property depends on your environment. A common way for doing so is using the -D command line
option for Java, e.g.:

`java -DDATABASE_URL=postgres://username:password@localhost:5432/EBQuery ...`

The format for the URL is

`PROTOCOL://USERNAME:PASSWORD@HOST:PORT/DATABASE_NAME`

A PostgreSQL DATABASE_URL for example would look like this:

`DATABASE_URL=postgres://username:password@localhost:5432/EBQuery`

The following protocols / RDBMS are available. Please note that only H2, MySQL and PostgreSQL drivers are included with
EBQuery. If you want to use one of the other RDBMS you have to add the appropriate JDBC driver to the shared libraries
directory of your Servlet container / application server:

- as400 (IBM DB2 AS400)
- db2 (IBM DB2)
- derby (Apache Derby)
- edbc (Ingres Database)
- firebirdsql (Firebird)
- h2 (H2)
- hsqldb (HSQLDB)
- mysql (MySQL)
- oracle (Oracle Database)
- postgres (PostgreSQL)
- sapdb (SAP DB)
- sqlserver (Microsoft SQL Server)
- sybase (Sybase)

**Please note: Only H2, MySQL and PostgreSQL drivers are included with EBQuery. If you want to use one of the other
RDBMS you have to add the appropriate JDBC driver to the shared libraries directory of your Servlet container /
application server.**

#### Security

In order to set your basic security preferences for EBQuery the environment variable _spring.profiles.active_ has to be
set to either 'local' or 'public', i.e.:

`spring.profiles.active=local`

or

`spring.profiles.active=public`

'local' will disable HTTP basic authentication whereas 'public' will enable it. The default password is 'pP2XLieKb6'.
This password can (and should be) changed in spring-security-public.xml in src/main/webapp in the EBQuery directory.
Please restart the server after having changed the password. Please note: If you turn HTTP basic authentication on your
API calls will have to send an HTTP basic authentication header with the appropriate username / password, too.

How you set the _spring.profiles.active_ system property depends on your environment. A common way for doing so is using
the -D command line option for Java, e.g.:

`java -Dspring.profiles.active=local ...`

## <a name="usage"/>Using EBQuery

Using EBQuery is simple. EBQuery has 2 main views:

- [database connections](#database-connections)
- [queries](#queries)

### <a name="database-connections"/>Database Connections

Here you can add and edit and your database connections. A valid database connection consists of:

- name
- URL
- username
- password

The URL has to be a valid JDBC connection URL such as:

`jdbc:postgresql://localhost:5432/SomeDatabase`

The following JDBC connection protocols / RDBMS are supported:

- jdbc:as400:// (IBM DB2 AS400)
- jdbc:db2:// (IBM DB2)
- jdbc:derby: (Apache Derby)
- jdbc:ingres:// (Ingres Database)
- jdbc:firebirdsql:// (Firebird)
- jdbc:h2: (H2)
- jdbc:hsqldb:mem: (HSQLDB)
- jdbc:JTurbo:// (Microsoft SQL Server, JTurbo driver)
- jdbc:mysql:// (MySQL)
- jdbc:oracle:thin:@ (Oracle Database)
- jdbc:postgresql:// (PostgreSQL)
- jdbc:sapdb:// (SAP DB)
- jdbc:microsoft:sqlserver (Microsoft SQL Server)
- jdbc:sybase:Tds: (Sybase)

**Please note: Only H2, MySQL and PostgreSQL drivers are included with EBQuery. If you want to use one of the other
RDBMS you have to add the appropriate JDBC driver to the shared libraries directory of your Servlet container /
application server.**

After having entered a valid database connection EBQuery will automatically create _SELECT _ FROM\* queries for each
table and view in your database. If your database uses foreign keys EBQuery will extend those queries to include
[links to referenced database entity resources](#foreign-keys).

### <a name="queries"/>Queries

Clicking on the 'Queries' button from the top menu will show all queries. Clicking the respective button for each
database connection will show only the queries for that connection.

Clicking on 'Details' for a query will preview the result set for this query as well as reveal a few additional options:

- Execute
- Update
- New
- Previous versions

Alongside these options EBQuery displays [REST API](#rest-api) links for this query above and below the result set
preview.

#### <a name="interpolation"/>Arguments and variable interpolation

EBQuery allows you to use the _?_ operator for dynamically supplying one or multiple arguments to a query, e.g.:

```
SELECT * FROM table WHERE field = ?
SELECT * FROM table WHERE field = ? OR another_field = ?
```

These arguments can then be supplied as [additional URL parameters](#formats) to your API calls.

#### <a name="foreign-keys"/>Navigating entities referenced by foreign keys

If your database tables make use of foreign keys for referencing entities EBQuery will automatically link those to the
referencing entity and add a link to the API resource for the referenced entity.

### <a name="rest-api"/>REST API

EBQuery turns each SQL query into an easily accessible REST API endpoint that returns data in
[a variety of formats](#formats).

#### <a name="formats"/>Parameters and formats

The EBQuery REST API returns data in the following formats:

- JSON
- XML
- HTML
- CSV

These are a few example URLs:

- [/api/v1/resultSetForQuery/6.json](/api/v1/resultSetForQuery/6.json) (JSON)
- [/api/v1/resultSetForQuery/6.xml](/api/v1/resultSetForQuery/6.xml) (XML)
- [/api/v1/resultSetForQuery/6.csv](/api/v1/resultSetForQuery/6.csv) (CSV)
- [/api/v1/resultSetForQuery/vertical/false/6.html](/api/v1/resultSetForQuery/vertical/false/6.html) (HTML list)
- [/api/v1/resultSetForQuery/vertical/true/6.html](/api/v1/resultSetForQuery/vertical/true/6.html) (styled HTML list)
- [/api/v1/resultSetForQuery/horizontal/false/6.html](/api/v1/resultSetForQuery/horizontal/false/6.html) (HTML table)
- [/api/v1/resultSetForQuery/horizontal/true/6.html](/api/v1/resultSetForQuery/horizontal/true/6.html)
  (styled HTML table)

EBQuery also allows you to add arguments to an API call, which will be used for
[interpolating variables in the SQL query](#interpolation). The arguments are appended to the URL after the query ID.
Multiple arguments are comma-separated. Moreover, you can also limit the size of the result set by adding a _size_
parameter.

Again, these are a few example URLs:

- [/api/v1/resultSetForQuery/6/45,SomeValue.json](/api/v1/resultSetForQuery/6/45,SomeValue.json)
  (JSON with interpolated variables)
- [/api/v1/resultSetForQuery/6/size/3.json](/api/v1/resultSetForQuery/6/size/3.json)
  (JSON with number of results limited to 3)
- [/api/v1/resultSetForQuery/6/45,SomeValue/size/3.json](/api/v1/resultSetForQuery/6/45,SomeValue/size/3.json)
  (JSON with interpolated variables and number of results limited
  to 3)

## <a name="features"/>Features

- Generate REST APIs from SQL queries.
- Access your data in JSON, XML or CSV formats.
- Supports all major RDBMS (including IBM DB2, Microsoft SQL Server, MySQL, Oracle Database and PostgreSQL).
- Entirely hosted on-premises. Your data stays with you all the time!
- Conveniently edit your SQL queries and preview your data.
- Version control for SQL queries.
- Snapshots (i.e. materialized views if supported by RDBMS).
- Transitive navigation (i.e. navigating entities referenced by foreign keys).
- Variable interpolation.
- Limit and filter query results.
