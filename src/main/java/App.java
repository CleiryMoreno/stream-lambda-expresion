import com.lambda.model.Person;
import com.lambda.model.Product;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {

        Person p1 = new Person(1, "Mito", LocalDate.of(1991, 1, 21));
        Person p2 = new Person(2, "Code", LocalDate.of(1990, 2, 21));
        Person p3 = new Person(3, "Jaime", LocalDate.of(1980, 6, 23));
        Person p4 = new Person(4, "Duke", LocalDate.of(2019, 5, 15));
        Person p5 = new Person(5, "James", LocalDate.of(2010, 1, 4));

        Product pr1 = new Product(1, "Ceviche", 15.0);
        Product pr2 = new Product(2, "Chilaquiles", 25.50);
        Product pr3 = new Product(3, "Bandeja Paisa", 35.50);
        Product pr4 = new Product(4, "Ceviche", 15.0);
// Lambda /metho reference,
        List<Person> persons = Arrays.asList(p1, p2, p3, p4, p5);
        List<Product> products = Arrays.asList(pr1, pr2, pr3, pr4);
        // imperative programing
      /*  for( int i=0;i<persons.size();i++){
            System.out.println(persons.get(i));
        }
        for (Product p: products) {
            System.out.println(p);
        }
        */
        //Funtional Programing
       // persons.forEach(p-> System.out.println(p));
       // persons.forEach(System.out::println);
       List<Person> listFiltered= persons.stream()
               .filter(p->getAge(p.getBirthDate()) >= 18)
               .collect(Collectors.toList());
       //printList(listFiltered);
        // map
       List<Integer> listFiltered2= persons.stream()
               .filter(p->getAge(p.getBirthDate()) >= 18)
               .map(p->getAge(p.getBirthDate()))
               .collect(Collectors.toList());
       // printList(listFiltered2);
        //sorted
        Comparator<Person> byNameAsc= (Person o1,Person o2)->o1.getName().compareTo(o1.getName());
       Comparator<Person> byNameDes= (Person o1,Person o2)->o2.getName().compareTo(o1.getName());
        List<Person> list3= persons.stream()
                .sorted(byNameDes)
                .collect(Collectors.toList());
       // printList(list3);
    // anyMatch
        Predicate<Person> predicate= person -> person.getName().startsWith("J");
       boolean res= persons.stream()
                .anyMatch(predicate);
        // allMatch
        boolean res1= persons.stream()
                .allMatch(predicate);
        //noneMach
        boolean res2= persons.stream()
                .noneMatch(predicate);
    //limit /skpy for pagination
        int pageNumber=2;
        int pageSize=2;
        List<Person> list4= persons.stream()
                .skip(pageSize* pageNumber)
                .limit(pageSize)
                .collect(Collectors.toList());
        // group by

        Map<Double, List<Product>> collect1= products.stream()
                .filter(p->p.getPrice()>20)
                .collect(Collectors.groupingBy(Product::getPrice));
        // counting and grouping
        Map<String, Long> collect2= products.stream()
                .collect(Collectors.groupingBy(
                        Product::getName,Collectors.counting()
                ));
        // summing and grouping
        Map<String, Double> collect= products.stream()
                .collect(Collectors.groupingBy(
                        Product::getName,Collectors.summingDouble(Product::getPrice)
                ));

    //System.out.println(collect);

      DoubleSummaryStatistics summaryStatistics=  products.stream().collect(Collectors.summarizingDouble(Product::getPrice));


        Optional<Double> sum= products.stream()
                .map(Product::getPrice)
                .reduce(Double::sum);
        System.out.println(sum.get());
    }
    public  static int getAge(LocalDate date){
        return Period.between(date, LocalDate.now()).getYears();
    }
    public static void printList(List <?> list){
        list.forEach(System.out::println);
    }

}