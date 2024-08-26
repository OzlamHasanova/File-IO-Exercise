import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static List<Employee> employeeList=new ArrayList<>();

    public static void main(String[] args){
        readEmployee();
        filterData();
        sortForStartDate();
        raiseSalarySomeEmployee();
    }
    public static void readEmployee(){
        //1. Read Employee Data from a Text File
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader("employees.txt"))){
            String line;
            while ((line=bufferedReader.readLine())!=null){
                String[] elementList=line.split(",");
                employeeList.add(new Employee(Integer.parseInt(elementList[0]),elementList[1],elementList[2],
                        Integer.parseInt(elementList[3]),Integer.parseInt(elementList[4]),Department.valueOf(elementList[5]),
                        Boolean.parseBoolean(elementList[6]), LocalDate.parse(elementList[7]),elementList[8],elementList[9],
                        Position.valueOf(elementList[10]),elementList[11]));
                System.out.println("-----------------------------------");
                employeeList.stream().forEach(System.out::println);
                System.out.println("-----------------------------------");

                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void filterData() {
        System.out.println("=============Filter out employees who are interns========");
        List<Employee> employeesFilter = employeeList.stream().filter(employee ->
                        Position.INTERN == (employee.getPosition()))
                .collect(Collectors.toList());
        if (employeesFilter.isEmpty()) {
            System.out.println("No intern");
        } else {
             employeesFilter.forEach(System.out::println);
        }
    }
    public static void sortForStartDate() {
        System.out.println("=============Sort the employees by their start date========");
        employeeList.stream().sorted(Comparator.comparing(Employee::getStartDate)).forEach(System.out::println);
    }
    public static void raiseSalarySomeEmployee(){
        System.out.println("=============Give a 10% salary raise to employees in the IT department with more than 5 years of experience========");
        List<Employee> employees=employeeList.stream().filter(employee ->
                        Period.between(employee.getStartDate(),LocalDate.now()).getYears()>5)
                .map(employee -> {employee.setSalary(employee.getSalary()+ employee.getSalary()*0.1);
                return employee;
                }).toList();
        if (employees.isEmpty()) {
            System.out.println("Does not have employee in the IT department with more than 5 years of experience");
        } else {
            employees.forEach(System.out::println);
        }
    }



}