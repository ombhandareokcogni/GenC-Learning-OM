package com.learning.patterns.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO (Data Access Object) Pattern Example
 * 
 * Purpose: Separates business logic from data access logic
 * Benefits: 
 * - Loose coupling between business and data layers
 * - Easy to switch between different data sources (DB, File, Web Service)
 * - Single responsibility for data operations
 */

// Step 1: Entity/Model class - represents the data structure
class Student {
    private int id;
    private String name;
    private String email;
    private String course;
    
    // Constructor
    public Student(int id, String name, String email, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    
    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', email='%s', course='%s'}", 
                           id, name, email, course);
    }
}

// Step 2: DAO Interface - defines contract for data operations
interface StudentDAO {
    // CRUD operations
    void save(Student student);          // Create
    Student findById(int id);            // Read
    List<Student> findAll();             // Read All
    void update(Student student);        // Update
    void delete(int id);                 // Delete
    
    // Custom query methods
    List<Student> findByCourse(String course);
    boolean exists(int id);
}

// Step 3: Concrete DAO Implementation - implements actual data access logic
class StudentDAOImpl implements StudentDAO {
    
    // Simulating database with in-memory storage
    private static Map<Integer, Student> database = new HashMap<>();
    private static int nextId = 1;
    
    @Override
    public void save(Student student) {
        // Auto-generate ID if not set
        if (student.getId() == 0) {
            student.setId(nextId++);
        }
        
        database.put(student.getId(), student);
        System.out.println("✓ Student saved: " + student);
    }
    
    @Override
    public Student findById(int id) {
        Student student = database.get(id);
        if (student != null) {
            System.out.println("✓ Student found: " + student);
        } else {
            System.out.println("✗ Student not found with ID: " + id);
        }
        return student;
    }
    
    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>(database.values());
        System.out.println("✓ Found " + students.size() + " students");
        return students;
    }
    
    @Override
    public void update(Student student) {
        if (database.containsKey(student.getId())) {
            database.put(student.getId(), student);
            System.out.println("✓ Student updated: " + student);
        } else {
            System.out.println("✗ Cannot update - Student not found with ID: " + student.getId());
        }
    }
    
    @Override
    public void delete(int id) {
        if (database.remove(id) != null) {
            System.out.println("✓ Student deleted with ID: " + id);
        } else {
            System.out.println("✗ Cannot delete - Student not found with ID: " + id);
        }
    }
    
    @Override
    public List<Student> findByCourse(String course) {
        List<Student> result = new ArrayList<>();
        for (Student student : database.values()) {
            if (student.getCourse().equalsIgnoreCase(course)) {
                result.add(student);
            }
        }
        System.out.println("✓ Found " + result.size() + " students in course: " + course);
        return result;
    }
    
    @Override
    public boolean exists(int id) {
        boolean exists = database.containsKey(id);
        System.out.println("✓ Student exists check for ID " + id + ": " + exists);
        return exists;
    }
}

// Step 4: Service Layer - uses DAO for business operations
class StudentService {
    
    // Dependency injection - service depends on DAO interface, not implementation
    private StudentDAO studentDAO;
    
    // Constructor injection
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
    
    // Business methods that use DAO
    public void enrollStudent(String name, String email, String course) {
        // Business logic: validate data before saving
        if (name == null || name.trim().isEmpty()) {
            System.err.println("✗ Error: Student name cannot be empty");
            return;
        }
        
        if (email == null || !email.contains("@")) {
            System.err.println("✗ Error: Invalid email format");
            return;
        }
        
        // Create and save student using DAO
        Student student = new Student(0, name.trim(), email.toLowerCase(), course);
        studentDAO.save(student);
    }
    
    public void transferStudent(int studentId, String newCourse) {
        // Business logic: find student and update course
        Student student = studentDAO.findById(studentId);
        if (student != null) {
            student.setCourse(newCourse);
            studentDAO.update(student);
            System.out.println("✓ Student transferred to course: " + newCourse);
        }
    }
    
    public void generateCourseReport(String course) {
        // Business logic: generate report using DAO
        List<Student> students = studentDAO.findByCourse(course);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("COURSE REPORT: " + course.toUpperCase());
        System.out.println("=".repeat(50));
        
        if (students.isEmpty()) {
            System.out.println("No students enrolled in this course.");
        } else {
            for (int i = 0; i < students.size(); i++) {
                System.out.println((i + 1) + ". " + students.get(i));
            }
        }
        System.out.println("Total Students: " + students.size());
        System.out.println("=".repeat(50));
    }
    
    public void removeStudent(int studentId) {
        // Business logic: check if student exists before deleting
        if (studentDAO.exists(studentId)) {
            studentDAO.delete(studentId);
            System.out.println("✓ Student removal completed");
        } else {
            System.err.println("✗ Cannot remove - Student does not exist");
        }
    }
}

// Step 5: Alternative DAO Implementation - demonstrates flexibility
class DatabaseStudentDAO implements StudentDAO {
    
    @Override
    public void save(Student student) {
        // This would connect to actual database
        System.out.println("🗄️ [DATABASE] Saving student to database: " + student.getName());
    }
    
    @Override
    public Student findById(int id) {
        // This would execute SQL query
        System.out.println("🗄️ [DATABASE] Executing: SELECT * FROM students WHERE id = " + id);
        return null; // Simplified for demo
    }
    
    @Override
    public List<Student> findAll() {
        System.out.println("🗄️ [DATABASE] Executing: SELECT * FROM students");
        return new ArrayList<>(); // Simplified for demo
    }
    
    @Override
    public void update(Student student) {
        System.out.println("🗄️ [DATABASE] Updating student in database: " + student.getId());
    }
    
    @Override
    public void delete(int id) {
        System.out.println("🗄️ [DATABASE] Executing: DELETE FROM students WHERE id = " + id);
    }
    
    @Override
    public List<Student> findByCourse(String course) {
        System.out.println("🗄️ [DATABASE] Executing: SELECT * FROM students WHERE course = '" + course + "'");
        return new ArrayList<>(); // Simplified for demo
    }
    
    @Override
    public boolean exists(int id) {
        System.out.println("🗄️ [DATABASE] Checking existence for student ID: " + id);
        return false; // Simplified for demo
    }
}

// Main class - demonstrates DAO pattern usage
public class DAOPatternExample {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("             DAO PATTERN DEMONSTRATION");
        System.out.println("=".repeat(60));
        
        // Step 1: Create DAO implementation (can be easily swapped)
        StudentDAO studentDAO = new StudentDAOImpl();
        
        // Step 2: Inject DAO into service layer
        StudentService studentService = new StudentService(studentDAO);
        
        System.out.println("\n📝 1. ENROLLING STUDENTS");
        System.out.println("-".repeat(30));
        
        // Enroll some students
        studentService.enrollStudent("John Doe", "john@example.com", "Computer Science");
        studentService.enrollStudent("Jane Smith", "jane@example.com", "Mathematics");
        studentService.enrollStudent("Bob Wilson", "bob@example.com", "Computer Science");
        studentService.enrollStudent("Alice Brown", "alice@example.com", "Physics");
        
        System.out.println("\n🔍 2. FINDING STUDENTS");
        System.out.println("-".repeat(30));
        
        // Find operations
        studentDAO.findById(1);
        studentDAO.findById(999); // Non-existent
        
        System.out.println("\n📊 3. GENERATING REPORTS");
        System.out.println("-".repeat(30));
        
        // Generate course reports
        studentService.generateCourseReport("Computer Science");
        studentService.generateCourseReport("Mathematics");
        
        System.out.println("\n✏️ 4. UPDATING STUDENT");
        System.out.println("-".repeat(30));
        
        // Transfer student
        studentService.transferStudent(2, "Computer Science");
        studentService.generateCourseReport("Computer Science");
        
        System.out.println("\n🗑️ 5. REMOVING STUDENT");
        System.out.println("-".repeat(30));
        
        // Remove student
        studentService.removeStudent(3);
        studentService.removeStudent(999); // Non-existent
        
        System.out.println("\n📋 6. FINAL REPORT");
        System.out.println("-".repeat(30));
        
        List<Student> allStudents = studentDAO.findAll();
        allStudents.forEach(System.out::println);
        
        System.out.println("\n🔄 7. DEMONSTRATING FLEXIBILITY");
        System.out.println("-".repeat(30));
        System.out.println("Switching to Database DAO implementation...");
        
        // Demonstrate flexibility - switch DAO implementation
        StudentDAO databaseDAO = new DatabaseStudentDAO();
        StudentService databaseService = new StudentService(databaseDAO);
        databaseService.enrollStudent("Test Student", "test@example.com", "Testing");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("               DAO PATTERN BENEFITS");
        System.out.println("=".repeat(60));
        System.out.println("✓ Separation of Concerns: Business logic separated from data access");
        System.out.println("✓ Flexibility: Easy to switch between different data sources");
        System.out.println("✓ Testability: Can mock DAO for unit testing");
        System.out.println("✓ Maintainability: Changes in data access don't affect business logic");
        System.out.println("✓ Reusability: DAO can be used by multiple services");
        System.out.println("=".repeat(60));
    }
} 