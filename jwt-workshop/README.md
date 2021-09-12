# jwt-workshop

* local.tin.tests.jwt.workshop.GetClaim:
    + java -cp jwt-workshop-1.0-jar-with-dependencies.jar local.tin.tests.jwt.workshop.GetClaim <Properties file> <Token>

* local.tin.tests.jwt.workshop.GetToken
    + java -cp target/jwt-workshop-1.0-jar-with-dependencies.jar local.tin.tests.jwt.workshop.GetToken <Properties file>

Java version 8 to prevent: 

    Exception in thread "main" java.lang.NoClassDefFoundError: javax/xml/bind/DatatypeConverter
            at local.tin.tests.jwt.workshop.GetClaim.main(GetClaim.java:36)
    Caused by: java.lang.ClassNotFoundException: javax.xml.bind.DatatypeConverter
            at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:583)
            at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
            at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:521)
            ... 1 more
