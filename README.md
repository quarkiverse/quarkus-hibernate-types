# Quarkus Hibernate Types

[![Build](https://github.com/quarkiverse/quarkus-hibernate-types/workflows/Build/badge.svg?branch=main)](https://github.com/quarkiverse/quarkus-hibernate-types/actions?query=workflow%3ABuild)
[![License](https://img.shields.io/github/license/quarkiverse/quarkus-hibernate-types.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Central](https://img.shields.io/maven-central/v/io.quarkiverse.hibernatetypes/quarkus-hibernate-types-parent?color=green)](https://search.maven.org/search?q=g:io.quarkiverse.hibernatetypes%20AND%20a:quarkus-hibernate-types-parent)

> **Warning**
>
> Quarkus 3.0 integrated Hibernate 6.2, so this extension is no longer needed for JSON type support.
>
> We provide an updated version of this extension anyway, so it is more convenient to upgrade quarkus.
> It currently looks like that we will not implement any new features in this extension anymore, because hibernate 6.2 should bring you all of the features out of the box.

## Introduction

This module integrates the hibernate extension "hibernate-types" into the quarkus universe, 
so you are able to support extra types that are not supported by the Hibernate ORM core.

For detailed information, check out the readme of the underlying repository: https://github.com/vladmihalcea/hypersistence-utils#readme

Detailed examples (including native image generation) can be found under the "integration-tests/simple" module.

## Usage

To use this extension, add the following dependency to your project:

```xml
<dependency>
  <groupId>io.quarkiverse.hibernatetypes</groupId>
  <artifactId>quarkus-hibernate-types</artifactId>
</dependency>
```

Now you are able to define the newly added types in your already created entities, 
by including the "@TypeDef" annotation on your entity class and the "@Type" annotation on the desired json field.

```java
import io.quarkiverse.hibernate.types.json.JsonBinaryType;
import io.quarkiverse.hibernate.types.json.JsonTypes;

/**
 * Declares a simple entity with a nested serialized object.
 * This object will get inserted into a "jsonb" (postgresql) column.
 */
@Entity
@TypeDef(name = JsonTypes.JSON_BIN, typeClass = JsonBinaryType.class)
public class MyEntity {

    /**
     * Unique identifier / primary key for this entity.
     */
    @Id
    private String id;

    /**
     * Nested parameter, that will get inserted into a json column
     */
    @Type(type = JsonTypes.JSON_BIN)
    @Column(name = "PARAM", columnDefinition = JsonTypes.JSON_BIN)
    private MyJsonParam param;

    /**
     * Parameter POJO
     */
    public static class MyJsonParam {
        private String id;
        private String name;
    }
    
}
```

## Limitations

### Hibernate Reactive Support

Using hibernate reactive is currently not (fully) supported, because the "hibernate-types" project isn't 
meant to be used by Hibernate Reactive (see https://quarkusio.zulipchat.com/#narrow/stream/187030-users/topic/quarkus-hibernate-types ).

> The "types" notion needs to map to vendor specific types that some databases expose in their proprietary JDBC driver, but Hibernate Reactive doesn't use those drivers.

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-3-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://www.lorislab.org"><img src="https://avatars2.githubusercontent.com/u/828045?v=4?s=100" width="100px;" alt="Andrej Petras"/><br /><sub><b>Andrej Petras</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-hibernate-types/commits?author=andrejpetras" title="Code">💻</a> <a href="#maintenance-andrejpetras" title="Maintenance">🚧</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://conceptive.io"><img src="https://avatars.githubusercontent.com/u/14055002?v=4?s=100" width="100px;" alt="Werner Glanzer"/><br /><sub><b>Werner Glanzer</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-hibernate-types/commits?author=wglanzer" title="Code">💻</a> <a href="#maintenance-wglanzer" title="Maintenance">🚧</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/brunobastosg"><img src="https://avatars.githubusercontent.com/u/320122?v=4?s=100" width="100px;" alt="Bruno Guimarães"/><br /><sub><b>Bruno Guimarães</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-hibernate-types/commits?author=brunobastosg" title="Code">💻</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification.
Contributions of any kind welcome!
