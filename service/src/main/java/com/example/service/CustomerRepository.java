package com.example.service;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
class CustomerRepository {
    private final JdbcClient jdbcClient;

    CustomerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    Collection<Customer> findAll() {
        return this.jdbcClient
                .sql("SELECT * FROM customer")
                .query((rs, row) -> new Customer(rs.getInt("id"), rs.getString("name")))
                .list();
    }
}
