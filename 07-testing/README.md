# JUnit Testing & TDD 🧪

## 📚 Overview
This section covers comprehensive testing strategies using JUnit 5, Mockito, and Test-Driven Development (TDD) practices. Testing is crucial for maintaining code quality and ensuring reliable software.

## 🎯 Testing Concepts Covered

### 1. JUnit 5 Fundamentals
- **Test Structure**: Given-When-Then pattern
- **Assertions**: assertEquals, assertTrue, assertThrows, etc.
- **Test Lifecycle**: @BeforeEach, @AfterEach, @BeforeAll, @AfterAll
- **Parameterized Tests**: @ParameterizedTest, @ValueSource
- **Dynamic Tests**: Runtime test generation

### 2. Test-Driven Development (TDD)
- **Red-Green-Refactor Cycle**: Write failing test, make it pass, refactor
- **Benefits**: Better design, higher coverage, regression prevention
- **Best Practices**: Small steps, clear test names, fast feedback

### 3. Unit Testing Best Practices
- **Test Isolation**: Independent tests
- **Test Naming**: Descriptive and clear names
- **Test Organization**: Arrange-Act-Assert pattern
- **Test Coverage**: Meaningful coverage, not just high percentage

### 4. Mockito Framework
- **Mock Objects**: @Mock, Mockito.mock()
- **Stubbing**: when().thenReturn(), when().thenThrow()
- **Verification**: verify(), times(), never()
- **Argument Matchers**: any(), eq(), argThat()

### 5. Spring Testing
- **Integration Tests**: @SpringBootTest, @WebMvcTest
- **Test Slices**: @DataJpaTest, @JsonTest
- **Test Configuration**: @TestConfiguration, @MockBean
- **Test Profiles**: @ActiveProfiles

## 📁 Examples Structure

```
07-testing/
├── junit-basics/
│   ├── BasicJUnitExample.java
│   ├── AssertionsExample.java
│   └── LifecycleExample.java
├── tdd-examples/
│   ├── TDD_Calculator_Example.java
│   ├── TDD_StringUtils_Example.java
│   └── TDD_BankAccount_Example.java
├── unit-testing/
│   ├── ServiceLayerTest.java
│   ├── RepositoryLayerTest.java
│   └── ControllerLayerTest.java
├── mockito-examples/
│   ├── BasicMockitoExample.java
│   ├── StubbingExample.java
│   ├── VerificationExample.java
│   └── ArgumentMatchersExample.java
├── spring-testing/
│   ├── SpringBootIntegrationTest.java
│   ├── WebLayerTest.java
│   ├── DataLayerTest.java
│   └── ServiceLayerIntegrationTest.java
├── advanced-testing/
│   ├── ParameterizedTestExample.java
│   ├── DynamicTestExample.java
│   ├── CustomExtensionExample.java
│   └── TestSuitesExample.java
└── resources/
    ├── test-data.json
    └── application-test.properties
```

## 🎓 Learning Objectives

By completing this section, you will understand:
- How to write effective unit tests using JUnit 5
- How to practice Test-Driven Development (TDD)
- How to use Mockito for mocking dependencies
- How to test Spring applications effectively
- How to organize and structure test code
- How to achieve meaningful test coverage

## 🔍 Testing Pyramid

```
    /\
   /  \     E2E Tests (Few)
  /____\    - Slow, expensive
 /      \   - Test complete user journeys
/__________\ 

 Integration Tests (Some)
 - Test component interactions
 - Database, external services

Unit Tests (Many)
- Fast, isolated
- Test single components
- High coverage
```

## ✅ JUnit 5 Assertions Overview

### Basic Assertions
```java
assertEquals(expected, actual);
assertNotEquals(unexpected, actual);
assertTrue(condition);
assertFalse(condition);
assertNull(value);
assertNotNull(value);
```

### Exception Testing
```java
assertThrows(ExceptionType.class, () -> {
    // code that should throw exception
});

assertDoesNotThrow(() -> {
    // code that should not throw exception
});
```

### Collection Assertions
```java
assertIterableEquals(expectedList, actualList);
assertArrayEquals(expectedArray, actualArray);
assertLinesMatch(expectedLines, actualLines);
```

## 🎭 Mockito Quick Reference

### Creating Mocks
```java
@Mock
private UserRepository userRepository;

// or
UserRepository userRepository = Mockito.mock(UserRepository.class);
```

### Stubbing
```java
when(userRepository.findById(1L)).thenReturn(user);
when(userRepository.save(any(User.class))).thenReturn(user);
doThrow(new RuntimeException()).when(userRepository).delete(any());
```

### Verification
```java
verify(userRepository).findById(1L);
verify(userRepository, times(2)).save(any(User.class));
verify(userRepository, never()).delete(any());
```

## 🔄 TDD Cycle

### 1. Red Phase
- Write a failing test
- Test should compile but fail
- Defines the expected behavior

### 2. Green Phase
- Write minimal code to make test pass
- Don't worry about code quality yet
- Focus on functionality

### 3. Refactor Phase
- Improve code structure
- Maintain test passing
- Clean up duplications

## 🏆 Testing Best Practices

### Test Naming
```java
// Good test names
shouldReturnUserWhenValidIdProvided()
shouldThrowExceptionWhenUserNotFound()
shouldCalculateDiscountForPremiumCustomers()

// Poor test names
testUser()
test1()
userTest()
```

### Test Structure (AAA Pattern)
```java
@Test
void shouldCalculateTotalWithDiscount() {
    // Arrange
    Order order = new Order(100.0);
    Customer premiumCustomer = new Customer("premium");
    
    // Act
    double total = orderService.calculateTotal(order, premiumCustomer);
    
    // Assert
    assertEquals(90.0, total); // 10% discount applied
}
```

### Test Independence
- Each test should be independent
- Tests should not depend on execution order
- Use @BeforeEach to set up clean state

---

**Previous**: [AOP Examples](../06-aop-examples/README.md) | **Next**: [Maven Projects](../08-maven-projects/README.md) 