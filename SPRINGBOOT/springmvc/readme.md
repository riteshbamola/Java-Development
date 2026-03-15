# Spring Core MVC Configuration

## Overview

This document explains how to configure a Spring MVC application using **Spring Core** with XML-based setup.

---

## The Full Abstraction Stack

```
Your Code
    ↓
Spring Data JPA
(JpaRepository, @Transactional)
    ↓
Hibernate
(ORM - maps Java objects to tables)
(eliminates raw SQL, manages sessions)
    ↓
JDBC
(raw Java API - connections, ResultSet, PreparedStatement)
    ↓
Database Driver (MySQL connector)
    ↓
Database
```

### What Each Layer Eliminates

| Layer | What you don't have to do anymore |
|---|---|
| **JDBC** | Manage OS/network level DB connection yourself |
| **Hibernate** | Write raw SQL, map ResultSet to Java objects manually |
| **Spring Data JPA** | Write DAO classes, manage SessionFactory, write HQL |
| **Spring Boot** | Configure DataSource, SessionFactory, TransactionManager beans manually |

### In One Line Each

- **JDBC** → *"Talk to DB in raw Java"*
- **Hibernate** → *"I'll write SQL for you, just give me Java objects"*
- **Spring Data JPA** → *"I'll write the DAO for you, just define the interface"*
- **Spring Boot** → *"I'll configure everything for you, just write properties file"*

---

## How It Works

In Spring Core, to set up the MVC flow, you need to manually configure a few key files. The request flow works as follows:

> **Incoming Request → Dispatcher Servlet → Controller → Response**

---

## Step 1: Configure `web.xml`

The first request goes to the **Dispatcher Servlet**. You need to register it in `web.xml` along with its URL mapping.

```xml
<web-app>

  <!-- Define the Dispatcher Servlet -->
  <servlet>
    <servlet-name>ritesh-servlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>

  <!-- Map the Dispatcher Servlet to a URL pattern -->
  <servlet-mapping>
    <servlet-name>ritesh-servlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

</web-app>
```

> In `web.xml`, you configure your **servlet name** and its **URL pattern** so the Dispatcher Servlet knows which requests to handle.

---

## Step 2: Configure `ritesh-servlet.xml`

In the `ritesh-servlet.xml` file, you need to:

- **Create and configure beans**
- **Set the base package** where all your components/beans are located
- **Enable annotation-based configuration**

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="...">

  <!-- Enable annotation-based configuration -->
  <context:annotation-config/>

  <!-- Set the base package to scan for components/beans -->
  <context:component-scan base-package="com.ritesh.controllers"/>

</beans>
```

> This tells Spring where to look for all your `@Controller`, `@Service`, `@Component`, and other annotated classes.

---

## Step 3: Configure Hibernate in ritesh-servlet.xml

Add the following beans manually:

### 1. DataSource Bean
```xml
<bean id="dataSource" 
      class="org.springframework.jdbc.datasource.DriverManagerDataSource">
  <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
  <property name="url" value="jdbc:mysql://localhost:3306/ritesh"/>
  <property name="username" value="root"/>
  <property name="password" value="1971"/>
</bean>
```

### 2. SessionFactory Bean
```xml
<bean id="sessionFactory"
      class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
  <property name="dataSource" ref="dataSource"/>
  <property name="packagesToScan" value="com.ritesh.models"/>
  <property name="hibernateProperties">
    <props>
      <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
      <prop key="hibernate.show_sql">true</prop>
      <prop key="hibernate.hbm2ddl.auto">update</prop>
    </props>
  </property>
</bean>
```

### 3. Transaction Manager Bean
```xml
<bean id="transactionManager"
      class="org.springframework.orm.hibernate5.HibernateTransactionManager">
  <property name="sessionFactory" ref="sessionFactory"/>
</bean>

<tx:annotation-driven transaction-manager="transactionManager"/>
```

### 4. DAO Class (Manual)
```java
@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    public User findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }
}
```

### 5. Service Class
```java
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }
}
```

### Flow
```
Controller
    ↓
Service (@Transactional)
    ↓
DAO (SessionFactory)
    ↓
Hibernate
    ↓
Database
```

---

## Summary

| File | Purpose |
|---|---|
| `web.xml` | Register the Dispatcher Servlet and define URL mappings |
| `ritesh-servlet.xml` | Define beans, set base package, enable annotation config |

---

## Key Concepts

- **Dispatcher Servlet** — The front controller that intercepts all incoming HTTP requests.
- **web.xml** — The deployment descriptor where servlet names and URL patterns are configured.
- **ritesh-servlet.xml** — The Spring application context file for MVC configuration.
- **component-scan** — Automatically detects and registers annotated beans from the specified base package.
- **Annotation-based Configuration** — Uses annotations like `@Controller`, `@RequestMapping` instead of verbose XML bean definitions.