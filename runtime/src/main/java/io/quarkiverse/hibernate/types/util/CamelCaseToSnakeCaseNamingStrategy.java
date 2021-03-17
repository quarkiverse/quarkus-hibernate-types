package io.quarkiverse.hibernate.types.util;

import java.util.regex.Pattern;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Maps the JPA camelCase properties to snake_case database identifiers.
 * <p>
 * For more details about how to use it, check out
 * <a href="https://vladmihalcea.com/map-camel-case-properties-snake-case-column-names-hibernate/">this article</a> on
 * <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
 *
 * @author Vlad Mihalcea
 */
public class CamelCaseToSnakeCaseNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    public static final CamelCaseToSnakeCaseNamingStrategy INSTANCE = new CamelCaseToSnakeCaseNamingStrategy();

    public static final Pattern CAMEL_CASE_REGEX = Pattern.compile("([a-z]+)([A-Z]+)");

    public static final String SNAKE_CASE_PATTERN = "$1\\_$2";

    public CamelCaseToSnakeCaseNamingStrategy() {
    }

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return formatIdentifier(super.toPhysicalCatalogName(name, context));
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return formatIdentifier(super.toPhysicalSchemaName(name, context));
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return formatIdentifier(super.toPhysicalTableName(name, context));
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return formatIdentifier(super.toPhysicalSequenceName(name, context));
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return formatIdentifier(super.toPhysicalColumnName(name, context));
    }

    private Identifier formatIdentifier(Identifier identifier) {
        if (identifier != null) {
            String name = identifier.getText();
            String formattedName = CAMEL_CASE_REGEX.matcher(name).replaceAll(SNAKE_CASE_PATTERN).toLowerCase();
            return !formattedName.equals(name) ? Identifier.toIdentifier(formattedName, identifier.isQuoted()) : identifier;
        } else {
            return null;
        }
    }
}
