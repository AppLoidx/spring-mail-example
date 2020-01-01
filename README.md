# Spring Boot JavaMail example 
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/AppLoidx/spring-mail-example.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/AppLoidx/spring-mail-example/context:java)


First, you need to configure your application properties with your credentials:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=465
spring.mail.username=your_email
spring.mail.password=password
spring.mail.properties.mail.smtp.auth=true

# If you need TLS in some ports
# spring.mail.properties.mail.smtp.starttls.enable=true

# SSL, post 465
spring.mail.properties.mail.smtp.socketFactory.port=465
spring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
```

In general, you have two ways to configure ports: TLS/STARTTLS or SSL. Find more in google official documentation

In this project configured SSL with port 465.

Next, lets write service bean:

```java
@Component
public class EmailServiceImpl {

    public final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    void sendEmailWithAttachment(String to, String subject) throws MessagingException {

        MimeMessage msg = emailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(to);

        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Hey!</h1>" +
                "<img src=\"https://i.pinimg.com/564x/31/df/14/31df1484768d55b36fc62b30e935b95c.jpg\" />", true);

        emailSender.send(msg);

    }
}
```

And create controller for trigger message sending:

```java
@RestController
@RequestMapping("/start")
public class StartTestController {

    private final EmailServiceImpl emailService;

    public StartTestController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/simple")
    public void simpleMessage() {
        emailService.sendSimpleMessage("to_email@gmail.com", "Arthur", "Hello!");
    }

    @GetMapping("attachment")
    public void withAttachment() throws IOException, MessagingException {
        emailService.sendEmailWithAttachment("to_email@gmail.com", "Arthur");
    }
}
```

Paste to your browser:
```
localhost:8080/start/simple
```
![](https://i.imgur.com/L3ZiJbV.png)

And another one:
```
localhost:8080/start/attachment
```

![](https://i.imgur.com/qeMkeg5.png)
