package com.example.store.projektstore;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseQueryExecutor {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseQueryExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void executeSampleQuery() {
        jdbcTemplate.query(
                "SELECT GETDATE()",
                (rs, rowNum) -> {
                    System.out.println("Current database time: " + rs.getString(1));
                    return rs.getString(1);
                }
        );
    }
}
