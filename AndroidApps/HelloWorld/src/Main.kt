class Recipe {
    private val ingredients = mutableListOf<String>()

    fun addIngredient(name: String) {
        ingredients.add(name)
    }

    fun getIngredients(): List<String> {
        return ingredients
    }
}

fun String.lastChar(): Char = this[this.length - 1]

fun greet(name: String, age: Int): String {
    return "Hello $name, I'm $age years old."
}

fun main() {
    println("ABCD".lastChar())

    val recipe = Recipe()
    recipe.addIngredient("Rice")
    recipe.addIngredient("Chicken")
    println(recipe.getIngredients())

    val list = (0..10).toList()
    val filtered = list.filter { it % 2 == 0 }
    println(filtered)   // [0, 2, 4, 6, 8, 10]

    var b: String? = "abc"
    println(b?.length)  // safe call

    println("Hello World!")
    println(3 + 5)

    val number: Int = 9
    val name: String = "Kotlin"

    val age: Int = 25
    if (age > 20) {
        println("You're an adult.")
    }

    println(greet("Teddy", age = 21))

    val sum = { x: Int, y: Int -> x + y }
    println(sum(2, 3))
}
