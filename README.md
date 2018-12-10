### Kullanılan Teknolojiler :rocket:
* JSF 
* PrimeFaces 
* WildFly 10
* Hibernate 
* PostgreSQL 
* Maven

### Sistem Özellikleri :zap:

1- Sistemde security bulunmaktadır. Register, Login, Şifremi unuttum gibi özellikler bulunmaktadır.

2- Şifremi unuttum özelliği kullanılırsa kullanıcının mailine şifre sıfırlama linki gönderilecektir ve yeni bir şifre belirlenecektir.

3- Sistemde Admin ve Customer adında iki rol bulunmaktadır.

4- Sisteme kayıt olan kullanıcılar admin onayından geçtikten sonra sisteme girebilirler.

5- Bloke olan müşteriler ise siteye erişimezler.

6- Admin dasboard ekranında sistemle alakalı bilgiler verilmektedir. Ör; aktif müşteri sayısı, aktif cihaz sayısı, bakım / onarım sayısı, admin sayısı gibi.

7- Müşteri dashboard ekranında müşteri ile ilgili verilmektedir. Müşteri cihaz sayısı, Ör; Müşteri bakım / onarım talep sayısı.

8- Admin kullanıcısı Customer kullanıcısının açtığı bakım onarım işleri ile ilgilenmektedir.

9- Bir müşteri cihazında arıza kaydı veya bakım kaydı yaptırmak istiyor ise ilk önce sisteme üye olmalıdır. Daha sonra cihaz kaydı yapmalı ve hangi cihaz üzerinde işlem yapacaksa ona göre bir issue açmalıdır.

10- Bir ürün üzerinde birden fazla admin işlem yapamaz.

11- Bakım tarihi gelen cihaz için kullanıcıya otomatik mail atılır. Veya bir cihaz üzerinde herhangi bir parça değişikliği olmuş ise bu bilgide kullanıcıya mail olarak gider.


### Yakında Eklenecek Özelllikler :boom:

1- Admin kullanıcıları to-do (yapılacak) listesi ekleyebilecek. Her admin kullanıcısının birden fazla to-do listesi olabilecek. Her to-do listesinin ismi, açıklaması, bitiş tarihi, statusu ve varsa bağımlı alt to-do listeleri olabilir.

2- To-do listesinin statusu <code> devam ediyor </code>, <code> başarılı </code>, <code> başarısız olarak işaretlenebilir. </code>

3- Admin kullanıcısı to-do listelerini görüntüleyebilir, güncelleyebilir, silebilirler. Yada mevcut to-do listesine yapılacak to-do item ekleyebilirler.

4- To-do listeleri arasında bağımlılık olabilir. Eğer bir to-do listesinde bağımlı olan to-do listeler var ise bu bağımlı to-do listelerinden herhangi biri tamamlanmamış ise ana liste tamamlandı olarak işaretlenemez.


### Kurulum :closed_lock_with_key:

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

* Email gönderimi için `  com/technicalservice/resource/mail.properties `  dosyası içersinde smtp ve kullanıcı bilgilerini ayarlamalısınız. Email hesabınızdan izin vermeniz gerekebilir (Ör: Gmail güvenilirliği az olan uygulamalar bölümü açık olmalı)

* Sisteme cihaz resmi yüklediğimiz için manuel olarak ` image_seq` adında sequence ekleyiniz.

* Yüklenen resimlerin yolunu `  com/technicalservice/resource/Prefix.java ` sınıfından ayarlayabilirsiniz. Daha sonra belirttiğiniz klasörün içine image adında bir klasör daha eklemelisiniz. Ayrıca belirttiğiniz adresin yazma ve okuma yetkisi olmalıdır.

### Kullanım :heavy_check_mark:

1- Admin Dashboard 
<br/>
![Admin Dashboard](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/00-adminDashbard.gif)


2- Şifremi Unuttum
<br/>
![Şifremi Unuttum](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/01-forgotPassword.gif)


3- Müşteri Kayıt
<br/>
![Müşteri Kayıt](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/02-customerRegister.gif)


4- Müşteri Login
<br/>
![Müşteri Login](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/03-customerLogin.gif)


5- Cihaz CRUD
<br/>
![Cihaz CRUD](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/04-deviceCrud.gif)


6- Bakım / Onarım CRUD
<br/>
![Bakım / Onarım CRUD](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/05-issueCrud.gif)


7- Admin Bakım / Onarım
<br/>
![Bakım / Onarım CRUD](https://raw.githubusercontent.com/oguzhancevik/technicalservice/master/analiz/ekran/06-adminIssue.gif)


### Kaynaklar :information_source:
[JavaDoc](https://github.com/oguzhancevik/technicalservice/tree/master/javadoc)
<br/>
[Uml Class Diagram](https://github.com/oguzhancevik/technicalservice/tree/master/uml)
<br/>
[Rest Servisleri Ekran Görüntüleri](https://github.com/oguzhancevik/technicalservice/tree/master/analiz/rest)
<br/>
[YouTube Playlist](https://www.youtube.com/playlist?list=PLfFIom4mu859IytYr4gPFFabvOnWp1w3f)
