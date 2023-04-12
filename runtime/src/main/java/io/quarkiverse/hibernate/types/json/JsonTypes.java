package io.quarkiverse.hibernate.types.json;

import java.sql.Blob;

import jakarta.persistence.Column;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * JSON hibernate types.
 */
public final class JsonTypes {

    /**
     * {@link JsonType} allows you to map any given JSON object (e.g., POJO, <code>Map&lt;String, Object&gt;</code>,
     * List&lt;T&gt;,
     * <code>JsonNode</code>) on any of the following database systems:
     * </p>
     * <ul>
     * <li><strong>PostgreSQL</strong> - for both <strong><code>jsonb</code></strong> and <strong><code>json</code></strong>
     * column
     * types</li>
     * <li><strong>MySQL</strong> - for the <strong><code>json</code></strong> column type</li>
     * <li><strong>SQL Server</strong> - for the <strong><code>NVARCHAR</code></strong> column type storing JSON</li>
     * <li><strong>Oracle</strong> - for the <strong><code>JSON</code></strong> column type if you're using Oracle 21c or the
     * <strong><code>VARCHAR</code></strong> column type storing JSON if you're using an older Oracle version</li>
     * <li><strong>H2</strong> - for the <strong><code>json</code></strong> column type</li>
     * </ul>
     * <p>
     * If you switch to Oracle 21c from an older version, then you should also migrate your {@code JSON} columns to the native
     * JSON
     * type since this binary type performs better than
     * {@code VARCHAR2} or {@code BLOB} column types.
     * </p>
     * <p>
     * However, if you don't want to migrate to the new {@code JSON} data type,
     * then you just have to provide the column type via the JPA {@link Column#columnDefinition()} attribute,
     * like in the following example:
     * </p>
     *
     * <pre>
     * {@code @Type(}type = "io.quarkiverse.hibernate.types.json.JsonType")
     * {@code @Column(}columnDefinition = "VARCHAR2")
     * </pre>
     * <p>
     * For more details about how to use the {@link JsonType}, check out
     * <a href="https://vladmihalcea.com/how-to-map-json-objects-using-generic-hibernate-types/">this article</a> on
     * <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
     * </p>
     * <p>
     * If you are using <strong>Oracle</strong> and want to store JSON objects in a <code>BLOB</code> column type, then you can
     * use
     * the {@link JsonBlobType} instead. For more details, check out
     * <a href="https://vladmihalcea.com/oracle-json-jpa-hibernate/">this article</a> on
     * <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
     * </p>
     * <p>
     * Or, you can use the {@link JsonType}, but you'll have to specify the underlying column type
     * using the JPA {@link Column#columnDefinition()} attribute, like this:
     * </p>
     *
     * <pre>
     * {@code @Type(}type = "io.quarkiverse.hibernate.types.json.JsonType")
     * {@code @Column(}columnDefinition = "BLOB")
     *
     * @see JsonType
     */
    public static final String JSON = "json";

    /**
     * Maps any given Java object on a JSON column type that is managed via
     * {@link java.sql.PreparedStatement#setObject(int, Object)} at JDBC Driver level.
     * </p>
     * <p>
     * If you are using <strong>PostgreSQL</strong>, you can use this {@link JsonBinaryType} to map both <code>jsonb</code> and
     * <code>json</code> column types.
     * </p>
     * <p>
     * For more details about how to use it, check out
     * <a href="https://vladmihalcea.com/how-to-map-json-objects-using-generic-hibernate-types/">this article</a> on
     * <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
     * </p>
     * <p>
     * If you want to use a more portable Hibernate <code>Type</code> that can work on <strong>Oracle</strong>, <strong>SQL
     * Server</strong>, <strong>PostgreSQL</strong>, <strong>MySQL</strong>, or <strong>H2</strong> without any configuration
     * changes, then you should use the {@link JsonType} instead.
     *
     * @see JsonBinaryType
     */
    public static final String JSON_BIN = "jsonb";

    /**
     * Maps any given Java object on a JSON column type that is managed via
     * {@link java.sql.PreparedStatement#setBlob(int, Blob)} at
     * JDBC Driver level.
     * </p>
     * <p>
     * If you are using <strong>Oracle</strong>, you can use this {@link JsonBlobType} to map a {@code BLOB} column type storing
     * JSON.
     * </p>
     * <p>
     * For more details about how to use it, check out <a href="https://vladmihalcea.com/oracle-json-jpa-hibernate/">this
     * article</a> on <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
     * </p>
     * <p>
     * If you want to use a more portable Hibernate <code>Type</code> that can work on <strong>Oracle</strong>, <strong>SQL
     * Server</strong>, <strong>PostgreSQL</strong>, <strong>MySQL</strong>, or <strong>H2</strong> without any configuration
     * changes, then you should use the {@link JsonType} instead.
     *
     * @see JsonBlobType
     */
    public static final String JSON_BLOB = "jsonb-lob";

    /**
     * Maps a Jackson {@link JsonNode} object on a JSON column type that is managed via
     * {@link java.sql.PreparedStatement#setString(int, String)} at JDBC Driver level.
     * </p>
     * <p>
     * For instance, if you are using <strong>MySQL</strong>, you can use the {@link JsonNodeStringType} to map the {@code json}
     * column type to a Jackson {@link JsonNode} object.
     * </p>
     * <p>
     * For more details about how to use it, check out
     * <a href=
     * "https://vladmihalcea.com/how-to-store-schema-less-eav-entity-attribute-value-data-using-json-and-hibernate/">this
     * article</a> on <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
     * </p>
     * <p>
     * If you want to use a more portable Hibernate <code>Type</code> that can work on <strong>Oracle</strong>, <strong>SQL
     * Server</strong>, <strong>PostgreSQL</strong>, <strong>MySQL</strong>, or <strong>H2</strong> without any configuration
     * changes, then you should use the {@link JsonType} instead.
     */
    public static final String JSON_OBJECT_STRING = "jsonb-node";

    /**
     * Maps a Jackson {@link JsonNode} object on a JSON column type that is managed via
     * {@link java.sql.PreparedStatement#setObject(int, Object)} at JDBC Driver level.
     * </p>
     * <p>
     * For instance, if you are using <strong>PostgreSQL</strong>, you can use the {@link JsonNodeBinaryType} to map both
     * {@code jsonb} and {@code json} column types to a Jackson {@link JsonNode} object.
     * </p>
     * <p>
     * For more details about how to use it, check out
     * <a href=
     * "https://vladmihalcea.com/how-to-store-schema-less-eav-entity-attribute-value-data-using-json-and-hibernate/">this
     * article</a> on <a href="https://vladmihalcea.com/">vladmihalcea.com</a>.
     * </p>
     * <p>
     * If you want to use a more portable Hibernate <code>Type</code> that can work on <strong>Oracle</strong>, <strong>SQL
     * Server</strong>, <strong>PostgreSQL</strong>, <strong>MySQL</strong>, or <strong>H2</strong> without any configuration
     * changes, then you should use the {@link JsonType} instead.
     */
    public static final String JSON_OBJECT_BIN = "jsonb-node";

    /**
     * Default constructor.
     */
    private JsonTypes() {
    }
}
