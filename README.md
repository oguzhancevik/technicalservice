[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8b35d8bd87ef4a6298cd08f9eb3a71b8)](https://app.codacy.com/app/oguzhancevik/technicalservice?utm_source=github.com&utm_medium=referral&utm_content=oguzhancevik/technicalservice&utm_campaign=Badge_Grade_Dashboard)
[![Known Vulnerabilities](https://snyk.io/test/github/oguzhancevik/technicalservice/badge.svg?targetFile=pom.xml)](https://snyk.io/test/github/oguzhancevik/technicalservice?targetFile=pom.xml)

:page_facing_up:[Turkish README](https://github.com/oguzhancevik/technicalservice/blob/master/READMETR.md)

### Used Technologies :rocket:
* JSF 
* PrimeFaces 
* WildFly 10
* Hibernate 
* PostgreSQL 
* Maven

### System Features :zap:

1- There is security in the system. There are features such as Register, Login and Forgot Password.

2- If the password forgotten feature is used, a password reset link will be sent to the user's mail and a new password will be set.

3- There are two roles in the system, Admin and Customer.

4- Users registered to the system can enter the system after the approval of admin.

5- Blocked customers cannot access the site.

6- The admin dashboard displays information about the system. For example; number of active customers, number of active devices, number of maintenance / repairs, number of admin.

7- The admin dashboard displays information about the system. For example; number of active customers, number of active devices, number of maintenance / repairs, number of admin.

8- Admin is responsible for repair or maintenance of customer device's.

9- If a customer wants to open a repair or maintenance on his/her device, firstly he/she must be member of system. Then the device should be registered and on which device to repair or maintenance an issue should open an issue.

10- Only one admin can work on an issue.

11- Automatic mail is sent to the user for the date of maintenance. Or, if there is any part change on a device, it goes to the user as an e-mail.

12- Admin can add to-do list. Each admin can have more than one to-do list.

13- Each to-do list can have a name, description, deadline, status, and sub to-do lists.

14- The status of the to-do list can be marked as <code>continues</code> , <code>completed</code>.

15- Admin can view, update and delete to-do lists. They can add to-do list to existing to-do list.

16- There may be dependencies between to-do lists. There may be sub-to-do lists that are dependent on a to-do list. If any of these dependent sub-to-do lists are not marked <code>complete</code>, the master list cannot be marked as <code>complete</code>.


### Installation :closed_lock_with_key:

* The jar files under `WEB-INF/lib/` directory are not included in the maven and must be included in the project.

* You must create a database named TechnicalService into PostgreSql.

* Because security is used in the application, you must add the following information into `standalone/configuration/standalone.xml` under WildFly directory (JBOSS_HOME).

Copy the following code between the `<datasources> </datasources>` tags.

```
<datasource jta="true" jndi-name="java:/TechnicalService" pool-name="TechnicalService" enabled="true" use-ccm="false">
  <connection-url>jdbc:postgresql://localhost:5432/TechnicalService</connection-url>
  <driver-class>org.postgresql.Driver</driver-class>
  <driver>postgresql-42.2.2.jar</driver>
  <security>
    <user-name>admin</user-name>
    <password>1234</password>
  </security>
  <validation>
    <check-valid-connection-sql>select 1</check-valid-connection-sql>
    <validate-on-match>true</validate-on-match>
    <background-validation>true</background-validation>
  </validation>
  <statement>
    <share-prepared-statements>false</share-prepared-statements>
  </statement>    
</datasource>
```

Copy the following code between the `<security-domains> </security-domains>` tags.
                
 ```
 <security-domain name="TechnicalServiceSD" cache-type="default">
  <authentication>
    <login-module code="Database" flag="required">
      <module-option name="dsJndiName" value="java:/TechnicalService"/>
      <module-option name="principalsQuery" value="SELECT password FROM users WHERE email=?"/>
      <module-option name="rolesQuery" value="SELECT role, 'Roles' FROM users WHERE email=?"/>
    </login-module>
  </authentication>
</security-domain>
```                

* You must set the smtp and user information in the `com/technicalservice/resource/mail.properties` file for sending an email. You may need to grant permission from your email account (For example: trusted apps section must be open on Gmail)

* Add `image_seq` sequence manually because we've uploaded the device image to the system.

* You can set the path of the uploaded images from the `com/technicalservice/resource/Prefix.java` class. Then you must add a folder named image into the path you specified. You must also have the authority to write and read the address you specify.

### Usage :heavy_check_mark:

1- Admin Dashboard 
<br/>
![Admin Dashboard](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/00-adminDashbard.gif)


2- Forgot Password
<br/>
![Forgot Password](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/01-forgotPassword.gif)


3- Customer Registration
<br/>
![Customer Registration](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/02-customerRegister.gif)


4- Customer Login
<br/>
![Customer Login](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/03-customerLogin.gif)


5- Device CRUD
<br/>
![Device CRUD](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/04-deviceCrud.gif)


6- Maintenance / Repair CRUD
<br/>
![Maintenance / Repair CRUD](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/05-issueCrud.gif)


7- Admin Maintenance / Repair
<br/>
![Admin Maintenance / Repair](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/06-adminIssue.gif)


8- To-Do CRUD
<br/>
![To-Do CRUD](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/07-toDoCrud.gif)


### Sources :information_source:
[JavaDoc](https://github.com/oguzhancevik/technicalservice/tree/master/javadoc)
<br/>
[Uml Class Diagram](https://github.com/oguzhancevik/technicalservice/tree/master/uml)
<br/>
[Rest Services Screenshots](https://github.com/oguzhancevik/technicalservice/tree/master/analiz/rest)
<br/>
[YouTube Playlist](https://www.youtube.com/playlist?list=PLfFIom4mu859IytYr4gPFFabvOnWp1w3f)
