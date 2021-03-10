package store

import androidx.annotation.VisibleForTesting
import com.example.parrottest.database.model.StoreRoomModel

@VisibleForTesting
class StoreBuilder private constructor() {

    private var uuid: String = ""
    private var name: String = ""
    private var availabilityState: String = ""
    private var email: String = ""
    private var username: String = ""

    fun withUuid(value: String): StoreBuilder {

        this.uuid = value
        return this
    }

    fun withName(value: String): StoreBuilder {

        this.name = value
        return this
    }

    fun withAvailabilityState(value: String): StoreBuilder {

        this.availabilityState = value
        return this
    }

    fun withEmail(value: String): StoreBuilder {

        this.email = value
        return this
    }

    fun withUsername(value: String): StoreBuilder {

        this.username = value
        return this
    }

    fun build(): StoreRoomModel {

        return StoreRoomModel(
            uuid = this.uuid,
            name = this.name,
            availabilityState = this.availabilityState,
            email = this.email,
            username = this.username
        )
    }

    companion object {

        fun aStore(): StoreBuilder {

            return StoreBuilder()
        }
    }
}