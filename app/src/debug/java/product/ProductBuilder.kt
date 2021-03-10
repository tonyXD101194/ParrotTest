package product

import androidx.annotation.VisibleForTesting
import com.example.parrottest.database.model.ProductRoomModel

@VisibleForTesting
class ProductBuilder private constructor() {

    private var uuid: String = ""
    private var idCategory: String = ""
    private var name: String = ""
    private var description: String = ""
    private var imageUrl: String = ""
    private var price: String = ""
    private var isSoldAlone: Boolean = false
    private var availability: String = ""

    fun withUuid(value: String): ProductBuilder {

        this.uuid = value
        return this
    }

    fun withIdCategory(value: String): ProductBuilder {

        this.idCategory = value
        return this
    }

    fun withName(value: String): ProductBuilder {

        this.name = value
        return this
    }

    fun withDescription(value: String): ProductBuilder {

        this.description = value
        return this
    }

    fun withImageUrl(value: String): ProductBuilder {

        this.imageUrl = value
        return this
    }

    fun withPrice(value: String): ProductBuilder {

        this.price = value
        return this
    }

    fun withIsSoldAlone(value: Boolean): ProductBuilder {

        this.isSoldAlone = value
        return this
    }

    fun withAvailability(value: String): ProductBuilder {

        this.availability = value
        return this
    }

    fun build(): ProductRoomModel {

        return ProductRoomModel(
            uuid = this.uuid,
            idCategory = this.idCategory,
            name = this.name,
            description = this.description,
            imageUrl = this.imageUrl,
            price = this.price,
            isSoldAlone = this.isSoldAlone,
            availability = this.availability
        )
    }

    companion object {

        fun aProduct(): ProductBuilder {

            return ProductBuilder()
        }
    }
}