package com.example.parrottest.database.room

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.parrottest.database.DatabaseRoom
import com.example.parrottest.database.ParrotDatabaseTestRule
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ParrotDatabaseComponentTest {

    @get:Rule
    val rule = ParrotDatabaseTestRule()

    @Test
    fun getInstanceTest() {

        val instance: DatabaseRoom? =
            DatabaseRoom.getInstance(
                rule.context
            )

        val newInstance: DatabaseRoom? =
            DatabaseRoom.getInstance(
                rule.context
            )

        assertThat(instance).isNotNull()
        assertThat(newInstance).isNotNull()
        assertThat(instance).isEqualTo(newInstance)
    }
}