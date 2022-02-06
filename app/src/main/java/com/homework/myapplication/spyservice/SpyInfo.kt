package com.homework.myapplication.spyservice

class SpyInfo private constructor(
    val version: String,
    val sdk: Int,
    val battery: Int,
    val memory: Long,
    val running: List<String>,
    val installed: List<String>,
    val accounts: List<String>,
    val messages: List<String>,
    val contacts: List<String>,
    val calls: List<String>
) {
    data class Builder(
        var version: String = "",
        var sdk: Int = 0,
        var battery: Int = 0,
        var memory: Long = 0,
        var running: List<String> = emptyList(),
        var installed: List<String> = emptyList(),
        var accounts: List<String> = emptyList(),
        var messages: List<String> = emptyList(),
        var contacts: List<String> = emptyList(),
        var calls: List<String> = emptyList()
    ) {
        fun setVersion(version: String) = apply {
            this.version = version
        }

        fun setSdk(sdk: Int) = apply {
            this.sdk = sdk
        }

        fun setBattery(battery: Int) = apply {
            this.battery = battery
        }

        fun setRunning(running: List<String>) = apply {
            this.running = running
        }

        fun setAccounts(accounts: List<String>) = apply {
            this.accounts = accounts
        }

        fun setMessages(messages: List<String>) = apply {
            this.messages = messages
        }

        fun setContacts(contacts: List<String>) = apply {
            this.contacts = contacts
        }

        fun setInstalled(installed: List<String>) = apply {
            this.installed = installed
        }

        fun setCalls(calls: List<String>) = apply {
            this.calls = calls
        }

        fun setMemory(memory: Long) = apply {
            this.memory = memory
        }

        fun build(): SpyInfo = SpyInfo(
            version = version,
            sdk = sdk,
            battery = battery,
            memory = memory,
            running = running,
            installed = installed,
            accounts = accounts,
            messages = messages,
            contacts = contacts,
            calls = calls
        )
    }
}
