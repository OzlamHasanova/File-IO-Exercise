import java.time.LocalDate;
import java.util.Comparator;

public class Employee implements Comparator {
    private int id;
    private String name;
    private String surname;
    private int age;
    private double salary;
    private Department department;
    private boolean isEmployee;
    private LocalDate startDate;
    private String email;
    private String phoneNumber;
    private Position position;
    private String address;

    public int getId() {
        return id;
    }

    public Employee(int id, String name, String surname, int age, double salary, Department department, boolean isEmployee, LocalDate startDate, String email, String phoneNumber, Position position, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.isEmployee = isEmployee;
        this.startDate = startDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return id +
                "," + name + '\'' +
                "," + surname + '\'' +
                "," + age +
                "," + salary +
                "," + department +
                "," + isEmployee +
                "," + startDate +
                "," + email + '\'' +
                "," + phoneNumber + '\'' +
                "," + position +
                "," + address
                ;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
