package category

import androidx.annotation.VisibleForTesting
import com.example.parrottest.database.model.CategoryRoomModel

@VisibleForTesting
class CategoryBuilder private constructor() {

    private var uuid: String = ""
    private var name: String = ""
    private var username: String = ""
    private var sortPosition: Int = 0

    fun withUuid(value: String): CategoryBuilder {

        this.uuid = value
        return this
    }

    fun withName(value: String): CategoryBuilder {

        this.name = value
        return this
    }

    fun withUsername(value: String): CategoryBuilder {

        this.username = value
        return this
    }

    fun withSortPosition(value: Int): CategoryBuilder {

        this.sortPosition = value
        return this
    }

    fun build(): CategoryRoomModel {

        return CategoryRoomModel(
            uuid = this.uuid,
            name = this.name,
            username = this.username,
            sortPosition = this.sortPosition
        )
    }

    companion object {

        fun aCategory(): CategoryBuilder {

            return CategoryBuilder()
        }
    }
}