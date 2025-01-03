# Hibernate knowledge
## Core
### Architecture

<details>
  <summary>Overview</summary>
  <br/>
  
  ![](images/architecture_overview.png)
  
</details>

<details>
  <summary>JPA vs ORM</summary>
  <br/>
  
  **ORM:** Object Relational Mapping is concept/process of converting the data from Object oriented language to relational DB and vice versa. It's enables developers to map Java objects to database tables without having to create SQL queries.
  
  **JPA:** The Java Persistence API is a Java specification. JPA is now considered the standard approach for Object to Relational Mapping (ORM).
  
</details>

<details>
  <summary>JPA and Hibernate</summary>
  <br/>

  JPA is a specification for accessing, managing, and persisting data between Java objects and relational databases. While Hibernate is a popular implementation of the JPA specification. It provides the actual functionality defined by JPA.
  
  ![](images/hibernate-jpa.png)
  
  JPA architecture | Hibernate architecture | 
  --- | --- |
  EntityManagerFactory | SessionFactory |
  EntityTransaction | Transaction |
  EntityManager | Session |

</details>

### Advantages and disadvantages (vs JBDC)

<details>
  <summary>Advantages</summary>
  <br/>
  
  + Hibernate supports to build relationships based on the data model.
  + Hibernate supports lot of databases.
  + Hibernate maintains database connection pool.
  + Hibernate has Caching mechanism. Using this number of database hits will be reduced
  + Hibernate generates the SQL on the fly and then automatically executes the necessary SQL statements. 
  
</details>

<details>
  <summary>Disadvantages</summary>
  <br/>
  
  + Lots of API to learn
  + Slower than JDBC
  + Generates complex quires with many joins
  + Not suitable for small project
  
</details>

### Persistence Context

A persistence context is a set of entity instances. Within the persistence context, the entity instances and their lifecycle are managed.

<details>
  <summary>Persistence Context Type</summary>
  <br/>
    
  Persistence contexts are available in two types:

  + Transaction-scoped persistence context (default)
  + Extended-scoped persistence context

</details>

<details>
  <summary>Persistent Context and Session/Entity Manager</summary>
  <br/>
  
  + **Persistent Context** is a run time memory area where Hibernate holds the references of objects (entities). At runtime whenever a session is opened and closed, between those open and close boundaries Hibernate maintains the object in a **Persistence Context**.
  + **Session/EntityManager** provides API to interact with the **enities**. Some APIs are provided by **Session**: _(session -> entities)_
    + Basic CRUD operation
    + Query Execution
    + Control of Transaction
    + Management of Persistent Context
    
    ![](images/entity_manager_&_persistence_context.gif)
  
  Ref: https://tech.lalitbhatt.net/2014/07/hibernate-persistent-context-and-session.html
  
</details>

### Entity lifecycle

<details>
  <summary>Overview</summary>
  <br/>
  
  ![](images/hibernate-entity-lifecycle.jpg)
  
  State | Description | 
  --- | --- |
  New or Transient | Transient entities exist in heap memory as normal Java objects. The persistent context does not track the changes done them. It don't associcate with any **Session** and not mapped to any database table row.|
  Persistent or Managed | A persistent entity is mapped to a specific database row. Hibernate’s current running **Session** is responsible for tracking all changes. |
  Detached | Detached entities have a representation in the database but these are currently not associcate with any **Session**. |
  Removed | Removed entities are entities that already exist in the database and will be deleted after flushing (commit) |
  
  Ref: https://howtodoinjava.com/hibernate/hibernate-entity-persistence-lifecycle-states/
  
</details>

### Automatic dirty checking
<details>
  <summary>How Automatic Dirty Checking Works?</summary>
  <br/>

  + **Entity Tracking:** When you retrieve an entity from the database using a Hibernate session, Hibernate keeps a snapshot of the entity’s original state.
  + **Modification Detection**: When we modify the entity’s properties within a transaction, Hibernate tracks these changes.
  + **Session Flush:** When the session is flushed (either manually or automatically at the end of a transaction), Hibernate compares the current state of the entity with the original snapshot.
  + **SQL Update Execution:** If Hibernate detects any changes it automatically generates and executes the necessary SQL UPDATE statements to synchronize the database.
  
  For managed entities, Hibernate can auto-detect incoming changes and schedule SQL UPDATES. This mechanism is called automatic dirty checking.
  
  ![](images/defaultflusheventflow.png)
  _At flush time (commit)_

  _Benefits of Automatic Dirty Checking:_

  + Ensures that the database always reflects the current state of your objects.
  
  Ref: https://www.codementor.io/@narendrasharma95ns/life-cycle-of-an-entity-object-dirty-checking-in-hibernate-lvh1dh5jz
  
</details>

<details>
  <summary>Write-Behind Technique</summary>
  <br/>
  
  Ref: http://learningviacode.blogspot.com/2012/02/write-behind-technique-in-hibernate.html
  
</details>

### Inheritance

Inheritance is one of the most important of object-oriented principles. But the relational databases do not support inheritance. Hibernate’s Inheritance Mapping strategies deal with this issue.

<details>
  <summary>Types of inheritance strategy</summary>
  <br/>
  
  + MappedSuperclass – the parent classes, can't be entities
  + Single Table – The entities from different classes are placed in a single table.
  + Joined Table – Each class has its table, and querying a subclass entity requires joining the tables.
  + Table per Class – All the properties of a class are in its table, so no join is required.
  
  Ref: https://www.baeldung.com/hibernate-inheritance
  
</details>
<details>
  <summary>Mapped Superclass</summary>
  <br/>

  This strategy is used when you want to share common fields between multiple entities without creating a separate table for the superclass.
  
  ![](images/mapped-super-class.png)
  
</details>
<details>
  <summary>Single Table</summary>
  <br/>

  All classes in the hierarchy are mapped to a single table. This is the _default_ strategy
  
  ![](images/single_table.png)
  
</details>
<details>
  <summary>Joined Table</summary>
  <br/>
  
  ![](images/sub_join_class.png)
  
</details>
<details>
  <summary>Table per Class</summary>
  <br/>
  
  ![](images/table-per-class.png)
  
</details>
<details>
  <summary>@DiscriminatorValue annotation in Hibernate</summary>
  <br/>
  
  Discriminator is commonly used in `SINGLE_TABLE` inheritance. You need a column to identify the type of the record.
  
  _Example:_ You have a class `Student` and 2 sub-classes: `GoodStudent` and `BadStudent`. Both Good and BadStudent data will be stored in 1 table, but of course we need to know the type and that's when (`DiscriminatorColumn` and) `DiscriminatorValue` will come in.
  
  ```
  @Entity
  @Table(name ="Student")
  @Inheritance(strategy=SINGLE_TABLE)
  @DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,
      name = "Student_Type")
  public class Student{
       private int id;
       private String name;
  }
  ```
  _Student class_
  
  ```
  @Entity
  @DiscriminatorValue("Bad Student")
  public class BadStudent extends Student{ 
   //code here
  }
  ```
  _Bad Student class_
  
  ```
  @Entity
  @DiscriminatorValue("Good Student")
  public class GoodStudent extends Student{ 
  //code here
  }
  ```
  _Good Student class_
  
  id|Student_Type | Name |
  --|-------------|-------|
  1 |Good Student | Ravi |
  2 |Bad Student  | Sham |
  
  _Result_
  
</details>

### Identifiers

<details>
  <summary>Identify type</summary>
  <br/>
  
  + **Auto:** The persistence provider chooses the generation strategy.
  + **Identity:** Relies on the database’s identity column to generate values.
  + **Sequence:** Uses a database sequence to generate values.
  + **Table:** Use a database table to simulate a sequence (The table will be stored ids).
  
  Ref: https://thorben-janssen.com/primary-key-mappings-jpa-hibernate/
</details>
<details>
  <summary>Identity vs Sequence</summary>
  <br/>
  
  + **Identity:**
  
  Hibernate will create a sequence and use for primary column is default value.
  ```
  CREATE TABLE public.session
  (
      id bigint NOT NULL DEFAULT nextval('session_id_seq'::regclass),
      ..
  )
  ```
  The next value of the sequence will be called automatically when the record is inserted into the database.
  + **Sequence:**
  
  ```
  select
    nextval ('data_sequence')
  -----------------------------
  insert 
  into
      data
      (data1, data2, data3, data4, data_id) 
  values
      (?, ?, ?, ?, ?)
  ```
  
  Hibernate will create a sequence and select the next value of the sequence before inserting the record into the database.
  
  _Note:_ 
  + Only true with **Postgres** database.
  + PostgreSQL uses the `SERIAL` or `BIGSERIAL` data type to create an auto-increment column. When you use `GenerationType.IDENTITY`, Hibernate will map this to a `SERIAL` column in PostgreSQL.
  
</details>
<details>
  <summary>Graps of the Primary key when rollback the transactions</summary>
  <br/>
  
  + Ref: https://stackoverflow.com/questions/449346/mysql-auto-increment-does-not-rollback
  + Ref: https://stackoverflow.com/questions/22787153/gaps-in-the-sequence-values-generated-by-jpa-generatedvalue-with-postgresql
  + Ref: https://stackoverflow.com/questions/20636144/are-jpa-and-hibernate-entity-identifiers-reset-to-null-after-a-rollback/67401366#67401366
</details>
<details>
  <summary>Why does Hibernate disable INSERT batching when using an IDENTITY?</summary>
  <br/>
  
  + Ref: https://stackoverflow.com/questions/27697810/why-does-hibernate-disable-insert-batching-when-using-an-identity-identifier-gen
</details>
<details>
  <summary>Sequence identifier strategies</summary>
  <br/>
  
  + Ref: https://vladmihalcea.com/hibernate-hidden-gem-the-pooled-lo-optimizer/
  + Ref: https://vladmihalcea.com/the-hilo-algorithm/
</details>


### Map Associations

<details>
  <summary>Types of associations</summary>
  <br/>
  
  + OneToOne
  + OneToMany
  + ManyToOne
  + ManyToMany
  
  ![](images/relationships.png)
  
</details>
<details>
  <summary>OneToOne</summary>
  <br/>

  `User` and `Profile`. Each user has one profile, and each profile is associated with one user.
  + **Default Fetch Type:** `FetchType.EAGER`

  ```
  @Entity
  public class User {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
  
      private String username;

      @Column("profile_id")
      private Long profileId;

      @OneToOne(cascade = CascadeType.ALL)
      @JoinColumn(name = "profile_id", insertable = false, updatable = false)
      private Profile profile;
  }
  ```

  ```
  @Entity
  public class Profile {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
  
      private String bio;
  
      @OneToOne(mappedBy = "profile")
      private User user;
  }
  ```

  **Optimize peformance with** `@OneToOne` (_verifying_)**:**
  
  PK and FK columns are most often indexed, so sharing the PK can reduce the index footprint by half, which is desirable since you want to store all your indexes into memory to speed up index scanning. And EAGER fetching is bad.
  
  The best way to map a `@OneToOne` relationship is to use `@MapsId`. (use the `@JoinColumn` to customize the key name)
  
  While the unidirectional `@OneToOne` association can be fetched lazily, the parent-side of a bidirectional `@OneToOne` association is not (child has `@JoinColumn`, parent - `mappedBy`).
  
  ```
  @Entity(name = "management")
  public class ManagementEntity implements Serializable {

      private static final long serialVersionUID = -495703064152328044L;

      @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
      @JoinColumn(name = "data_id")
      @MapsId
      private DataEntity dataEntity;

      @Id
      @Column(name = "management_id")
      private Long managementId; // You’ll notice that the @Id column no longer uses a @GeneratedValue annotation since the identifier is populated with the identifier of the DataEntity association.
      
      ...
  }
  ```
  
  Ref: https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
</details>
<details>
  <summary>OneToMany & ManyToOne</summary>
  <br/>
  
  `@ManyToOne` might be just enough. It does not mean this should be the default option for every one-to-many database relationship.
  
  In reality, `@OneToMany` is practical only when many means few.

  + `@OneToMany` - **Default Fetch Type:** `FetchType.LAZY`
  + `@ManyToOne` - **Default Fetch Type:** `FetchType.EAGER`

  _Exmaple:_
  ```
  @Entity
  public class Author {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
  
      @OneToMany(mappedBy = "author")
      private List<Book> books = new ArrayList<>();
  
      // Getters and setters
  }
  ```
  ```
  @Entity
  public class Book {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
  
      private Long authorId;
  
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "author_id", insertable = false, updatable = false)
      private Author author;
  }
  ```
  
  Note: If not careful, `@ManyToOne` can cause _n+1 select issue_.
  
  + Ref: https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/  
  + Ref: https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/#Avoid_the_mapping_of_huge_to-many_associations
</details>
<details>
  <summary>ManyToMany</summary>
  <br/>
  If you have an entity `StudentCourse` to represent the join table, you can still use `insertable=false` and `updatable=false` in the `@JoinColumn` annotations
  
  ```
    @Entity
    public class Student {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        private String name;
    
        @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
        private List<StudentCourse> studentCourses = new ArrayList<>();
    
        // Getters and setters
    }
  ```

  ```
  @Entity
  public class Course {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
  
      private String title;
  
      @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
      private List<StudentCourse> studentCourses = new ArrayList<>();
  
      // Getters and setters
  }
  ```

  ```
    @Entity
    public class StudentCourse {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "student_id", insertable = false, updatable = false)
        private Student student;
    
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "course_id", insertable = false, updatable = false)
        private Course course;
    
        // Additional fields if needed
    
        // Getters and setters
    }
  ```
  
  **Optimize peformance with** `@ManyToMany` (_verifying_)**:**
  
  You should never use a `List` if you model a `Many-to-Many` association. Instead of a `List`, we can use a `Set`.
  
  Avoid the CascadeTypes REMOVE and ALL, which includes REMOVE
  
  ```
  @Entity(name = "post")
  public class Post {
  
      @ManyToMany(cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      })
      @JoinTable(name = "post_tag",
          joinColumns = @JoinColumn(name = "post_id"),
          inverseJoinColumns = @JoinColumn(name = "tag_id")
      )
      private Set<Tag> tags = new TreeSet<>();
      ...
  }
  ```
  
  ```
  @Entity(name = "tag")
  public class Tag {

      @ManyToMany(mappedBy = "tags")
      private Set<Post> posts = new TreeSet<>();
      ...
  }
  ```
  
  + Ref: https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
  + Ref: https://thorben-janssen.com/best-practices-for-many-to-many-associations-with-hibernate-and-jpa/
</details>

<details>
  <summary>insertable=false, updatable=false & separate field for the foreign key</summary>
  <br/>

  When you use `insertable=false`, `updatable=false` in a `@JoinColumn` and create a separate field for the foreign key, combined with lazy loading in relationship (`@OneToOne` or `@OneToMany`). In case, you have to get value of foreign key, by this way we don't need to load the associate entity. 

  _Example:_

```
  @Entity
  public class Order {
      @Id
      private Long id;
  
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "customer_id", insertable = false, updatable = false)
      private Customer customer;
  
      @Column(name = "customer_id")
      private Long customerId;
  
      // getters and setters
  }
```

```
  Order order = entityManager.find(Order.class, orderId);
  Long customerId = order.getCustomerId(); // This does not trigger loading of the Customer entity
```
</details>

<details>
  <summary>Lazy vs eager load</summary>
  <br/>

  **Lazy Loading:**

  + Lazy loading is a fetching strategy (such as collections or associations) not loaded immediately when the parent entity is loaded. The related entities are loaded only when they are accessed at runtime.
  + `@OneToMany`, `@ManyToMany` default fetch type is lazy.

  **Eager Loading:**
  
  + Eager loading is a fetching strategy loaded immediately along with the parent entity.
  + `@ManyToOne`, `@OneToOne` default fetch type is eager.

</details>

### Cascade
<details>
  <summary>Cascading best practices</summary>
  <br/>

  1. Cascading only makes sense only for Parent – Child associations (the Parent entity state transition being cascaded to its Child entities). 
  2. Cascading from Child to Parent is not very useful and usually, it’s a mapping code smell.
  3. Use CascadeType.REMOVE and CascadeType.ALL only for to-one associations.
  
  + Ref: https://vladmihalcea.com/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
  + Ref: https://thorben-janssen.com/avoid-cascadetype-delete-many-assocations/#Remove_One_By_One

</details>

### Query
<details>
  <summary>JPQL</summary>
  <br/>
  
  The JPQL (Java Persistence Query Language) is a query language which is used to perform database operations on persistent entities. The role of **JPA** is to transform JPQL into SQL.
  
</details>
<details>
  <summary>Sort</summary>
  <br/>
  
  With Spring Data JPA, you can also add a parameter of type Sort to your method definition
  
  ```
  @Query("FROM Author WHERE firstName = ?1")
  List<Author> findByFirstName(String firstName, Sort sort);
  ```
  
  ```
  Sort sort = new Sort(Direction.ASC, "firstName");
  List<Author> authors = authorRepository.findByFirstName("Thorben", sort);
  ```
  Ref: https://thorben-janssen.com/spring-data-jpa-query-annotation/
</details>
<details>
  <summary>Pagination</summary>
  <br/>
  
  + `LIMIT` clause returns a subset of “n” rows from the query result.
  + `OFFSET` clause placed after the `LIMIT` clause skips "m" number of rows before returning the result query.
  
  ```
  Pageable pageable = PageRequest.of(0, 5, Sort.by("price").descending().and(Sort.by("name")));
  
  Page<Product> allProducts = productRepository.findAll(pageable);
  ```
  
  _Note:_
  1. `offset` in `Pageable` equal `pageIndex * pageSize`
  2. Pagination must use the same order type for all `PageRequest`
  3. When using `JOIN FETCH` with Pagination, it will fetch all associations and make memory increase.
  
  + Ref: https://thorben-janssen.com/pagination-jpa-hibernate/
  + Ref: https://www.baeldung.com/spring-data-jpa-pagination-sorting
</details>
<details>
  <summary>Parameter binding</summary>
  <br/>
  
  Two way to use the parameter binding:
  1. Binding paramter with positions
  2. Binding paramter with `@Param`
  
  ```
    @Query("FROM Author WHERE firstName = ?1")
    List<Author> findByFirstName(String firstName); // position
 
    @Query("SELECT a FROM Author a WHERE firstName = :firstName AND lastName = :lastName")
    List<Author> findByFirstNameAndLastName(@Param("lastName") String firstName, @Param("firstName") String lastName); // @Param
  ```
  
  + Ref: https://thorben-janssen.com/spring-data-jpa-query-annotation/
</details>
<details>
  <summary>JOIN, LEFT JOIN and JOIN FETCH</summary>
  <br/>
  
  + **JOIN:** It's similar with `JOIN` in SQL.
  
  _**Example:**  SELECT a FROM Author a JOIN a.books b WHERE b.title LIKE '%Hibernate%'_
  
  + **LEFT JOIN:** `LEFT JOIN` statement is similar to the `JOIN` statement. The main difference is that a `LEFT JOIN` statement includes all rows of the entity or table referenced on the left side of the statement.
  
  _**Example:**  SELECT a, b FROM Author a LEFT JOIN a.books b WHERE a.lastName = 'Janssen'_
  
  + **FETCH JOIN:** It not only join the 2 database tables within the query but to also initialize the association on the returned entity.
  
  _**Example:**  SELECT a FROM Author a JOIN FETCH a.books b WHERE b.title LIKE '%Hibernate%'_
  
  _Note:_
  1. Hibernate will generate a `CROSS JOIN` for a implicit join. Prefer to always use an explicit JOIN instead.
  
  _**Example:** SELECT s FROM session s WHERE s.managementEntity.managementId = ?1 (implicit join)_ 
  
  + Ref: https://thorben-janssen.com/hibernate-tips-difference-join-left-join-fetch-join/
  + Ref: https://stackoverflow.com/questions/17431312/what-is-the-difference-between-join-and-join-fetch-when-using-jpa-and-hibernate
</details>
<details>
  <summary>Modifying</summary>
  <br/>
  
  When you use the `UPDATE`/`INSERT` statement, it requires an additional @Modifying annotation.
  
  ```
    @Query("UPDATE Author SET firstName = :prefix || firstName")
    @Modifying
    void addPrefixToFirstName(@Param("prefix") String prefix);
  ```
  + Ref: https://thorben-janssen.com/spring-data-jpa-query-annotation/
</details>

<details>
  <summary>Specifications</summary>
  <br/>

  Spring Data JPA Specifications are used to create dynamic queries based on various criteria. They provide a way to build complex queries programmatically using the JPA Criteria API.

  ```
  public interface CustomerRepository extends CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
   …
  }
  ```

  ```
  public class BookSpecifications {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("title"), title);
    }
  }
  ```
  
  + Ref: https://thorben-janssen.com/hibernate-query-spaces/
  
</details>

### Native Query
<details>
  <summary>How to use the native query?</summary>
  <br/>
  
  Enable the `nativeQuery` attribute in the `@Query`.
  
  Ref: https://thorben-janssen.com/native-queries-with-spring-data-jpa/
  
</details>
<details>
  <summary>Limitations of Native Queries With Spring Data JPA</summary>
  <br/>
  
  1. Pagination of native query results requires an extra step.
  
  You need to provide a count query that returns the total number of records.
  
  ```
    @Query(value="select * from author a where a.last_name= ?1", 
        countQuery = "select count(id) from author a where a.last_name= ?1", 
        nativeQuery = true)
    Page<Author> getAuthorsByLastName(String lastname, Pageable page);
  ```
  
  2. Spring Data JPA doesn’t support dynamic sorting for native SQL statements.
  
  When working with a JPQL query, you can add a parameter of type `Sort` to your repository method. This enables you to define the sorting criteria at runtime.
  
  Unfortunately, Spring Data JPA doesn’t support this feature for native queries.
  
  Ref: https://thorben-janssen.com/native-queries-with-spring-data-jpa/
  
</details>

### 
  
### Store Procedure
<details>
  <summary>Call store procedure</summary>
  <br/>
  
  ```
  CREATE PROCEDURE GET_TOTAL_CARS_BY_MODEL(IN model_in VARCHAR(50), OUT count_out INT)
  BEGIN
      SELECT COUNT(*) into count_out from car WHERE model = model_in;
  END
  ```
  _Define the store procedure_
  
  ```
  @Procedure(procedureName = "GET_TOTAL_CARS_BY_MODEL")
  int getTotalCarsByModelProcedureName(String model);
  ```
  _Use the store procedure in Repository_
  
  ```
  @Query(value = "CALL FIND_CARS_AFTER_YEAR(:year_in);", nativeQuery = true)
  List<Car> findCarsAfterYear(@Param("year_in") Integer year_in);
  ```
  _Use the store procedure with `@Query`_
  
  + Ref: https://thorben-janssen.com/hibernate-query-spaces/
  + Ref: https://www.baeldung.com/spring-data-jpa-stored-procedures
</details>

### Transaction in JPA

<details>
  <summary>Transaction Characteristics</summary>
  <br/>
  
  1. Atomicity - _Either all operations complete successfully of the transaction fails and the db in unchanged._
  2. Consistency - _The db state must be valid after the transaction. All constraints must be satisfied._
  3. Durability - _Data written by a sucessful transaction must e recorded in persistennt storage._
  4. Isolation - _Concurrent transaction must not affect each other._
  + Ref: 
</details>
  
<details>
  <summary>Isolation</summary>
  <br/>
  
  Isolation is one of the four property of a database transaction, where at its highest level, a perfect isolation ensures that all concurrent transactions will not affect each other. But there are several ways that a transaction can be interfered by other transactions that runs simultaneously with it. This is called `read phenomenon`.
  
  Phenomenon          | Description | 
  ---                 | ---         | 
  Dirty read          | A transaction **reads** data written by other concurrent **uncommitted** transaction |
  Non-repeatable read | A transaction **reads** the **same row twice** and sees different value because it has been **modified** by other **committed** transaction |
  Phantom read        | A transaction re-executes a query to find rows that satisfy a condition and sees a different set of rows, due to changes by other committed transaction |
  Serialization       |             |
  
  + Ref: https://dev.to/techschoolguru/understand-isolation-levels-read-phenomena-in-mysql-postgres-c2e#acid-property
</details>  

### Optimistic vs. Pessimistic Locking
<details>
  <summary>Optimistic locking</summary>
  <br/>

  + Optimistic locking in Hibernate is a concurrency control mechanism that assumes multiple transactions can complete without affecting each other.
  + _Instead of locking_ the data resources, each transaction proceeds and verifies at commit time.

  **Here’s how it works:**
  
  ![](images/optimistic_lock.png)
  
  + **Versioning:** Each entity has a version field annotated with @Version. This field is automatically incremented with each update.
  + **Transaction Check:** When a transaction tries to commit, Hibernate checks the version field.  If the version has changed, it means another transaction has modified the data, and an exception is thrown.

  **When to Use Optimistic Locking**
  + When your application performs many read operations and fewer write operations. (E-commerce Platforms: For managing product details or user profiles, where updates are less frequent than reads)
  + Distributed Systems, in systems where transactions runs across multiple nodes or services.
  
</details>
<details>
  <summary>Pessimistic locking</summary>
  <br/>

  + Pessimistic locking is a strategy used in database management to handle concurrent access to data.
  + The main idea is to lock the data as soon as a transaction starts to access it, preventing other transactions from making changes until the lock is released.

  **How it works**
  + When a transaction wants to read or write data, it requests a lock.
  + **Read Lock (PESSIMISTIC_READ):** Other transactions can read the data but cannot modify or delete it.
  + **Write Lock (PESSIMISTIC_WRITE):** Other transactions cannot read, modify, or delete the data.
  + **Force Increment Lock (PESSIMISTIC_FORCE_INCREMENT):** Similar to a write lock but also increments a version number, useful for versioned entities.

  **When to Use Pessimistic Locking**
  + When multiple transactions frequently access the same data.
  + When data consistency is important, such as in financial transactions.
  + When transactions take a long time to complete, increasing the risk of conflicts.
</details>

<details>
  <summary>Pessimistic locking vs locking mechanism in database</summary>
  <br/>

  
  
</details>

<details>
  <summary>Optimistic locking vs Isolation</summary>
  <br/>

  Optimistic Locking and Isolation Levels are both mechanisms used to ensure data consistency in concurrent environments.

  **Optimistic Locking**
  + **Level:** Application-level
  + **Mechanism:** Tracks versions of entities to detect conflicts when multiple transactions attempt to modify the same data at the same time.
  
  **Isolation Levels**
  + **Level:** Database-level.
  + **Mechanism:** Defines the isolation level between concurrent transactions.
  
</details>

### Flush Mode
  
<details>
  <summary>Overview</summary>
  <br/>
  
  Flushing is the process of synchronizing the state of the persistence context with the underlying database.
  
  Mode | Description | Note
  --- | --- | --- | 
  AUTO | It flushes the changed entities before committing a transaction and before executing a query related to an entity that has any pending changes | supported by JPA and Hibernate (DEFAULT) Similar.
  COMMIT | It flushes the changed entities before committing a transaction but doesn't execute any pending changes before a query | supported by JPA and Hibernate.
  ALWAYS | It's similar `AUTO` mode. But it will execute with every query | supported by Hibernate.
  MANUAL | It deactivates all automatic flushes and requires the application to trigger the flushes automatically. | supported by Hibernate (high risks)
  
  _Note:_
  + `flush()` is called only after executing a JPQL query or a Criteria Query.
  + If you use default functions in `JpaRepository`, you must call the `flush()` function manually.
  + By default, the **FlushMode** is set to `AUTO`, and I recommend you **DON’T** change it.

  Ref: https://thorben-janssen.com/flushmode-in-jpa-and-hibernate/
</details>
<details>
  <summary>flush() vs commit()</summary>
  <br/>
  
  + `flush()` will synchronize your database with the current state of object/objects held in the memory but it does not commit the transaction.
  + `commit()` will make data stored in the database permanent.
  
  Ref: https://stackoverflow.com/questions/14581865/hibernate-flush-and-commit
</details>
  
### Projections
<details>
  <summary>What is projection?</summary>
  <br/>
  
  Projection allows to fetch only specific columns or attributes from an entity, rather than fetching the entire entity.
  
  Ref: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
</details>
<details>
  <summary>Projection type</summary>
  <br/>
  
  1. **Scalar projection**: that consists of one or more database columns that are returned as an Object[].
  2. **DTO projection**: that consists that selects all database columns mapped by an entity class.of one or more database columns. It uses them to call a constructor and returns one or more unmanaged objects.
  3. **Entity projection**: that selects all database columns mapped by an entity class.
  
  _Note:_ Entity projections are great for all **write operations**. But **read-only operations** should be handled differently. Hibernate doesn’t need to manage any states or **perform dirty checks** if you just want to read some data from the database. So, `DTO projection` should be the better projection for reading your data.
  
  + Ref: https://thorben-janssen.com/spring-data-jpa-query-projections/#JPA8217s_DTOs
  + Ref: https://thorben-janssen.com/entities-dtos-use-projection/
</details>  
<details>
  <summary>DTO projection type</summary>
  <br/>

  There are a few ways to implement projections in JPA:

  + **JPA’s DTOs**
  + **Class-based Projections (DTOs)**
  + **Interface-based Projections**
</details>
<details>
  <summary>JPA’s DTOs</summary>
  <br/>
  
  A DTO class typically only defines a set of attributes, getter methods, a constructor that sets all attributes.
  ```
  public class AuthorSummaryDTO {
     
    private String firstName;
    private String lastName;
     
    public AuthorSummaryDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
     
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
  }
  ```
  To use this class as a projection with plain JPA, you need to use a constructor expression in your query
  ```
  @Query("SELECT new com.thorben.janssen.spring.jpa.projections.dto.AuthorSummaryDTO(a.firstName, a.lastName) FROM Author a WHERE a.firstName = :firstName")
  List<AuthorSummaryDTO> findByFirstName(String firstName);
  ```
  Ref: https://thorben-janssen.com/spring-data-jpa-query-projections/#JPA8217s_DTOs
</details>
<details>
  <summary>Class-based projections (DTOs)</summary>
  <br/>
  
  You can use DTO projections in a derived query without a constructor expression. As long as the DTO class has **only one constructor** and its parameter names **match** your entity class’s attribute names.
  ```
  @Repository
  public interface AuthorRepository extends CrudRepository<Author, Long> {

      List<AuthorSummaryDTO> findByFirstName(String firstName);
  }
  ```
  + Ref: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.dtos
  + Ref: https://thorben-janssen.com/spring-data-jpa-query-projections/#Spring_Data8217s_Simplified_DTOs
</details>
<details>
  <summary>Interface-based projections</summary>
  <br/>
  
  You can also use an interface as your DTO projection. As long as your interface only defines getter methods for basic attributes. 
  
  In addition, **the name of that method** has to be identical to that of a **getter method** defined on the **entity** class.
  
  ```
  public interface AuthorView {
  
    String getFirstName(); // the method name must be similar to entity class (Author)
  
    String getLastName();
  }
  ```
  ```
  @Repository
  public interface AuthorRepository extends CrudRepository<Author, Long> {

      AuthorView  findViewByFirstName(String firstName);
  }
  ```
  Ref: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.interfaces
</details>
<details>
  <summary>Dynamic projections</summary>
  <br/>
  
  ```
  @Repository
  public interface AuthorRepository extends CrudRepository<Author, Long> {
      <T> T findByLastName(String lastName, Class<T> type);   
  }
  ```
  Ref: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.dtos
</details> 

### Logging
<details>
  <summary>Hibernate logging</summary>
  <br/>

  ```
  spring:
    jpa:
      show-sql: true
      properties.hibernate.format_sql: true
  logging:
    level:
      org.hibernate.type.descriptor.sql: trace
      logger.org.hibernate.SQL: debug
      org.springframework.orm.jpa: debug
      org.hibernate.engine.transaction.internal: debug
      org.hibernate.resource.jdbc.internal.AbstractLogicalConnectionImplementor: trace
      org.springframework.transaction.interceptor.TransactionAspectSupport: debug
  ```
  
  Ref: https://vladmihalcea.com/the-best-way-to-log-jdbc-statements/
  
</details>

### Performance review
<details>
  <summary>Check list</summary>
  <br/>
  
  + Ref: https://vladmihalcea.com/hibernate-performance-tuning-tips/
  + Ref: https://thorben-janssen.com/tips-to-boost-your-hibernate-performance/
  
</details>
<details>
  <summary>DTO projections</summary>
  <br/>
  
  Ref: https://thorben-janssen.com/entities-dtos-use-projection/
</details>
<details>
  <summary>Insert/Update Batch</summary>
  <br/>
  
  ```
  spring:
    jpa:
      properties:
        hibernate: 
          jdbc.batch_size: 5 # Hibernate will silently disable batch inserts if we use GenerationType.IDENTITY
          order_inserts: true
          order_updates: true
          batch_versioned_data: true
  ```
  + Ref: https://stackoverflow.com/questions/50772230/how-to-do-bulk-multi-row-inserts-with-jparepository
  + Ref: https://www.baeldung.com/jpa-hibernate-batch-insert-update
</details>
 
## Common pitfalls
### Avoid CascadeType.REMOVE for to-many associations
<details>
  <summary>Root cause</summary>
  <br/>
  
  
  
</details>
<details>
  <summary>Solution</summary>
  <br/>
  
  Ref: https://thorben-janssen.com/avoid-cascadetype-delete-many-assocations/
</details>
  
### LazyInitializationException

<details>
  <summary>Root cause</summary>
  <br/>
  
  Hibernate throws the LazyInitializationException when it needs to initialize a lazily fetched association to another entity without an active session context.
  
  ```
  EntityManager em = emf.createEntityManager();
  em.getTransaction().begin(); // open session

  TypedQuery<Author> q = em.createQuery(
          "SELECT a FROM Author a",
          Author.class);
  List<Author> authors = q.getResultList();
  em.getTransaction().commit();
  em.close(); //session is closed

  for (Author author : authors) {
      List<Book> books = author.getBooks();
      log.info("... the next line will throw LazyInitializationException ...");
      books.size();
  }
  ```

  Ref: https://thorben-janssen.com/lazyinitializationexception/#:~:text=Hibernate%20throws%20the%20LazyInitializationException%20when,client%20application%20or%20web%20layer.
  
</details>
<details>
  <summary>Solution</summary>
  <br/>
  
  1. Make sure to load all associated entities in before closing the session (LEFT JOIN FETCH clause, `@NamedEntityGraph` or the _EntityGraph_ API.)
  2. Use a DTO projection instead of entities. DTOs don’t support lazy loading
  
</details>

### N+1 query problem
<details>
  <summary>Root cause</summary>
  <br/>
  
  The N+1 query problem happens when the data access framework executed N additional SQL statements to fetch the same data that could have been retrieved when executing the primary SQL query.
  
  _For example:_
  We have a relationship `one-to-many` company -> employees. 
  
  If we select all employees in the database.
  ```
  List<Employee> employees = employeeRepository.findAll();
  ```
  And, laster, we decide to fetch associated `company` for each employee.
  
  ```
  for (Employee employee : employees) {
    employee.getCompany();
  }
  ```
  _Query_
  
  ```
  SELECT * FROM employee

  // trigger the N+1 query issue
  SELECT * FROM company c WHERE c.id = 1
  SELECT * FROM company c WHERE c.id = 2
  SELECT * FROM company c WHERE c.id = 3
  SELECT * FROM company c WHERE c.id = 4
  ```
  Ref: https://vladmihalcea.com/n-plus-1-query-problem/
</details>
<details>
  <summary>Solution</summary>
  <br/>
  All we need to do is extract all the data you need in the original SQL query
  
  ```
  List<Employee> employees = entityManager.createNativeQuery("""
      SELECT *
      FROM employee emp
      JOIN company c ON emp.company_id = c.id
      """, Employee.class)
  .getResultList();

  for (Employee employee : employees) {
      LOGGER.info("{}", employee);
  }
  ```
</details>

### MultipleBagFetchException issues
<details>
  <summary>Root cause</summary>
  <br/>
  
  In Hibernate, the collection type is called a _Bag_ if the elements in List are unordered. Otherwise, it's called a _List_. If we try to fetch multiple of these bags (_*ToMany_), Hibernate will throws a `MultipleBagFetchException`.

  Example: 
  ```
  public interface ManagementRepository extends JpaRepository<ManagementEntity, Long> {

    @EntityGraph(attributePaths = {"sessions", "users"})
    ManagementEntity findById(Long managementId);

  }
  ```
  In this example, `ManagementEntity` is one to many with `SessionEntity` and `UserEntity`. When we use the `@EntityGraph` to try to fetch early sessions and users. And we will create a multiple of bags then Hibernate will throws `MultipleBagFetchException`.
</details>
<details>
  <summary>Solution</summary>
  <br/>

  + If the collections only contain a small number of elements, we can consider to use _`java.util.Set`_. Hibernate can then fetch multiple collections in 1 query.
  + If at least one of collection contains a lot of elements, we have to split multiple queries to different parts (_in this case is 2 parts_).
</details>

### @OneToOne lazy fetch issue
<details>
  <summary>Root cause</summary>
  <br/>
  
  In unidirectional @OneToOne association, Hibernate supports loading lazy as expected. But in the unidirectional association, the parsent (child has `@JoinColumn`, parent - `mappedBy`) side is not. Even when specifying that the association is not `optional` and we have the `FetchType.LAZY`. The parent-side association behaves like a `FetchType.EAGER` relationship.
  
  Example: 
  ```
  @Entity
  public class Book {
   
      @Id
      @GeneratedValue
      private Long id;
   
      @OneToOne(mappedBy = "book", fetch = FetchType.LAZY) // the FetchType.LAZY is not effect.
      private Manuscript manuscript;
   
      ...
  }
  ```
</details>
<details>
  <summary>Solution</summary>
  <br/>
  
  + Use the unidirectional association and query from the child site
  + Share the Primary Key in a One-to-One Association
</details>

## Envers Event
## Spring Data JPA
### Pagination and Sorting using Spring Data JPA

<details>
  <summary>Page as Resource vs Page as Representation</summary>
  <br/>

  In high level we have following three options to build the pagination.

  + `http://domainname/products?page=1` _(standard)_
  + `http://domainname/products/page/1?sort_by=date`
  + `http://domainname/products/date/page/1`

  Keeping in mind that **a page isn’t a Resource**. We’ll use the standard way by encoding the paging information in a URI query.
  
</details>

<details>
  <summary>Pagination & Sorting example</summary>
  <br/>

  ```
  @GetMapping("/api/products")
  public ResponseEntity<Page<Product>> getProducts(@PageableDefault(page = 1, size = 20, sort = "name", direction = Sort.Direction.DESC) Pageable pageable) {
      Page<Product> products = productService.getProducts(pageable);
      return ResponseEntity.ok(products);
  }
  ```

  URL: `/api/products?page=2&size=50&sort=price&direction=ASC`
</details>
<details>
  <summary>Information should return for Front-end</summary>
  <br/>

  + `number`: The current page number (zero-based).
  + `size`: The size of the page (number of elements per page).
  + `totalPages`: The total number of pages available.
  + `totalElements`: The total number of elements across all pages.
  + `isLast`: Checks if this is the last page.
  
</details>
