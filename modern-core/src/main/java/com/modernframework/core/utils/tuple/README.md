# Tuple Package Documentation

## Overview
The `com.modernframework.core.utils.tuple` package provides lightweight, generic data structures called tuples that allow storing multiple values of potentially different types in a single object. Tuples are useful for returning multiple values from methods or storing temporary data without creating a dedicated class.

## Classes

### Tuple2<T0, T1>
A 2-element tuple that stores two values of potentially different types.

#### Example Usage:
```java
Tuple2<String, Integer> nameAge = new Tuple2<>("John", 25);
// Or using the factory method:
Tuple2<String, Integer> nameAge = Tuples.of("John", 25);

System.out.println(nameAge._0); // Output: John (first element)
System.out.println(nameAge._1); // Output: 25 (second element)
```

### Tuple3<T0, T1, T2>
A 3-element tuple that stores three values of potentially different types.

#### Example Usage:
```java
Tuple3<String, Integer, Boolean> person = Tuples.of("John", 25, true);
System.out.println(person._0); // Output: John
System.out.println(person._1); // Output: 25
System.out.println(person._2); // Output: true
```

### Tuple4<T0, T1, T2, T3>
A 4-element tuple that stores four values of potentially different types.

#### Example Usage:
```java
Tuple4<String, Integer, Boolean, Double> data = Tuples.of("John", 25, true, 100.5);
```

### Tuple5<T0, T1, T2, T3, T4>
A 5-element tuple that stores five values of potentially different types.

#### Example Usage:
```java
Tuple5<String, Integer, Boolean, Double, Character> complexData = Tuples.of("John", 25, true, 100.5, 'A');
```

### Tuples
A utility class that provides factory methods to create tuples more easily.

#### Available Methods:
- `of(T0 _0, T1 _1)` - Creates a Tuple2
- `of(T0 _0, T1 _1, T2 _2)` - Creates a Tuple3
- `of(T0 _0, T1 _1, T2 _2, T3 _3)` - Creates a Tuple4
- `of(T0 _0, T1 _1, T2 _2, T3 _3, T4 _4)` - Creates a Tuple5

## Use Cases

Tuples are particularly useful in the following scenarios:

1. **Returning Multiple Values**:
```java
public Tuple2<String, Integer> getPersonInfo() {
    return Tuples.of("John", 25);
}
```

2. **Key-Value Pairing**:
```java
List<Tuple2<String, Object>> attributes = new ArrayList<>();
attributes.add(Tuples.of("name", "John"));
attributes.add(Tuples.of("age", 25));
```

3. **Method Parameters**:
```java
public void processTuple(Tuple3<String, Integer, Boolean> data) {
    // Process the tuple data
    System.out.println("Name: " + data._0 + ", Age: " + data._1 + ", Active: " + data._2);
}
```

## Features

- **Type Safety**: Generic type parameters ensure type safety
- **Immutability**: Tuples are immutable by design (though the current implementation has public fields)
- **Equality**: Proper `equals()` and `hashCode()` implementations
- **String Representation**: Human-readable `toString()` method
- **Utility Methods**: Factory methods for easy creation

## Real-World Example from the Framework

Based on the usage in DateUtils.java, tuples are used to store date patterns and their corresponding format strings:

```java
// From DateUtils.java
private static final Tuple2<Pattern, String>[] SIMPLE_DATE_FORMATS = new Tuple2[]{
    Tuples.of(Pattern.compile("^\\\\d{4} \\\\d{2} \\\\d{2} \\\\d{2} \\\\d{2} \\\\d{2}$"), "yyyy MM dd HH mm ss"),
    Tuples.of(Pattern.compile("^\\\\d{4} \\\\d{2} \\\\d{2}$"), "yyyy MM dd"),
    // ...
};
```

This approach allows the framework to efficiently store and process pairs of related information like regex patterns and their corresponding date format strings.