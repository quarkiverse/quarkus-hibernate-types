package com.vladmihalcea.hibernate.type.json.internal;

import java.sql.*;
import java.util.Properties;

import org.hibernate.*;
import org.hibernate.dialect.*;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DatabaseMetaDataDialectResolutionInfoAdapter;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.descriptor.*;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.*;
import org.hibernate.usertype.*;

import com.vladmihalcea.hibernate.type.util.ParameterTypeUtils;
import com.vladmihalcea.hibernate.util.StringUtils;

/**
 * @author Vlad Mihalcea
 */
public class JsonSqlTypeDescriptor extends AbstractJsonSqlTypeDescriptor implements ParameterizedType {

    private volatile AbstractJsonSqlTypeDescriptor sqlTypeDescriptor;

    private volatile Properties properties;

    public JsonSqlTypeDescriptor() {
    }

    public JsonSqlTypeDescriptor(Properties properties) {
        this.properties = properties;
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>(javaTypeDescriptor, this) {
            @Override
            protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor
                        .wrap(sqlTypeDescriptor(rs.getStatement().getConnection(), options).extractJson(rs, name), options);
            }

            @Override
            protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor
                        .wrap(sqlTypeDescriptor(statement.getConnection(), options).extractJson(statement, index), options);
            }

            @Override
            protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor
                        .wrap(sqlTypeDescriptor(statement.getConnection(), options).extractJson(statement, name), options);
            }
        };
    }

    @Override
    public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<>(javaTypeDescriptor, this) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                sqlTypeDescriptor(st.getConnection(), options).getBinder(javaTypeDescriptor).bind(st, value, index, options);
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
                    throws SQLException {
                sqlTypeDescriptor(st.getConnection(), options).getBinder(javaTypeDescriptor).bind(st, value, name, options);
            }
        };
    }

    private AbstractJsonSqlTypeDescriptor sqlTypeDescriptor(Connection connection, WrapperOptions options) {
        if (sqlTypeDescriptor == null) {
            sqlTypeDescriptor = resolveSqlTypeDescriptor(connection, options);
        }
        return sqlTypeDescriptor;
    }

    private AbstractJsonSqlTypeDescriptor resolveSqlTypeDescriptor(Connection connection, WrapperOptions options) {
        try {
            DatabaseMetaDataDialectResolutionInfoAdapter metaDataInfo = new DatabaseMetaDataDialectResolutionInfoAdapter(
                    connection.getMetaData());
            Dialect dialect = _resolveDialect(metaDataInfo, options);
            if (dialect instanceof PostgreSQL81Dialect) {
                return JsonBinarySqlTypeDescriptor.INSTANCE;
            } else if (dialect instanceof H2Dialect) {
                return JsonBytesSqlTypeDescriptor.INSTANCE;
            } else if (dialect instanceof Oracle8iDialect) {
                if (properties != null) {
                    DynamicParameterizedType.ParameterType parameterType = ParameterTypeUtils.resolve(properties);
                    if (parameterType != null) {
                        String columnType = ParameterTypeUtils.getColumnType(parameterType);
                        if (!StringUtils.isBlank(columnType)) {
                            switch (columnType) {
                                case "json":
                                    return JsonBytesSqlTypeDescriptor.of(Database.ORACLE);
                                case "blob":
                                case "clob":
                                    return JsonBlobSqlTypeDescriptor.INSTANCE;
                                case "varchar2":
                                case "nvarchar2":
                                    return JsonStringSqlTypeDescriptor.INSTANCE;
                            }
                        }
                    }
                }
                if (metaDataInfo.getDatabaseMajorVersion() >= 21) {
                    return JsonBytesSqlTypeDescriptor.of(Database.ORACLE);
                }
            }
            return JsonStringSqlTypeDescriptor.INSTANCE;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
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
    public int getSqlType() {
        return sqlTypeDescriptor != null ? sqlTypeDescriptor.getSqlType() : super.getSqlType();
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
