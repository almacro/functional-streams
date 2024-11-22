package org.example;

import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Code and examples from LinkedIn courses:
 * 
 * "Java: Lambdas and Streams" by Bethan Palmer
 * "Functional Programming with Streams in Java 9" by Marco Faella
 * 
 * @author almacro
 */
public class Main {
    
    static void helloDemo() {
        Greeting greeting = new HelloWorldGreeting();
        greeting.sayHello();
        
        Greeting greeting2 = new Greeting() {
            @Override
            public void sayHello() { System.out.println("Hello World"); }
        };
        greeting2.sayHello();

        Greeting greeting3 = () -> System.out.println("Hello World");
        greeting3.sayHello();
        
        Calculator calculator = (int x, int y) -> {
          Random rand = new Random();
          int randomNumber=  rand.nextInt(50);
          return x * y + randomNumber;
        };
        System.out.println(calculator.calculate(1, 2));
        
        IntBinaryOperator calc = (x,y) ->  {
          Random rand = new Random();
          int randomNumber=  rand.nextInt(50);
          return x * y + randomNumber;
        };
        System.out.println(calc.applyAsInt(1, 2));
    }
    static void streamsDemo() {
        String[] countriesArr = new String[]{ "France", "Paraguay", "Canada", "Spain", "Uruguay", "Chile" };
        List<String> countries = new ArrayList<>(Arrays.asList(countriesArr));
        // procedural
        /*
        for(String country: countries) {
            country = country.toUpperCase();
            if(!country.startsWith("C")) {
                System.out.println(country);
            }
        }
        */
                
        // functional
        countries.stream()
                .map(s -> s.toUpperCase())
                .filter(s -> !s.startsWith("C"))
                .sorted()
                .forEach(s -> System.out.println(s));
    }
    static void shoppingDemo() {
        Integer[] scores = new Integer[]{ 80, 66, 73, 92, 43 };
        Stream<Integer> scoresStream = Arrays.stream(scores);
        
        List<String> shoppingList = new ArrayList<>();
        shoppingList.add("coffee");
        shoppingList.add("bread");
        shoppingList.add("pineapple");
        shoppingList.add("milk");
        shoppingList.add("pasta");
        Stream<String> shoppingListStream = shoppingList.stream();
        //shoppingListStream.sorted().forEach(item -> System.out.println(item));
        /*
        shoppingListStream.sorted()      
                .map(item -> item.toUpperCase())
                .filter(item -> item.startsWith("P"))
                .forEach(item -> System.out.println(item));
        */
        List<String> sortedShoppingList = shoppingList.stream()
                .sorted()
                .map(item -> item.toUpperCase())
                .filter(item -> item.startsWith("P"))
                .collect(Collectors.toList());
        
        System.out.println(sortedShoppingList);
        System.out.println(shoppingList);
        
        // raises java.lang.IllegalStateException:
        //   stream has already been operated upon or closed
        //shoppingListStream.forEach(item -> System.out.println(item));
                
        Stream<String> lettersStream = Stream.of("a", "b", "c");
    }
    
    // iterative
    static int arraySum(int[] array) {
        int sum = 0;
        for(int i=0; i<array.length; i++) {
            sum += array[i];
        }
        return sum;
    }
    
    // recursive
    static int arraySum2(final int[] array, final int start) {
        if(start >= array.length)
            return 0;
        else
            return array[start] + arraySum2(array, start + 1);
    }
    
    static void arrayDemo() {
        int[] numbers = new int[]{ 9, 3, 7, 1, 2 };
        System.out.println("sum = " + arraySum(numbers));
        System.out.println("sum = " + arraySum2(numbers,0));
    }
    
    static void employeeDemo() {
        Employee mike = new Employee("Mike", 2000),
                 louise = new Employee("Louise", 2500);
        
        // old style
        Comparator<Employee> byName = new Comparator<>() {
            public int compare(Employee a, Employee b) {
                return a.getName().compareTo(b.getName());
            }
        };
        
        System.out.println("By name:");
        System.out.println(byName.compare(mike, louise));
        try {
            // throws NPE
            System.out.println(byName.compare(mike, null));
        } catch(NullPointerException e) {
            System.out.println(e);
        }
        
        Comparator<Employee> byNameThenNull = Comparator.nullsLast(byName);
        System.out.println("Then null:");
        System.out.println(byNameThenNull.compare(mike, louise));
        System.out.println(byNameThenNull.compare(mike, null));
        
        Comparator<Employee> nullThenByDecreasingName = byNameThenNull.reversed();
        System.out.println("Reversed:");
        System.out.println(nullThenByDecreasingName.compare(mike, louise));
        System.out.println(nullThenByDecreasingName.compare(mike, null));
    }
    
    static void lambdaDemo() {
        Comparator<String> byLength = (String a, String b) -> {
            return Integer.compare(a.length(), b.length());
        };
        
        // old style
        Comparator<Employee> byName = new Comparator<>() {
            public int compare(Employee a, Employee b) {
                return a.getName().compareTo(b.getName());
            }
        };
        
        // Our first lamba expression
        Comparator<Employee> byNameLambda1 = (Employee a, Employee b) -> {
            return a.getName().compareTo(b.getName());
        };
        
        // Remove parameter types
        Comparator<Employee> byNameLambda2 = (a, b) -> {
            return a.getName().compareTo(b.getName());
        };
        
        // Remove braces and 'return'
        Comparator<Employee> byNameLambda3 = (a, b) -> 
             a.getName().compareTo(b.getName());
        
        // Cannot remove braces and leave 'return'
        /*
        Comparator<Employee> byNameLambda4 = (a, b) -> 
            return a.getName().compareTo(b.getName());
        */

        // Expression with no parameters
        Runnable r = () -> { System.out.println("A compact Runnable!"); };
        Thread t1 = new Thread(r);
        
        // No need to even mention Runnable
        Thread t2 = new Thread(() -> { System.out.println("An implicit Runnable!"); });
        
        // No need for braces here
        Thread t3 = new Thread(() -> System.out.println("An implicit Runnable!"));
        
        // Expression with one parameter
        Consumer<String> lengthPrinter =
                s -> System.out.println(s.length());
    }
    
    public static <T> T getFirst(T[] array) {
        return array[0];
    }
    static void typingLambdaDemo() {
        String[] strarray = { "one", "two", "three" };
        String one = getFirst(strarray);
        
        // Standard syntax
        Consumer<String> c1 = msg -> System.out.println(msg.length());
        
        // Compile time error: not enough info
        //   incompatible types: Object is not a functional interface
        /*
        Object x1 = msg -> System.out.println(msg.length());
        */
        
        // Compile time error: not enough info
        /*
        Object x2 = (String msg) -> System.out.println(msg.length());
        */
        
        // OK: cast added
        Object x3 = (Consumer<String>) ((String msg) -> System.out.println(msg.length()));
        
        // Infer the parameter
        // OK, but inferred parameter type is Object
        Consumer<?> c2 = msg -> System.out.println(msg);
        
        // Compile-time error: Inference is *not* based on body of lambda
        /*
        Consumer<?> c3 = msg -> System.out.println(msg.length());
        */
        
        // OK: added manifest type to parameter
        Consumer<?> c4 = (String msg) -> System.out.println(msg.length());
    }
    
    static class LambdaImplementation {
        private int id = 1;
        
        public void foo() {
            System.out.println("\nInstance capturing lambda:");
            
            for (int i=0; i<5; i++) {
                // this-capturing lambda
                Consumer<String> myPrinter4 =
                        msg -> System.out.println("Consuming " + msg + ", " + id);
                
                myPrinter4.accept(myPrinter4.toString());
            }
        }
    }
    static void capturingDemo() {
        
        // Anonymous class, multiple instances
        System.out.println("\nAnonymous class:");
        for (int i=0; i<5; i++) {
            Consumer<String> myPrinter1 = new Consumer<>() {
              @Override
              public void accept(String msg) {
                  System.out.println("Consuming " + msg);
              }
            };
            myPrinter1.accept(myPrinter1.toString());
        }
        
        // Non-capturing lambda, one instance
        System.out.println("\nNon-capturing lambda:");
        for (int i=0; i<5; i++) {
            Consumer<String> myPrinter2 =
                    msg -> System.out.println("Consuming " + msg);
            
            myPrinter2.accept(myPrinter2.toString());
        }
        
        // Constant capturing lambda, one instance
        System.out.println("\nConstant capturing lambda:");
        final int secret = 42;
        for (int i=0; i<5; i++) {
            Consumer<String> myPrinter3 =
                    msg -> System.out.println("Consuming " + msg + ", " + secret);
            
            myPrinter3.accept(myPrinter3.toString());
        }

        (new LambdaImplementation()).foo();
    }
    
    interface ThreadSupplier {
        Thread giveMeAThread();
    }
    static <T> void printAll(T[] array,
                             Function<T,String> toStringFun) {
        int i = 0;
        for(T t: array) {
            System.out.println(i++ + ":\t" + toStringFun.apply(t));
        }
    }
    static void methodReferenceDemo() {
        
        // Static method
        Supplier<Thread> s1 = Thread::currentThread;
        
        // Nothing special about 'Supplier'...
        ThreadSupplier ts = Thread::currentThread;
        
        // Instance method (instance specified)
        Employee frank = new Employee("Frank", 3000);
        
        Integer i = frank.getSalary();
        Supplier<Integer> s2 = frank::getSalary;
        
        System.out.println(s2.get());
        
        // A common instance method (instance specified)
        Consumer<String> c1 = System.out::println;
        
        // An instance method (instance not specified)
        Function<Employee, Integer> f1 = Employee::getSalary;
        Integer frankSalary = f1.apply(frank);
        
        // A useful application: building a comparator based on a field
        
        // comparing expects Function<Employee, U>,
        // where U supports natural ordering (i.e., Comparable)
        Comparator<Employee> byName =
              Comparator.comparing(Employee::getName);
        
        Employee dept[] = new Employee[5];
        dept[0] = new Employee("Alec", 1500);
        dept[1] = new Employee("Bob", 1600);
        dept[2] = new Employee("Claire", 1700);
        dept[3] = new Employee("Danielle", 1800);
        dept[4] = new Employee("Ethan", 1900);
        printAll(dept, Employee::getName);
        
        System.out.println("");
        
        // Compile-time error: type inference failure
        //printAll(dept, Employee::getSalary);
        
        printAll(dept, emp -> "" + emp.getSalary());
    }
    
    static int composeHashCodes(Object a, Object b) {
        return a.hashCode() ^ b.hashCode();
    }
    static int composeHashCodes2(Object a, Object b) {
        Objects.requireNonNull(a, "a may not be null! " + getApplicationStatus());
        Objects.requireNonNull(b, "b may not be null! " + getApplicationStatus());
        
        return a.hashCode() ^ b.hashCode();
    }
    static int composeHashCodes3(Object a, Object b) {
        Objects.requireNonNull(a, () -> "a may not be null! " + getApplicationStatus());
        Objects.requireNonNull(b, () -> "b may not be null! " + getApplicationStatus());
        
        return a.hashCode() ^ b.hashCode();
    }
    static String getApplicationStatus() {
        System.out.println("getApplicationStatus");
        return "It's " + LocalTime.now();
    }
    static void functionalInterfacesDemo() {
        /*
        System.out.println(composeHashCodes("Hello", "world"));
        System.out.println(composeHashCodes("Hello", null));
        
        System.out.println(composeHashCodes2("Hello", "world"));
        System.out.println(composeHashCodes2("Hello", null));        
        */
        System.out.println(composeHashCodes3("Hello", "world"));
        System.out.println(composeHashCodes3("Hello", null));
    }
    
    static void primitiveFunctionalInterfacesDemo() {
        IntBinaryOperator sum = (a,b) -> a + b;
        
        final Random rand = new Random();
        BooleanSupplier randomBS = rand::nextBoolean;
    }
    
    static void composingFunctionsDemo() {
        try {
            PrintWriter writer = new PrintWriter("filename.txt");
            Consumer<String> logger = writer::println;
            Consumer<String> screener = System.out::println;
            Consumer<String> both = screener.andThen(logger);

            both.accept("Program started");
        } catch(java.io.IOException e) {
            System.err.println(e.getMessage());
        }
        
        Function<Employee, String> getName = Employee::getName;
        Function<String, Character> getFirstLetter = name -> name.charAt(0);
        Function<Employee, Character> initial = getName.andThen(getFirstLetter);
        
        Comparator<Employee> byName = Comparator.comparing(Employee::getName);
        Comparator<Employee> bySalary = Comparator.comparingInt(Employee::getSalary);
        Comparator<Employee> byNameAndSalary = byName.thenComparing(bySalary);
    }
    static void anotherStreamsDemo() {
        /*
        List<Integer> l = ...
        for(Integer n: l) {
          System.out.println(n);
        }
        
        Iterator<Integer> i = ...
        while(i.hasNext()) {
          System.out.println(n);
        }
        
        Stream<Integer> s = ...
        s.forEach(System.out::println);
        */
        Stream<Integer> fib = Stream.of(1, 1, 2, 3);
        Stream<String> italianNumbers = Stream.of("uno", "due", "tre");
        
        // infinite, unordered
        final Random random = new Random();
        //Stream<Integer> randoms = Stream.generate(random::nextInt);
        
        // infinite, ordered
        Stream<String> as = Stream.iterate("a", s -> s + "a");
        
        Supplier<Integer> supp = () -> {
             Integer result = random.nextInt();
             System.out.println("(supplying " + result + ")");
             return result;
        };
        
        System.out.println("\n Test 1");
        
        Stream<Integer> randoms = Stream.generate(supp);
        
        System.out.println("First stream built");
        
        randoms.filter(n -> n>=0).limit(3).forEach(System.out::println);
        
        System.out.println("\n\n\n Test 2");
        
        Stream<Integer> randoms2 = Stream.generate(supp);
        
        randoms2.limit(3).filter(n -> n>=0).forEach(System.out::println);
        
        fib.forEach(System.out::println);
        /*
        // java.lang.IllegalStateException:
        //   stream has already been operated upon or closed
        fib.forEach(System.out::println);
        
        // java.lang.IllegalStateException:
        //   stream has already been operated upon or closed
        fib = Stream.of(1, 1, 2, 3);
        fib.limit(2);
        fib.forEach(System.out::println);
        */
        fib = Stream.of(1, 1, 2, 3);
        Stream<Integer> shortFib = fib.limit(2);
        shortFib.forEach(System.out::println);
    }
    
    static void streamOpsDemo() {
        final Random rand = new Random();
        Stream<Integer> randoms = Stream.generate(rand::nextInt);
        
        randoms.filter(n -> n>=0)
                .distinct()
                .limit(10)
                .forEach(System.out::println);
    }
    
    public static void main(String[] args) {
        /* From "Java: Lambdas and Streams" */
        //helloDemo();
        //streamsDemo();
        //shoppingDemo();
        
        /* From "Functional Programming with Streams in Java 9" */
        //arrayDemo();
        //employeeDemo();
        
        //lambdaDemo();
        //typingLambdaDemo();
        //capturingDemo();
        //methodReferenceDemo();
        
        //functionalInterfacesDemo();
        //primitiveFunctionalInterfacesDemo();
        //composingFunctionsDemo();
        //anotherStreamsDemo();
        streamOpsDemo();
    }
}
