import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main {
    public static List<Employee> employeeList = new ArrayList<>();

    public static void main(String[] args) {
        readEmployee();
        filterData();
        sortForStartDate();
        raiseSalarySomeEmployee();
        getDepartmentSummaryReport();
        getNewEmployees();
        getUniqueEmployees();
        calculateAverageSalaryByDepartment();
        getOldEmployee();
        countEmployeesByPosition();
        getGroupEmployeesByPositionAndEachDepartment();
    }

    public static void readEmployee() {
        //1. Read Employee Data from a Text File
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] elementList = line.split(",");
                employeeList.add(new Employee(Integer.parseInt(elementList[0]), elementList[1], elementList[2],
                        Integer.parseInt(elementList[3]), Integer.parseInt(elementList[4]), Department.valueOf(elementList[5]),
                        Boolean.parseBoolean(elementList[6]), LocalDate.parse(elementList[7]), elementList[8], elementList[9],
                        Position.valueOf(elementList[10]), elementList[11]));
                employeeList.stream().forEach(System.out::println);

                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void filterData() {
        System.out.println("=============Filter out employees who are interns========");

        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("processed_employees_filter.txt"))) {
            List<Employee> employeesFilter = employeeList.stream().filter(employee ->
                            Position.INTERN == (employee.getPosition()))
                    .toList();
            if (employeesFilter.isEmpty()) {
                System.out.println("No intern");
            } else {
                employeesFilter.forEach(System.out::println);
            }
            for (Employee employee :
                    employeesFilter) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sortForStartDate() {
        System.out.println("=============Sort the employees by their start date========");
        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("processed_employees_sort.txt"))) {
            List<Employee> employees = employeeList.stream().sorted(Comparator.comparing(Employee::getStartDate)).toList();
            employees.forEach(System.out::println);

            for (Employee employee :
                    employees) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void raiseSalarySomeEmployee() {
        System.out.println("\n=============Give a 10% salary raise to employees in the IT department with more than 5 years of experience========");
        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("processed_employees_raise_salary.txt"))) {
            List<Employee> employees = employeeList.stream().filter(employee ->
                            Period.between(employee.getStartDate(), LocalDate.now()).getYears() > 5)
                    .map(employee -> {
                        employee.setSalary(employee.getSalary() + employee.getSalary() * 0.1);
                        return employee;
                    }).toList();
            if (employees.isEmpty()) {
                System.out.println("Does not have employee in the IT department with more than 5 years of experience");
            } else {
                employees.forEach(System.out::println);
            }
            for (Employee employee :
                    employees) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void getDepartmentSummaryReport() {
        System.out.println("\n=============Generate a department summary report========");
        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("department_summary.txt"))) {
            Map<Department, List<Employee>> employees = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment));
            employees.forEach((department, employees1) -> {
                        System.out.println(department);
                        employees1.forEach(System.out::println);
                        try {
                            bufferedWriter.write(department.name());
                            bufferedWriter.newLine();
                            for (Employee employee :
                                    employees1) {
                                bufferedWriter.write(employee.toString());
                                bufferedWriter.newLine();
                            }
                            bufferedWriter.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void getNewEmployees() {
        System.out.println("=============List employees who started in the last 2 years========");

        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("new_employees.txt"))) {
            List<Employee> employeesFilter = employeeList.stream().filter(employee ->
                            Period.between(employee.getStartDate(), LocalDate.now()).getYears() < 2)
                    .toList();
            if (employeesFilter.isEmpty()) {
                System.out.println("No new employees");
                bufferedWriter.write("No new employees");
            } else {
                employeesFilter.forEach(System.out::println);
            }
            for (Employee employee :
                    employeesFilter) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void getUniqueEmployees() {
        System.out.println("\n=============Identify and remove duplicate employees based on their ID or email========");

        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("unique_employees.txt"))) {
            Map<Integer, List<Employee>> employeesDuplicateId = employeeList.stream().collect(Collectors.groupingBy(Employee::getId));
            employeesDuplicateId.forEach((id, employees1) -> {
                        if (employees1.size() > 1) {
                            System.out.println(id);
                            employees1.forEach(System.out::println);
                            try {
                                bufferedWriter.write(id);
                                bufferedWriter.newLine();
                                for (Employee employee :
                                        employees1) {
                                    bufferedWriter.write(employee.toString());
                                    bufferedWriter.newLine();
                                    employeeList.remove(employee);
                                }
                                bufferedWriter.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void calculateAverageSalaryByDepartment() {
        System.out.println("\n=============Calculate the average salary in each department========");
        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("department_summary.txt"))) {
            Map<Department, Double> departmentAndAverageSalary = employeeList.stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment,
                            Collectors.averagingDouble(Employee::getSalary)));

            departmentAndAverageSalary.forEach((department, avgSalary) -> {
                        System.out.println(department);
                        System.out.println(avgSalary);
                        try {
                            bufferedWriter.write(department.name());
                            bufferedWriter.newLine();
                            bufferedWriter.write(avgSalary.toString());
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getOldEmployee() {
        System.out.println("\n=============Find the employee with the earliest start date========");
        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("processed_employees_sort.txt"))) {
            Employee employee = employeeList.stream().min(Comparator.comparing(Employee::getStartDate)).orElseThrow();
            System.out.println(employee);
            bufferedWriter.write(employee.toString());

            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void countEmployeesByPosition() {
        System.out.println("\n=============Count the number of employees in each position========");
        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("employees_by_position.txt"))) {
            Map<Position, Long> employees = employeeList.stream().collect(Collectors.groupingBy(Employee::getPosition, Collectors.counting()));
            employees.forEach((position, count) -> {
                        System.out.println(position + "==>" + count);
                        try {
                            bufferedWriter.write(position.name());
                            bufferedWriter.newLine();

                            bufferedWriter.write(count.toString());
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void getGroupEmployeesByPositionAndEachDepartment() {
        System.out.println("\n============= Group employees first by department and then by position within each department========");
        try (BufferedWriter bufferedWriter = new BufferedWriter
                (new FileWriter("employees_by_department_and_position.txt"))) {
            Map<Department, Map<Position, List<Employee>>> employees = employeeList.stream()
                    .collect(Collectors.groupingBy
                            (Employee::getDepartment, Collectors.groupingBy(Employee::getPosition)));
            employees.forEach((department, employeeList) -> {
                System.out.println("\nDepartment => "+department);

                try {
                    bufferedWriter.write("Department=> "+department.name());
                    bufferedWriter.newLine();
                    employeeList.forEach((position, employees1) -> {
                                System.out.println(position);
                                try {
                                    bufferedWriter.write(position.name());
                                    bufferedWriter.newLine();
                                    employees1.forEach(System.out::println);
                                    for (Employee employee :
                                            employees1
                                    ) {
                                        bufferedWriter.write(employee.toString());
                                        bufferedWriter.newLine();
                                    }
                                    bufferedWriter.flush();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}