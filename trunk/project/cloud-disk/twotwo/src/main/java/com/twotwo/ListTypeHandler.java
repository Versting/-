package com.twotwo;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.List;

@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ListTypeHandler implements TypeHandler<List<String>> {
    private final String SPLIT = ",";

    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null || parameter.isEmpty()) {
            ps.setNull(i, Types.VARCHAR);
            return;
        }
        String str = StrUtil.join(SPLIT, parameter);
        ps.setString(i, str);
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        if (StrUtil.isEmpty(str)) {
            return null;
        }
        return StrUtil.split(str, SPLIT);
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        if (StrUtil.isEmpty(str)) {
            return null;
        }
        return StrUtil.split(str, SPLIT);
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        if (StrUtil.isEmpty(str)) {
            return null;
        }
        return StrUtil.split(str, SPLIT);
    }

}