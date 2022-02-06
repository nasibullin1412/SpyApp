package com.homework.myapplication.spyservice

import android.accounts.AccountManager
import android.content.Context

class GetAccounts : (Context) -> (List<String>) {
    override fun invoke(context: Context): List<String> {
        return AccountManager.get(context).accounts.filter { it.name != null }.map { it.name }
    }
}
