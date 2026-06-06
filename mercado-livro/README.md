# Mercado Livro 

Mercado Livro is a book marketplace application built with Java and Spring Boot. 
It allows users to buy and sell books, manage their inventory, and track orders.

> This is a sample project made at `Kotlin e Spring do ZERO ao Avançado Course` from Udemy

## Testing endpoints

### Customers

Get all customers, filter by name, or get a specific customer by ID:

```bash
curl -X GET "http://localhost:8080/customers"
curl -X GET "http://localhost:8080/customers?name=John"
curl -X GET "http://localhost:8080/customers/1"
```

Create a new customer:

```bash
curl -X POST "http://localhost:8080/customers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Silva",
    "email": "john@example.com"
  }'
```

Update an existing customer:

```bash
curl -X PUT "http://localhost:8080/customers/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Silva Updated",
    "email": "john.new@example.com"
  }'
```

Delete a customer:

```bash
curl -X DELETE "http://localhost:8080/customers/1"
```

# Notes 

Notes about the learning process and project structure.

## Java version

This project uses SDKMAN and defines the required Java version in `.sdkmanrc`.  
Currently, it is set to Java 17.

After entering the project directory, set the Java version by running:

```bash
sdk env

java -version
```

## Dependencies

The project dependencies are managed with Gradle and defined at `build.gradle.kts`.

To build de application:

```bash
./gradlew build
```

To check de dependencies installed:

```bash
./gradlew dependencies --configuration compileClasspath
```

## Useful concepts of Kotlin

[Online Kotlin compiler](https://play.kotlinlang.org/)

<details>
<summary>Classes</summary>

```kotlin
// Person with name and age attributes
// var is used to declare mutable properties and val is used to declare read-only properties
class Person(var name: String, var age: Int, var friend: Friend) {
    // Overriding toString function
    override fun toString(): String {
        return "Name: ${name} | Age: ${age} | Friend: ${friend.name}"
    }
}

// Class to represent composition relationship with Person class
class Friend(var name: String) {}

fun main() {
    // Instance of Person class
    var someone = Person("John Doe", 23, Friend("Jane Doe"))
    // Name: John Doe | Age: 23
    println(someone)

    someone.name = "Michael Jackson"
    println(someone)

    someone.friend.name = "Quincy Jones"
    println(someone)
}
```

</details>

<details>
<summary>Functions</summary>

```kotlin
// Functions can return "Unit" (similar to void in Java) or a specific type
fun printMessage(message: String): Unit {
    println(message)
}

// Function with a return type
fun sum(a: Int, b: Int): Int {
    return a + b
}

// Parameters can have default values
fun greet(name: String = "Guest") {
    println("Hello, $name!")
}

// Parameters can be passed by name
fun displayInfo(name: String, age: Int) {
    println("Name: $name | Age: $age")
}

// Usage examples
fun main() {
    printMessage("Welcome to Kotlin!")
    
    val result = sum(5, 10)
    println("Sum: $result")
    
    greet()
    greet("Alice")
    
    displayInfo(age = 30, name = "Bob")
}
```

</details>

<details>
<summary>Variables and types</summary>

Only `var` and `val` are used to declare variables in Kotlin.

```kotlin
// Mutable variable (can be reassigned)
var mutableVariable: String = "I can change"
mutableVariable = "I have changed"

// Read-only variable (cannot be reassigned)
val readOnlyVariable: String = "I cannot change"
// readOnlyVariable = "Trying to change" // This will cause a compilation error

// lateinit is used for variables that will be initialized later
lateinit var lateInitVariable: String
lateInitVariable = "I am initialized later"

// Typing can be inferred by the compiler, so you can omit the type declaration
var inferredVariable = "I am inferred as String"

// But you can typically specify the type if needed
var explicitVariable: Int = 42

// You can't change the type of variable once it's declared
// inferredVariable = 100 // This will cause a compilation error
```

[Kotlin types on docs](https://kotlinlang.org/docs/types-overview.html)

</details>

<details>
<summary>Loops</summary>

```kotlin
// For loop with a range
for (i in 1..5) {
    println("Number: $i")
}

// Use downTo and step in for loops
for (i in 10 downTo 1) {
    println("Descending: $i")
}

for (i in 0..10 step 2) {
    println("Even: $i")
}

// For loop with a collection
val fruits = listOf("Apple", "Banana", "Cherry")
for (fruit in fruits) {
    println("Fruit: $fruit")
}

// While and do while loops
var count = 0
while (count < 5) {
    println("Count: $count")
    count++
}

var count = 0
do {
    println("Count: $count")
    count++
} while (count < 5)
```

</details>

<details>
<summary>When expression (similar to switch in Java)</summary>

```kotlin
val number = 3
when (number) {
    1, 10 -> println("One or Ten")
    2 -> println("Two")
    3 -> {
        println("Can be multiple lines")
        println("Three")
    }
    in 11..20 -> println("Between Eleven and Twenty")
    !in 1..10 -> println("Not between One and Ten")
    else -> println("Other (default case)")
}

// Can be used directly as an expression to return a value
val result = when (number) {
    1 -> "One"
    else -> "Other"
}

// Or at return from a function
fun getNumberDescription(number: Int): String {
    return when (number) {
        1 -> "One"
        2 -> "Two"
        else -> "Other"
    }
}
```

</details>

<details>
<summary>Null safety</summary>

```kotlin
// Non-nullable variable (cannot hold null)
var nonNullableString: String = "I cannot be null"
// nonNullableString = null // This will cause a compilation error

// Nullable variable (can hold null) 
// "?" after the type indicates that it can be null
var nullableString: String? = "I can be null"
nullableString = null // This is allowed

// Safe call operator to access properties or methods of a nullable variable
// Preventing NullPointerException
val length = nullableString?.length // Safe call operator, returns null if nullableString is null

// Elvis operator to provide a default value if the expression on the left is null
val lengthOrDefault = nullableString?.length ?: 0 // Returns 0 if nullableString is null

// Not-null assertion operator to throw an exception if the variable is null
// Use with caution, as it can lead to NullPointerException if the variable is null
// val lengthNotNull = nullableString!!.length // This will throw an exception if nullableString is null

// Null safety and lists
var list: List<String>? = null // This list can be null
var list: List<String?> = listOf("A", null, "C") // This list can contain null values
```

</details>

<details>
<summary>Lists</summary>

```kotlin
// Immutable list (cannot be modified after creation)
val immutableList: List<String> = listOf("Apple", "Banana", "Cherry")
// immutableList.add("Date") // This will cause a compilation error

// Mutable list (can be modified after creation)
val mutableList: MutableList<String> = mutableListOf("Apple", "Banana", "Cherry")
mutableList.add("Date") // This is allowed
mutableList.remove("Banana") // This is allowed

// Map (key-value pairs)
val immutableMap: Map<String, Int> = mapOf("One" to 1, "Two" to 2, "Three" to 3)
// immutableMap["Four"] = 4 // This will cause a compilation error

val mutableMap: MutableMap<String, Int> = mutableMapOf("One" to 1, "Two" to 2, "Three" to 3)
mutableMap["Four"] = 4 // This is allowed
mutableMap.remove("Two") // This is allowed
```

[Kotlin collection overview on docs](https://kotlinlang.org/docs/collections-overview.html)

</details>

<details>
<summary>Companion object (similar to static members in Java)</summary>

```kotlin
class MyClass {
    companion object {
        // This is a static-like member that belongs to the class, not to instances
        val staticValue: String = "I am a companion object"
        
        fun staticFunction() {
            println("This is a static function")
        }
    }
}

// Accessing companion object members without creating an instance of MyClass
fun main() {
    println(MyClass.staticValue) // Output: I am a companion object
    MyClass.staticFunction() // Output: This is a static function
    
    // You don't need to create an instance of MyClass to access the companion object members
    // MyClass().staticFunction() // This is also possible but not necessary
}
```

</details>

<details>
<summary>Data classes</summary>

```kotlin
// Data class to represent a simple data structure with 
// automatically generated functions like equals(), hashCode(), and toString()
// It's also like Record in Java, but with more features
data class User(val name: String, val age: Int)

fun main() {
    val user1 = User("Alice", 30)
    val user2 = User("Alice", 30)
    println(user1) // Output: User(name=Alice, age=30)
    println(user1 == user2) // Output: true (data classes automatically generate equals() based on properties)
}
```

</details>

## Flyway 

Flyway is a database migration tool that helps manage and version control database schema changes. 
In this project, Flyway is used to automatically apply database migrations when the application starts.

The migration scripts are located in the `src/main/resources/db/migration` directory. 
Each script is named with a version number and a description, following the format `V{version}__{description}.sql`. 
For example, `V1__Create_customers_table.sql` creates the `customers` table. 
When the application starts, Flyway checks the database for existing migrations and applies any new migrations in order. 
This ensures that the database schema is always up to date with the application's requirements.