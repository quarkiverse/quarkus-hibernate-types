package io.quarkiverse.hibernate.types.json;

import java.sql.Blob;

/**
 * JSON hibernate types.
 */
public final class JsonTypes {

    /**
     * Maps any given Java object on a JSON column type that is managed via
     * {@link java.sql.PreparedStatement#setString(int, String)} at JDBC Driver level.
     * <p>
     * If you are using <strong>Oracle</strong>, you should use this {@link JsonType} to map a
     * <strong>{@code VARCHAR2}</strong> column type storing JSON.
     * <p>
     * If you are using <strong>SQL Server</strong>, you should use this {@link JsonType} to map an
     * <strong>{@code NVARCHAR}</strong> column type storing JSON.
     * <p>
     * If you are using <strong>MySQL</strong>, you should use this {@link JsonType} to map the
     * <strong>{@code json}</strong> column type.
     * <p>
     * If you are using <strong>PostgreSQL</strong>, then you should <strong>NOT</strong> use this {@link JsonType}. You
     * should use {@link JsonBinaryType} instead.
     *
     * @see JsonType
     */
    public static final String JSON = "json";

    /**
     * Maps any given Java object on a JSON column type that is managed via
     * {@link java.sql.PreparedStatement#setObject(int, Object)} at JDBC Driver level.
     * <p>
     * If you are using <strong>PostgreSQL</strong>, you should use this {@link JsonBinaryType} to map both
     * <strong>{@code jsonb}</strong> and <strong>{@code json}</strong> column types.
     *
     * @see JsonBinaryType
     */
    public static final String JSON_BIN = "jsonb";

    /**
     * Maps any given Java object on a JSON column type that is managed via
     * {@link java.sql.PreparedStatement#setBlob(int, Blob)} at JDBC Driver level.
     * <p>
     * If you are using Oracle, you should use this {@link JsonBlobType} to map a {@code BLOB} column type storing JSON.
     *
     * @see JsonBlobType
     */
    public static final String JSON_BLOB = "jsonb-lob";

    /**
     * Maps a JSON object on a JSON column type that is managed via {@link java.sql.PreparedStatement#setString(int, String)}
     * at JDBC Driver level. For instance, if you are using MySQL, you should be using string version to map the
     * {@code json} column type to a Jackson JsonNode or Json-B JsonStructure object.
     */
    public static final String JSON_OBJECT_STRING = "jsonb-node";

    /**
     * Maps a Jackson JsonNode object on a JSON column type that is managed via
     * {@link java.sql.PreparedStatement#setObject(int, Object)} at JDBC Driver level. For instance, if you are using
     * PostgreSQL, you should be using binary version to map both {@code jsonb} and {@code json} column types to a
     * Jackson JsonNode or Json-B JsonStructure object.
     */
    public static final String JSON_OBJECT_BIN = "jsonb-node";

    /**
     * Default constructor.
     */
    private JsonTypes() {
    }
}
