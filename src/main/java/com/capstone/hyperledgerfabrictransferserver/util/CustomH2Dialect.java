package com.capstone.hyperledgerfabrictransferserver.util;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomH2Dialect extends H2Dialect {
    public CustomH2Dialect() {
        registerFunction("date", new SQLFunctionTemplate(StandardBasicTypes.DATE, "FORMATDATETIME(?1, 'yyyy-MM-dd')"));
    }
}
