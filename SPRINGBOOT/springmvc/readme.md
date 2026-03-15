# Spring Core MVC Configuration

## Overview

This document explains how to configure a Spring MVC application using **Spring Core** with XML-based setup.

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