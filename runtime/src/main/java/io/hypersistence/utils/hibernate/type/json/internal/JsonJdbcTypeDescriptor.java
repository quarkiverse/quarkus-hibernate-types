package io.hypersistence.utils.hibernate.type.json.internal;

import java.sql.*;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.*;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DatabaseMetaDataDialectResolutionInfoAdapter;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import org.hibernate.type.descriptor.jdbc.BasicExtractor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.ParameterizedType;

import io.hypersistence.utils.common.StringUtils;
import io.hypersistence.utils.hibernate.type.util.ParameterTypeUtils;

/**
 * @author Vlad Mihalcea
 */
public class JsonJdbcTypeDescriptor extends AbstractJsonJdbcTypeDescriptor implements ParameterizedType {

    private volatile Dialect dialect;
    private volatile AbstractJsonJdbcTypeDescriptor jdbcTypeDescriptor;

    private volatile Properties properties;

    public JsonJdbcTypeDescriptor() {
    }

    public JsonJdbcTypeDescriptor(Properties properties) {
        this.properties = properties;
    }

    @Override
    public <X> ValueBinder<X> getBinder(final JavaType<X> javaType) {
        return new BasicBinder<X>(javaType, this) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                sqlTypeDescriptor(st.getConnection(), options).getBinder(javaType).bind(
                        st, value, index, options);
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
                    throws SQLException {
                sqlTypeDescriptor(st.getConnection(), options).getBinder(javaType).bind(
                        st, value, name, options);
            }
        };
    }

    @Override
    protected Object extractJson(ResultSet rs, int paramIndex) throws SQLException {
        return sqlTypeDescriptor(rs.getStatement().getConnection(), null).extractJson(rs, paramIndex);
    }

    @Override
    protected Object extractJson(CallableStatement statement, int index) throws SQLException {
        return sqlTypeDescriptor(statement.getConnection(), null).extractJson(statement, index);
    }

    @Override
    protected Object extractJson(CallableStatement statement, String name) throws SQLException {
        return sqlTypeDescriptor(statement.getConnection(), null).extractJson(statement, name);
    }

    private AbstractJsonJdbcTypeDescriptor sqlTypeDescriptor(Connection connection, WrapperOptions options) {
        if (jdbcTypeDescriptor == null) {
            jdbcTypeDescriptor = resolveJdbcTypeDescriptor(connection, options);
        }
        return jdbcTypeDescriptor;
    }

    private AbstractJsonJdbcTypeDescriptor resolveJdbcTypeDescriptor(Connection connection, WrapperOptions options) {
        try {
            DatabaseMetaDataDialectResolutionInfoAdapter metaDataInfo = new DatabaseMetaDataDialectResolutionInfoAdapter(
                    connection.getMetaData());
            Dialect dialect = _resolveDialect(metaDataInfo, options);
            if (dialect instanceof PostgreSQLDialect) {
                return JsonBinaryJdbcTypeDescriptor.INSTANCE;
            } else if (dialect instanceof H2Dialect) {
                return JsonBytesJdbcTypeDescriptor.INSTANCE;
            } else if (dialect instanceof OracleDialect) {
                if (properties != null) {
                    DynamicParameterizedType.ParameterType parameterType = ParameterTypeUtils.resolve(properties);
                    if (parameterType != null) {
                        String columnType = ParameterTypeUtils.getColumnType(parameterType);
                        if (!StringUtils.isBlank(columnType)) {
                            switch (columnType) {
                                case "json":
                                    return JsonBytesJdbcTypeDescriptor.of(Database.ORACLE);
                                case "blob":
                                case "clob":
                                    return JsonBlobJdbcTypeDescriptor.INSTANCE;
                                case "varchar2":
                                case "nvarchar2":
                                    return JsonStringJdbcTypeDescriptor.INSTANCE;
                            }
                        }
                    }
                }
                if (metaDataInfo.getDatabaseMajorVersion() >= 21) {
                    return JsonBytesJdbcTypeDescriptor.of(Database.ORACLE);
                }
            }
            return JsonStringJdbcTypeDescriptor.INSTANCE;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
        return new BasicExtractor<X>(javaType, this) {
            @Override
            protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
                // overriden, because we need to access the options
                Object json = sqlTypeDescriptor(rs.getStatement().getConnection(), options).extractJson(rs, paramIndex);
                return javaType.wrap(json, options);
            }

            @Override
            protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
                // overriden, because we need to access the options
                Object json = sqlTypeDescriptor(statement.getConnection(), options).extractJson(statement, index);
                return javaType.wrap(json, options);
            }

            @Override
            protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
                // overriden, because we need to access the options
                Object json = sqlTypeDescriptor(statement.getConnection(), options).extractJson(statement, name);
                return javaType.wrap(json, options);
            }
        };
    }

    private Dialect _resolveDialect(DatabaseMetaDataDialectResolutionInfoAdapter metaDataInfo, WrapperOptions options)
            throws SQLException {
        // get dialect from session, so we are compatible with native images...
        if (options instanceof Session) {
            SessionFactory factory = ((Session) options).getSessionFactory();
            if (factory instanceof SessionFactoryImplementor)
                return ((SessionFactoryImplementor) factory).getJdbcServices().getDialect();
        }

        // this part is not compatible with native images... but what should we do?
        return new StandardDialectResolver().resolveDialect(metaDataInfo);
    }

    @Override
    public int getJdbcTypeCode() {
        return jdbcTypeDescriptor != null ? jdbcTypeDescriptor.getJdbcTypeCode() : super.getJdbcTypeCode();
    }

    @Override
    public void setParameterValues(Properties parameters) {
        if (properties == null) {
            properties = parameters;
        } else {
            properties.putAll(parameters);
        }
    }
}
