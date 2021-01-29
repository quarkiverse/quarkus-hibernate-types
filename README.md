# Quarkus Hibernate types
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![Build](https://github.com/quarkiverse/quarkus-hibernate-types/workflows/Build/badge.svg?branch=master)](https://github.com/quarkiverse/quarkus-hibernate-types/actions?query=workflow%3ABuild)
[![License](https://img.shields.io/github/license/quarkiverse/quarkus-hibernate-types.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Central](https://img.shields.io/maven-central/v/io.quarkiverse.hibernate/quarkus-hibernate-types-parent?color=green)](https://search.maven.org/search?q=a:quarkus-hibernate-types-parent)
[![All Contributors](https://img.shields.io/badge/all_contributors-1-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

## Usage

To use the extension, add the dependency to the target project:

```xml
<dependency>
  <groupId>io.quarkiverse.hibernate</groupId>
  <artifactId>quarkus-hibernate-types</artifactId>
  <version>{latest-maven-release}</version>
</dependency>
```
## Simple usage
```java
import io.quarkiverse.hibernate.types.json.JsonBinaryType;
import io.quarkiverse.hibernate.types.json.JsonType;
import io.quarkiverse.hibernate.types.json.JsonTypes;

@Entity
@TypeDef(name = JsonTypes.JSON, typeClass = JsonType.class)
@TypeDef(name = JsonTypes.JSON_BIN, typeClass = JsonBinaryType.class)
public class MyEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Type(type = JsonTypes.JSON_BIN)
    @Column(name = "PARAM", columnDefinition = JsonTypes.JSON_BIN)
    private MyParam param;
}
```
```java
public class MyParam {

    private String id;

    private String name;
}
```

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://www.lorislab.org"><img src="https://avatars2.githubusercontent.com/u/828045?v=4?s=100" width="100px;" alt=""/><br /><sub><b>Andrej Petras</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkiverse-hibernate-types/commits?author=andrejpetras" title="Code">💻</a> <a href="#maintenance-andrejpetras" title="Maintenance">🚧</a></td>
  </tr>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!