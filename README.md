# Quarkus Hibernate types

https://github.com/quarkiverse/quarkus-hibernate-types/issues/102 - looking for new maintainers

<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

## Usage

To use the extension, add the dependency to the target project:

```xml
<dependency>
  <groupId>io.quarkiverse.hibernatetypes</groupId>
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
  <tbody>
    <tr>
      <td align="center"><a href="https://www.lorislab.org"><img src="https://avatars2.githubusercontent.com/u/828045?v=4?s=100" width="100px;" alt="Andrej Petras"/><br /><sub><b>Andrej Petras</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-hibernate-types/commits?author=andrejpetras" title="Code">💻</a> <a href="#maintenance-andrejpetras" title="Maintenance">🚧</a></td>
      <td align="center"><a href="https://conceptive.io"><img src="https://avatars.githubusercontent.com/u/14055002?v=4?s=100" width="100px;" alt="Werner Glanzer"/><br /><sub><b>Werner Glanzer</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-hibernate-types/commits?author=wglanzer" title="Code">💻</a> <a href="#maintenance-wglanzer" title="Maintenance">🚧</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification.
Contributions of any kind welcome!
