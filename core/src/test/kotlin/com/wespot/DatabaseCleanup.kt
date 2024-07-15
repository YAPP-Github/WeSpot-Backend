package com.wespot

import org.springframework.beans.factory.InitializingBean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet


@Component
class DatabaseCleanup(
    private val jdbcTemplate: JdbcTemplate,
    private val tableNames: MutableList<String> = mutableListOf()
) : InitializingBean {

    companion object {
        private val TRUNCATE_SQL_MESSAGE: String = "TRUNCATE TABLE %s"
        private val SET_REFERENTIAL_INTEGRITY_SQL_MESSAGE: String = "SET FOREIGN_KEY_CHECKS = %s"
        private val DISABLE_REFERENTIAL_QUERY: String =
            SET_REFERENTIAL_INTEGRITY_SQL_MESSAGE.format(false)
        private val ENABLE_REFERENTIAL_QUERY: String =
            SET_REFERENTIAL_INTEGRITY_SQL_MESSAGE.format(true)
    }

    @Override
    override fun afterPropertiesSet() {
        val resultSet: ResultSet = jdbcTemplate.dataSource!!
            .connection
            .metaData
            .getTables(null, "PUBLIC", null, arrayOf("TABLE"))

        while (resultSet.next()) {
            val tableName: String = resultSet.getString("TABLE_NAME")
            this.tableNames.add(tableName)
        }
    }

    @Transactional
    fun execute() {
        disableReferentialIntegrity()
        executeTruncate()
        enableReferentialIntegrity()
    }

    private fun disableReferentialIntegrity() {
        jdbcTemplate.execute(DISABLE_REFERENTIAL_QUERY)
    }

    private fun executeTruncate() {
        for (tableName in tableNames) {
            val TRUNCATE_QUERY: String = TRUNCATE_SQL_MESSAGE.format(tableName)
            jdbcTemplate.execute(TRUNCATE_QUERY)
        }
    }

    private fun enableReferentialIntegrity() {
        jdbcTemplate.execute(ENABLE_REFERENTIAL_QUERY)
    }

}
