package com.wespot.lock

import com.wespot.exception.GetLockFailException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ExecutorWithLockAdapter(
    private val jdbcTemplate: JdbcTemplate
) : ExecutorWithLock {

    @Transactional(timeout = 10)
    @Retryable(
        retryFor = [GetLockFailException::class],
        backoff = Backoff(delay = 100, multiplier = 2.0)
    )
    override fun execute(task: Runnable, lockKey: LockKey) {
        tryWithMysqlLock({
            task.run()
            null
        }, lockKey.key)
    }

    private fun <RETURN_TYPE> tryWithMysqlLock(
        task: () -> RETURN_TYPE,
        key: String
    ): RETURN_TYPE {
        val gotLock: Int = jdbcTemplate.queryForObject(
            "SELECT GET_LOCK(?, 0)",
            Int::class.java,
            key
        )

        if (gotLock == 1) {
            try {
                return task()
            } finally {
                jdbcTemplate.queryForObject("SELECT RELEASE_LOCK(?)", Int::class.java, key)
            }
        }

        throw GetLockFailException("Could not acquire lock for key: $key")
    }

    @Transactional(timeout = 10)
    @Retryable(
        retryFor = [GetLockFailException::class],
        backoff = Backoff(delay = 100, multiplier = 2.0)
    )
    override fun <RETURN> execute(task: () -> RETURN, lockKey: LockKey): RETURN {
        return tryWithMysqlLock(task, lockKey.key)
    }
}
