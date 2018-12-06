### Kullanılan Teknolojiler
* JSF 
* PrimeFaces 
* WildFly 10
* Hibernate 
* PostgreSQL 
* Maven


### Kurulum

* ` WEB-INF/lib/ ` dizini altındaki jar dosyaları maven olarak bulunmadığı için projeye dahil etmelisiniz.

* Uygulamada Security kullanıldığı için WildFly dizini altında ` standalone/configuration/standalone.xml ` içine aşağıdaki bilgileri eklemelisiniz.

` <datasources> </datasources> ` etiketleri arasına aşağıdaki kodu kopyalayınız.
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

` <security-domains> </security-domains> ` etiketleri arasına aşağıdaki kodu kopyalayınız.

                
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

* Email gönderimi için `  com/technicalservice/resource/mail.properties `  dosyası içersinde smtp ve kullanıcı bilgilerini ayarlamalısınız.

* Sisteme cihaz resmi yüklediğimiz için manuel olarak ` image_seq` adında sequence ekleyiniz.

* Yüklenen resimlerin yolunu `  com/technicalservice/resource/Prefix.java ` sınıfından ayarlayabilirsiniz. Daha sonra belirttiğiniz klasörün içine image adında bir klasör daha eklemelisiniz. Ayrıca belirttiğiniz adresin yazma ve okuma yetkisi olmalıdır.
