package com.wespot.lock

interface ExecutorWithLock {

    fun execute(task: Runnable, lockKey: LockKey)

    fun <RETURN> execute(task: () -> RETURN, lockKey: LockKey): RETURN

}
