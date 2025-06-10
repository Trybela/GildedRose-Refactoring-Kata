## Project Details

- **Language**: Java 17
- **Build Tool**: Gradle
- **Testing**: JUnit 5
- **Mutation Testing**: PIT (Pitest plugin)
- **Code Coverage**: JaCoCo plugin

## Domain Description

The system tracks various types of items in a store. Each item has the following properties:
- **name**: the name of the item
- **sellIn**: the number of days we have to sell the item
- **quality**: how valuable the item is

### Item Types

- **Default items**: Quality degrades by 1 each day before and after the sellIn date.
- **Aged Brie**: Increases in quality the older it gets.
- **Backstage passes**: Increases in quality as the sellIn date approaches:
  - +2 when there are 10 days or less
  - +3 when there are 5 days or less
  - Drops to 0 after the concert
- **Sulfuras**: Legendary item, never has to be sold or decreases in quality.
- **Conjured items**: Degrade in quality twice as fast as normal items.

## Running the Project

Build the project (includes running tests, code coverage and mutation tests):

```
./gradlew clean build
```

Run unit tests:

```
./gradlew test
```

Run mutation tests (Pitest):

```
./gradlew pitest
```

Generate code coverage report:

```
./gradlew jacocoTestReport
```

## Run the TextTest Fixture from Command-Line

```
./gradlew -q text
```

### Specify Number of Days

For e.g. 10 days:

```
./gradlew -q text --args 10
```

You should make sure the gradle commands shown above work when you execute them in a terminal before trying to use TextTest (see below).


## Run the TextTest approval test that comes with this project

There are instructions in the [TextTest Readme](../texttests/README.md) for setting up TextTest. What's unusual for the Java version is there are two executables listed in [config.gr](../texttests/config.gr) for Java. The first uses Gradle wrapped in a python script. Uncomment these lines to use it:

    executable:${TEXTTEST_HOME}/Java/texttest_rig.py
    interpreter:python

The other relies on your CLASSPATH being set correctly in [environment.gr](../texttests/environment.gr). Uncomment these lines to use it instead:

    executable:com.gildedrose.TexttestFixture
    interpreter:java


## Step-by-Step Solution Approach


1. **Jacoco gate first** – wired Jacoco into Gradle with a *100 % line-coverage* rule on the production packages (DTO `Item` excluded).
   *Result → build red → forced to write tests.*

2. **Golden-master test** – ported the original `TexttestFixture` to JUnit 5, capturing day-by-day output so later refactors must stay behaviour-compatible.

3. **Mutation testing (PIT)** – enabled `STRONGER` mutators with a *100 % kill rate*.
   *Result → build red again → added parameterised tests until every mutant died.*

4. **Started step by step refactoring** – refactor main logic on small logical steps. Isolation logic for each of product types.
5. **Introduce new Conjured product** - write test for Conjured product behaviour and bussines logic for it.
6. **Wrap raw Item into AbstractItem like wrapper** - introduce AbstractItem class with encapsulation compliance and inheriting classes that corresponds item types.
7. ***ItemType enum like factory for types** - create enum that to resolve right implementation for each input product name.
7. **Input validation** – enforced `quality ∈ [0, 50]` and other invariants at object-creation time; wrote dedicated tests to prove guards.

8. **Logging added** – introduced SLF4J + log4j;
9. **Error handling**  - custom exceptions and handling.

