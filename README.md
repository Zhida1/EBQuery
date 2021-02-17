# EBQuery

## Table of Contents

**[Introduction - Enterprise Backend as a Service](#introduction)**

**[Requirements](#requirements)**

**[Getting started](#getting-started)**

**[Using EBQuery](#usage)**

**[Features](#features)**

**[UI](#support)**

**[Support](#support)**

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

